import java.util.Date;

public class Main {
    public static void main(String[] args) {
        CybersecStudent cybersecStudent = new CybersecStudent("Jan", "Novák", new Date());
        cybersecStudent.performSkill();

        TelecomStudent telecomStudent = new TelecomStudent("Petr", "Čech", new Date());
        telecomStudent.performSkill();
    }
}