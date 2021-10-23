package persistence;

import exceptions.NoRotorsLoadedException;
import model.InUseRotors;

import model.RotorCase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonReaderTest {

    @Test
    void testNoFileFoundInUseRotors() {
        JsonReader reader = new JsonReader("./data/noFileFound.json");
        try {
            InUseRotors iur = reader.readFile();
            fail("Should have caught IOException");
        } catch (NoRotorsLoadedException e) {
            fail("Caught no rotors loaded, should have caught IO");
        } catch (IOException e) {
            //expected outcome, IOException caught
        }
    }

    @Test
    void testNoFileFoundLoadRotorCase() {
        RotorCase r = new RotorCase("./data/noFileFound.json");
        assertEquals(3, r.availableArrays());
    }

    @Test
    void testLoadSettingsNoRotorsInFile() {
        JsonReader reader = new JsonReader("./data/testFileNoRotorsToLoad.json");

        try {
            InUseRotors iur = reader.readFile();
            fail("Should have produced exception");
        } catch (NoRotorsLoadedException e) {
            //expected
        } catch (IOException e) {
            fail("Caught IOException, should have caught NoRotorsLoaded");
        }
    }

    @Test
    void testLoadSettings() {
        JsonReader reader = new JsonReader("./data/testFileGeneralRotorData.json");

        try {
            InUseRotors iur = reader.readFile();
            assertEquals(2, iur.getRotorCount());
            List<Integer> checkName = iur.returnRotorNames();
            List<Integer> checkSettings = iur.returnStartPoints();
            assertEquals(4, checkName.get(0));
            assertEquals(5, checkName.get(1));
            assertEquals(0, checkSettings.get(0));
            assertEquals(4, checkSettings.get(1));
        } catch (NoRotorsLoadedException e) {
            fail("Caught unwanted NoRotorsLoadedException");
        } catch (IOException e) {
            fail("Caught IOException, should have caught NoRotorsLoadedException");
        }
    }















    @Test
    void testLoadRotorsFromFile() {
        RotorCase r = new RotorCase("./data/testFileLoadRotors.json");
        assertEquals(2, r.availableArrays());
    }


}
