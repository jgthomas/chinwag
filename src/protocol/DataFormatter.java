package protocol;

import java.util.List;

public final class DataFormatter {
    static String listToString(List<String> lst) {
        String sep = Token.SEPARATOR.getValue();
        StringBuilder sb = new StringBuilder();
        for (String s : lst) {
            sb.append(s);
            sb.append(sep);
        }
        return sb.toString();
    }
}
