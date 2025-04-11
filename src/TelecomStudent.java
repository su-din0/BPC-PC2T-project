import java.util.Date;
import java.util.Map;

public class TelecomStudent extends Student {
    public TelecomStudent(String firstName, String lastName, Date birthday) {
        super(firstName, lastName, birthday);
    }

    @Override
    public void performSkill() {
        String morseCode = this.nameToMorse(super.getFirstName(), super.getLastName());
        System.out.println(morseCode);
    }

    private String nameToMorse(String firstName, String lastName) {
        String text = firstName + " " + lastName;

        String normalizedText = this.removeDiacritics(text).toUpperCase();

        Map<Character, String> morseMap = Map.ofEntries(
                Map.entry('A', ".-"),    Map.entry('B', "-..."),  Map.entry('C', "-.-."),
                Map.entry('D', "-.."),   Map.entry('E', "."),     Map.entry('F', "..-."),
                Map.entry('G', "--."),   Map.entry('H', "...."),  Map.entry('I', ".."),
                Map.entry('J', ".---"),  Map.entry('K', "-.-"),   Map.entry('L', ".-.."),
                Map.entry('M', "--"),    Map.entry('N', "-."),    Map.entry('O', "---"),
                Map.entry('P', ".--."),  Map.entry('Q', "--.-"),  Map.entry('R', ".-."),
                Map.entry('S', "..."),   Map.entry('T', "-"),     Map.entry('U', "..-"),
                Map.entry('V', "...-"),  Map.entry('W', ".--"),   Map.entry('X', "-..-"),
                Map.entry('Y', "-.--"),  Map.entry('Z', "--.."),  Map.entry(' ', "/")
        );

        StringBuilder morse = new StringBuilder();
        for (char c : normalizedText.toCharArray()) {
            morse.append(morseMap.getOrDefault(c, "?")).append(" ");
        }

        return morse.toString().trim();
    }

    private String removeDiacritics(String input) {
        input = input.replace("á", "a").replace("č", "c").replace("ď", "d")
                .replace("é", "e").replace("ě", "e").replace("í", "i")
                .replace("ň", "n").replace("ó", "o").replace("ř", "r")
                .replace("š", "s").replace("ť", "t").replace("ú", "u")
                .replace("ů", "u").replace("ý", "y").replace("ž", "z");

        input = input.replace("Á", "A").replace("Č", "C").replace("Ď", "D")
                .replace("É", "E").replace("Ě", "E").replace("Í", "I")
                .replace("Ň", "N").replace("Ó", "O").replace("Ř", "R")
                .replace("Š", "S").replace("Ť", "T").replace("Ú", "U")
                .replace("Ů", "U").replace("Ý", "Y").replace("Ž", "Z");

        return input;
    }

}
