package bpc_pct2_projekt;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class CybersecStudent extends Student implements Serializable {


    private static final long serialVersionUID = 1L;

    public CybersecStudent(String firstName, String lastName, Date birthday) {
        super(firstName, lastName, birthday);
    }

    public CybersecStudent() {
        super("", "", new Date());
    }

    @Override
    public String getStudyProgram() {
        return "kyberbezpecnost";
    }

    @Override
    public void performSkill() {
        try {
            String hash = this.nameToHash(super.getFirstName(), super.getLastName());
            System.out.println("Hash:" + hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private String nameToHash(String firstName, String secondName) throws NoSuchAlgorithmException {
        String fullName = firstName + " " + secondName;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(fullName.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
