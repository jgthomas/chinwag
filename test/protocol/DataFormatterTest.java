package protocol;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataFormatterTest {

    @Test
    public void emptyStringFormatting() {
        String expected = "";
        String actual = DataFormatter.listToString(new ArrayList<>());
        assertEquals(expected, actual);
    }

    @Test
    public void singleNameFormatting() {
        String expected = "dave£££";
        List<String> userNames = new ArrayList<>();
        userNames.add("dave");
        String actual = DataFormatter.listToString(userNames);
        assertEquals(expected, actual);
    }

    @Test
    public void multiNameFormatting() {
        String expected = "dave£££paul£££jenny£££";
        List<String> userNames = new ArrayList<>();
        userNames.add("dave");
        userNames.add("paul");
        userNames.add("jenny");
        String actual = DataFormatter.listToString(userNames);
        assertEquals(expected, actual);
    }
}