import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        TelecomStudent telecomStudent = database.addTelecomStudent("Jan", "Novák", new Date());
        CybersecStudent cybersecStudent = database.addCybersecStudent("Petr", "Svoboda", new Date());

        database.printAllStudents();
<<<<<<< HEAD
        
        System.out.println("test");
        System.out.println("test");
        System.out.println("test");
=======
        database.printAllStudents();
>>>>>>> 14bd3e836a23fb12e8e396c0664419b7c589a78f

    }
}