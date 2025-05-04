package bpc_pct2_projekt;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Database_sql.DatabaseInitializer;
import Database_sql.SqlDatabaseHandler;

public class Database {
    private HashMap<Integer, Student> students;

    public Database() {
        this.students = new HashMap<>();
    }

    public TelecomStudent addTelecomStudent(String firstName, String lastName, Date birthday) {
        TelecomStudent student = new TelecomStudent(firstName, lastName, birthday);
        this.students.put(student.getId(), student);

        return student;
    }

    public CybersecStudent addCybersecStudent(String firstName, String lastName, Date birthday) {
        CybersecStudent student = new CybersecStudent(firstName, lastName, birthday);
        this.students.put(student.getId(), student);

        return student;
    }

    public Student findStudentById(Integer id) {
        this.validate(id);

        Student student = this.students.get(id);
        if (student == null) throw new IllegalStateException("Student s ID " + id + " nebyl nalezen.");

        return student;
    }

    public void removeStudentById(Integer id) {
        this.validate(id);

        Student student = this.students.remove(id);
        if (student == null) throw new IllegalStateException("Student s ID " + id + " nebyl nalezen.");
    }

    public void printStudentById(Integer id) {
        Student student = this.students.get(id);

        if (student == null) {
            System.out.println("Student s ID " + id + " nebyl nalezen.");
            return;
        }

        System.out.println(formatStudentInfo(student));
    }

    public void executeSkillById(Integer id) {
        this.validate(id);

        try {
            Student student = this.findStudentById(id);
            student.performSkill();
        } catch (IllegalStateException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }

    public void printAllStudentsSortedByLastName() {
        if (this.students.isEmpty()) {
            System.out.println("Databáze je prázdná. Žádní studenti k vypsání.");
            return;
        }


        List<Student> studentList = new ArrayList<>(this.students.values());


        studentList.sort(Comparator.comparing(Student::getLastName));

        System.out.println("--- Cybersecurity studenti ---");
        for (Student s : studentList) {
            if (s instanceof CybersecStudent) {
                printStudentSummary(s);
            }
        }

        System.out.println("\n--- Telekomunikační studenti ---");
        for (Student s : studentList) {
            if (s instanceof TelecomStudent) {
                printStudentSummary(s);
            }
        }

    }

    public void AverageGradeByGroup() {
        if (this.students.isEmpty()) {
            throw new IllegalStateException("Databáze je prázdná.");
        }

        int cyberSum = 0, cyberCount = 0;
        int telecomSum = 0, telecomCount = 0;

        for (Student s : this.students.values()) {
            if (s instanceof CybersecStudent) {
                cyberSum += s.getAverageGrade();
                cyberCount++;
            } else if (s instanceof TelecomStudent) {
                telecomSum += s.getAverageGrade();
                telecomCount++;
            }
        }

        printGroupStats("Cybersecurity studenti", cyberSum, cyberCount);
        printGroupStats("Telekomunikační studenti", telecomSum, telecomCount);
    }


    public void GroupCount() {
        if (this.students.isEmpty()) {
            throw new IllegalStateException("Databáze je prázdná.");
        }

        int cyberCount = 0;
        int telecomCount = 0;

        for (Student s : this.students.values()) {
            if (s instanceof CybersecStudent) cyberCount++;
            else if (s instanceof TelecomStudent) telecomCount++;
        }

        System.out.println("Cybersecurity studentů: " + cyberCount);
        System.out.println("Telekomunikační studentů: " + telecomCount);
    }


    public void loadFromDatabase() {
        try {
            DatabaseInitializer.initializeDatabase();
            List<Student> loadedStudents = SqlDatabaseHandler.loadAllStudents();
            students.clear();
            for (Student s : loadedStudents) {
                students.put(s.getId(), s);
            }
            System.out.println("Načteno studentů z DB: " + students.size());
        } catch (SQLException e) {
            System.err.println("Chyba při načítání studentů: " + e.getMessage());
        }
    }


    public void saveToDatabase() {
        try {
            SqlDatabaseHandler.saveAllStudents(students);
            System.out.println("Studenti a známky byly úspěšně uloženy.");
        } catch (SQLException e) {
            System.err.println("Chyba při ukládání studentů: " + e.getMessage());
        }

    }

    public void saveStudentToFile(Integer id, String fileName) {
        this.validate(id);

        Student student = this.students.get(id);
        if (student == null) {
            throw new IllegalStateException("Student s ID " + id + " nebyl nalezen.");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(student);
            System.out.println("Student byl úspěšně uložen do souboru: " + fileName);
        } catch (IOException e) {
            System.err.println("Chyba při ukládání studenta do souboru: " + e.getMessage());
        }
    }

    public void loadStudentFromFile(String fileName, int expectedId) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Student student = (Student) ois.readObject();

            if (student.getId() != expectedId) {
                System.out.println("ID studenta v souboru nesouhlasí. Očekáváno: " + expectedId + ", nalezeno: " + student.getId());
                return;
            }

            if (this.students.containsKey(student.getId())) {
                System.out.println("Student s ID " + student.getId() + " už existuje v databázi. Načtení bylo přerušeno.");
                return;
            }

            System.out.println(student.getId());

            this.students.put(student.getId(), student);
            System.out.println("Student byl úspěšně načten ze souboru: " + fileName);
            System.out.println("Načtený student: " + student.getFirstName() + " " + student.getLastName() + " (ID: " + student.getId() + ")");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Chyba při načítání studenta ze souboru: " + e.getMessage());
        }
    }

    private void validate(Integer id) {
        if (this.students.isEmpty()) throw new IllegalStateException("Databáze je prázdná.");
        if (id == null) throw new IllegalArgumentException("ID studenta nesmí být null.");
        if (id < 0) throw new IllegalArgumentException("ID studenta musí být kladné číslo.");
    }

    private String formatStudentInfo(Student student) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return "\n--- Informace o studentovi ---\n" +
                "ID: " + student.getId() + "\n" +
                "Jméno: " + student.getFirstName() + " " + student.getLastName() + "\n" +
                "Datum narození: " + dateFormat.format(student.getBirthday()) + "\n" +
                "Studijní průměr: " + student.getAverageGrade();
    }

    private void printGroupStats(String groupName, int sum, int count) {
        System.out.println("\n--- " + groupName + " ---");
        if (count == 0) {
            System.out.println("Žádní studenti v této skupině.");
            return;
        }

        double average = (double) sum / count;
        System.out.printf("Průměr: %.2f\n", average);
    }

    private void printStudentSummary(Student student) {
        Date birthday = student.getBirthday();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(birthday);

        System.out.println(student.getId() + " " + student.getFirstName() + ", " +
                student.getLastName() + ", " +
                year + ", " +
                student.getAverageGrade());
    }
}
