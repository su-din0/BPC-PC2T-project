import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class CybersecStudent extends Student {
    public CybersecStudent(String firstName, String lastName, Date birthday) {
        super(firstName, lastName, birthday);
    }

    @Override
    public void performSkill() {
        try {
            String hash = this.nameToHash(super.getFirstName(), super.getLastName());
            System.out.println(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private String nameToHash(String firstName, String secondName) throws NoSuchAlgorithmException {
        String fullName = firstName + " " + secondName;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(fullName.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
