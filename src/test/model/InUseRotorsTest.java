package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InUseRotorsTest {
    InUseRotors testCase;

    @BeforeEach
    void runBefore() {
        testCase = new InUseRotors();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCase.getRotorCount());
        int i = testCase.encodeSingle(0);
        assertEquals(4, i);
    }

    @Test
    void testAddRotorSingle() {
        testCase.addRotor(1,0);
        int i = testCase.encodeSingle(0);
        assertEquals(20, i);
        i= testCase.encodeSingle(1);
        assertEquals(13, i);
        i = testCase.encodeSingle(12);
        assertEquals(7, i);
    }

    @Test
    void testAddRotorMultiple() {
        testCase.addRotor(1,0);
        testCase.addRotor(2,0);
        testCase.addRotor(1,0);
        testCase.addRotor(2,0);
        int i = testCase.encodeSingle(0);
        assertEquals(8, i);
        assertEquals(4, testCase.getRotorCount());
    }

    @Test
    void testGetStartPoints() {
        testCase.addRotor(1,0);
        testCase.addRotor(2,2);
        testCase.addRotor(1,3);
        testCase.addRotor(2,7);
        List<Integer> startPoints = testCase.returnStartPoints();
        assertEquals(0, startPoints.get(0));
        assertEquals(2, startPoints.get(1));
        assertEquals(3, startPoints.get(2));
        assertEquals(7, startPoints.get(3));
    }

    @Test
    void testStepRotorsNoRollOver() {
        testCase.addRotor(1,0);
        testCase.addRotor(2,2);
        testCase.addRotor(1,3);
        testCase.addRotor(2,7);
        List<Integer> startPoints = testCase.returnCurrentSettings();
        assertEquals(0, startPoints.get(0));
        assertEquals(2, startPoints.get(1));
        assertEquals(3, startPoints.get(2));
        assertEquals(7, startPoints.get(3));
        testCase.stepRotors();
        startPoints = testCase.returnCurrentSettings();
        assertEquals(1, startPoints.get(0));
        assertEquals(2, startPoints.get(1));
        assertEquals(3, startPoints.get(2));
        assertEquals(7, startPoints.get(3));
    }

    @Test
    void testStepRotorsRollOver() {
        testCase.addRotor(1,25);
        testCase.addRotor(2,25);
        testCase.addRotor(1,3);
        testCase.addRotor(2,7);

        testCase.stepRotors();
        List<Integer> startPoints = testCase.returnCurrentSettings();
        assertEquals(0, startPoints.get(0));
        assertEquals(0, startPoints.get(1));
        assertEquals(4, startPoints.get(2));
        assertEquals(7, startPoints.get(3));
    }

    @Test
    void testStepRotorsRollOverStopsBefore() {
        testCase.addRotor(1,25);
        testCase.addRotor(2,25);
        testCase.addRotor(1,3);
        testCase.addRotor(2,25);

        testCase.stepRotors();
        List<Integer> startPoints = testCase.returnCurrentSettings();
        assertEquals(0, startPoints.get(0));
        assertEquals(0, startPoints.get(1));
        assertEquals(4, startPoints.get(2));
        assertEquals(25, startPoints.get(3));
    }

    @Test
    void testEncodeFullStringNoSpaces() {
        testCase.addRotor(1,25);
        testCase.addRotor(2,25);
        String test = "ABCDEFGHIJKL";

        String output = testCase.encodeFullMessage(test);
        assertEquals("KYEVFCRRFSLO", output);
        List<Integer> returnSettings = testCase.returnCurrentSettings();
        assertEquals(11, returnSettings.get(0));
        assertEquals(0, returnSettings.get(1));
    }

    @Test
    void testEncodeStringWithSpaces() {
        testCase.addRotor(1,25);
        testCase.addRotor(2,25);
        String test = "ABCDE FGH IJK L";

        String output = testCase.encodeFullMessage(test);
        assertEquals("KYEVFCRRFSLO", output);
        List<Integer> returnSettings = testCase.returnCurrentSettings();
        assertEquals(11, returnSettings.get(0));
        assertEquals(0, returnSettings.get(1));
    }

    @Test
    void testReturnRotorData() {
        testCase.addRotor(1,25);
        testCase.addRotor(2,16);
        testCase.addRotor(3,12);
        testCase.addRotor(2,3);

        List<Integer> testNameReturn = testCase.returnRotorNames();
        assertEquals(1, testNameReturn.get(0));
        assertEquals(2, testNameReturn.get(1));
        assertEquals(3, testNameReturn.get(2));
        assertEquals(2, testNameReturn.get(3));

        testCase.stepRotors();
        List<Integer> testSettingReturn = testCase.returnCurrentSettings();
        assertEquals(0, testSettingReturn.get(0));
        assertEquals(17, testSettingReturn.get(1));
        assertEquals(12, testSettingReturn.get(2));
        assertEquals(3, testSettingReturn.get(3));

        List<Integer> testStartReturn = testCase.returnStartPoints();
        assertEquals(25, testStartReturn.get(0));
        assertEquals(16, testStartReturn.get(1));
        assertEquals(12, testStartReturn.get(2));
        assertEquals(3, testStartReturn.get(3));
    }

    @Test
    void testRotorReset() {
        testCase.addRotor(1,25);
        testCase.addRotor(2,16);
        testCase.addRotor(3,12);
        testCase.addRotor(2,3);

        testCase.resetRotorDetails(0, 12);
        testCase.resetRotorDetails(1, 8);
        testCase.resetRotorDetails(2, 22);
        testCase.resetRotorDetails(3, 16);

        List<Integer> testSettingReturn = testCase.returnCurrentSettings();
        assertEquals(12, testSettingReturn.get(0));
        assertEquals(8, testSettingReturn.get(1));
        assertEquals(22, testSettingReturn.get(2));
        assertEquals(16, testSettingReturn.get(3));

        List<Integer> testStartReturn = testCase.returnStartPoints();
        assertEquals(12, testStartReturn.get(0));
        assertEquals(8, testStartReturn.get(1));
        assertEquals(22, testStartReturn.get(2));
        assertEquals(16, testStartReturn.get(3));
    }

    @Test
    void testRotorDelete() {
        testCase.addRotor(1,25);
        testCase.addRotor(2,16);
        testCase.addRotor(3,12);
        testCase.addRotor(2,3);
        assertEquals(4, testCase.getRotorCount());

        testCase.deleteRotor(0);
        assertEquals(3, testCase.getRotorCount());
        List<Integer> testNameReturn = testCase.returnRotorNames();
        assertEquals(2, testNameReturn.get(0));
        assertEquals(3, testNameReturn.get(1));
        assertEquals(2, testNameReturn.get(2));

        testCase.deleteRotor(2);
        assertEquals(2, testCase.getRotorCount());
        testNameReturn = testCase.returnRotorNames();
        assertEquals(2, testNameReturn.get(0));
        assertEquals(3, testNameReturn.get(1));
    }

    @Test
    void testAvailableRotors() {
        Rotor r = new Rotor();
        assertEquals(r.getAvailableRotors(), testCase.getAvailableRotorTypes());
    }

    @Test
    void testSingleReturn() {
        testCase.addRotor(1,25);
        testCase.addRotor(2,18);
        testCase.addRotor(1,3);
        testCase.addRotor(2,2);

        assertEquals(25, testCase.returnStartPoint(0));
        assertEquals(18, testCase.returnStartPoint(1));
        assertEquals(3, testCase.returnStartPoint(2));
        assertEquals(2, testCase.returnStartPoint(3));

    }
}
