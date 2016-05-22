package Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTextBtween {

    public GetTextBtween() {
    }

    public String GetTxtBtween(String text, String pattern1, String pattern2) {
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(text);
        while (m.find()) {
            return (m.group(1));
        }
        return null;
    }

    public String[] SplitAll(String text, String pattern1) {
        Boolean FirstLoop = true;
        String[] ListClientsString = text.split(pattern1);
        return ListClientsString;
    }
}
