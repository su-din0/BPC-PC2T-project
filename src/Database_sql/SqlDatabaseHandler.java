package Database_sql;

import java.sql.*;
import java.util.*;
import java.util.Date;

import bpc_pct2_projekt.*;

public class SqlDatabaseHandler {
    
    
    public static void saveAllStudents(HashMap<Integer, Student> students) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL)) {
            conn.setAutoCommit(false); 

            try (Statement clear = conn.createStatement()) {
                clear.execute("DELETE FROM students");
                clear.execute("DELETE FROM grades");
            }
           
            String sInsert = "INSERT INTO students (id, first_name, last_name, birthday, study_program) VALUES (?, ?, ?, ?, ?)";
            String gInsert = "INSERT INTO grades (student_id, grade) VALUES (?, ?)";

            try (PreparedStatement psStudent = conn.prepareStatement(sInsert);
                 PreparedStatement psGrade = conn.prepareStatement(gInsert)) {

                for (Student s : students.values()) {
                    psStudent.setInt(1, s.getId());
                    psStudent.setString(2, s.getFirstName());
                    psStudent.setString(3, s.getLastName());

                    java.sql.Date sqlBirthday = new java.sql.Date(s.getBirthday().getTime());
                    psStudent.setDate(4, sqlBirthday);

                    psStudent.setString(5, s.getStudyProgram());
                    psStudent.executeUpdate();

                    for (Integer grade : s.getGrades()) {
                        psGrade.setInt(1, s.getId());
                        psGrade.setInt(2, grade);
                        psGrade.executeUpdate();
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            System.err.println("Chyba při ukládání studentů a známek: " + e.getMessage());
            throw e;
        }
    }
    
    public static List<Student> loadAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        int maxId = 0;

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL)) {
            String query = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                Date birthday = rs.getDate("birthday");

                String studyProgram = rs.getString("study_program");

                Student student;
                if (studyProgram.equalsIgnoreCase("telekomunikace")) {
                    student = new TelecomStudent(firstName, lastName, birthday);
                } else {
                    student = new CybersecStudent(firstName, lastName, birthday);
                }

                student.setId(id);
                if (id > maxId) maxId = id;

                String gQuery = "SELECT grade FROM grades WHERE student_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(gQuery)) {
                    ps.setInt(1, id);
                    ResultSet rsGrades = ps.executeQuery();

                    while (rsGrades.next()) {
                        student.addGrade(rsGrades.getInt("grade"));
                    }
                }

                students.add(student);
            }
            Student.setNextId(maxId + 1);
        }

        return students;
    }  
}
