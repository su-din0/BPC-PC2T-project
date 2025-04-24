package Database_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.URL);
             Statement stmt = conn.createStatement()) {

            String createStudents = """
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    birth_year INTEGER,
                    study_program TEXT
                );
                """;

            String createGrades = """
                CREATE TABLE IF NOT EXISTS grades (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_id INTEGER,
                    grade INTEGER,
                    FOREIGN KEY(student_id) REFERENCES students(id)
                );
                """;

            stmt.execute(createStudents);
            stmt.execute(createGrades);

            
        } catch (SQLException e) {
            System.err.println("Chyba při vytváření tabulek: " + e.getMessage());
        }
    }
}
