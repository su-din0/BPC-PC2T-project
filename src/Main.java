import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        TelecomStudent telecomStudent = database.addTelecomStudent("Jan", "Novák", new Date());
        CybersecStudent cybersecStudent = database.addCybersecStudent("Petr", "Svoboda", new Date());

        database.printAllStudents();
        
        System.out.println("test");

    }
}