package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import exceptions.NoRotorsLoadedException;
import model.InUseRotors;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest {

    @Test
    void testNoFileFound() {
        JsonWriter json = new JsonWriter("./data/invalid\0file.json");
        try {
            json.open();
            fail("Exception expected before this point");
        } catch (FileNotFoundException e) {
            //expected behaviour
        }
    }

    @Test
    void testWrite() {
        JsonWriter jsonWrite = new JsonWriter("./data/testFileWriting.json");
        JsonReader jsonRead = new JsonReader("./data/testFileWriting.json");
        InUseRotors iur = new InUseRotors();
        iur.addRotor(1,0);
        iur.addRotor(2,4);
        iur.addRotor(3,22);
        try {
            jsonWrite.open();
            jsonWrite.write(iur);
            jsonWrite.close();

            InUseRotors iurRead = jsonRead.readFile();

            List<Integer> checkName = iurRead.returnRotorNames();
            List<Integer> checkSettings = iurRead.returnStartPoints();
            assertEquals(1, checkName.get(0));
            assertEquals(2, checkName.get(1));
            assertEquals(4, checkSettings.get(1));
            assertEquals(22, checkSettings.get(2));
        } catch (FileNotFoundException e) {
            fail("Unexpected Exception thrown");
        } catch (NoRotorsLoadedException e) {
            fail("NoRotorsLoaded exception thrown, unexpected behaviour");
        } catch (IOException e) {
            fail("IO exception thrown, unexpected behaviour");
        }
    }
}
