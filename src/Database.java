import java.io.*;
import java.util.Date;
import java.util.HashMap;

public class Database {
    private HashMap<Integer, Student> students;

    public Database() {
        this.students = new HashMap<>();
    }

    public Student findStudentById(Integer id) {
        this.validate(id);

        Student student = this.students.get(id);
        if (student == null) throw new IllegalStateException("Student s ID " + id + " nebyl nalezen.");

        return student;
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

    public void removeStudentById(Integer id) {
        this.validate(id);

        Student student = this.students.remove(id);
        if (student == null) throw new IllegalStateException("Student s ID " + id + " nebyl nalezen.");
    }

    public void executeSkillById(Integer id) {
        this.validate(id);

        Student student = this.findStudentById(id);
        student.performSkill();
    }

    public void printAllStudents() {
        if (this.students.isEmpty()) throw new IllegalStateException("Databáze je prázdná.");

        for (Student student : this.students.values()) {
            System.out.println(student);
        }
    }

    public void loadFromDatabase()
    {

    }

    public void saveToDatabase()
    {

    }

    public void saveStudentToFile(Integer id, String fileName) {
        Student student = this.findStudentById(id);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(student);
            System.out.println("Student byl úspěšně uložen do souboru: " + fileName);
        } catch (IOException e) {
            System.err.println("Chyba při ukládání studenta do souboru: " + e.getMessage());
        }
    }

    public void loadStudentFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Student student = (Student) ois.readObject();
            this.students.put(student.getId(), student);
            System.out.println("Student byl úspěšně načten ze souboru: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Chyba při načítání studenta ze souboru: " + e.getMessage());
        }
    }

    private void validate(Integer id) {
        if (this.students.isEmpty()) throw new IllegalStateException("Databáze je prázdná.");
        if (id == null) throw new IllegalArgumentException("ID studenta nesmí být null.");
        if (id < 0) throw new IllegalArgumentException("ID studenta musí být kladné číslo.");
    }
}
