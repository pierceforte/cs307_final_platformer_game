package data;

import java.util.ArrayList;
import java.util.List;

public class PrettyPrint {
    private String pretty = "";
    private String buffer = "";
    private static final String newline = "\n";
    private static final String basicBuffer = "  ";
    private List<String> last = new ArrayList<>();

    public PrettyPrint(String jsonString) {
        for (int index = 0; index < jsonString.length(); index++) {
            String letter = Character.toString(jsonString.charAt(index));
            pretty = pretty + letter;
            String copy = letter;
            if (index < jsonString.length() - 1 && (copy.concat(jsonString.substring(index + 1, index + 2))).equals("99")) {
                pretty = pretty;
            }
            if (letter.equals("{")) {
                openBracket(letter);
            }
            if (letter.equals("[")) {
                openArray(letter, jsonString.substring(index + 1, index + 2));
            }
            if ((letter.equals("]")) & (index + 1 < jsonString.length())) {
                closeBrackets(letter, jsonString.substring(index + 1, index + 2));
            }
            if (letter.equals(",")) {
                comma(letter, jsonString.substring(index - 1, index), jsonString.substring(index + 1, index + 2));
            }
            if (index < jsonString.length() - 1) {
                indent(letter, jsonString.substring(index + 1, index + 2));
            }
        }
    }

    private void openBracket(String letter) {
        last.add(letter);
        buffer = buffer + basicBuffer;
        pretty = pretty + newline + buffer;
    }

    private void openArray(String letter, String nextLetter) {
        last.add(letter);
        if ((nextLetter).equals("[")) {
            buffer = buffer + basicBuffer;
            pretty = pretty + newline + buffer;
        }
    }

    private void closeBrackets(String letter, String nextLetter) {
        last.remove(last.size() - 1);
        if ((!nextLetter.equals(",") && !(nextLetter.equals("}")) & letter.equals("]"))){
            if (buffer.length() >= 2) buffer = buffer.substring(2);
            pretty = pretty + newline + buffer;
        }
    }

    private void comma(String letter, String lastLetter, String nextLetter) {
        if (lastLetter.equals("]") || lastLetter.equals("}") || last.get(last.size() - 1).equals("{")){

            pretty = pretty + newline + buffer;
        }
    }

    private void indent(String letter, String nextLetter) {
        if (nextLetter.equals("}")) {
            if (buffer.length() >= 2) buffer = buffer.substring(2);
            pretty = pretty + newline + buffer;
        }
    }

    public String getString() {
        return pretty;
    }
}
