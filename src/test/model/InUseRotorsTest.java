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
        int i = testCase.encode(0);
        assertEquals(0, i);
    }

    @Test
    void testAddRotorSingle() {
        testCase.addRotor(1,0);
        int i = testCase.encode(0);
        assertEquals(0, i);
        i= testCase.encode(1);
        assertEquals(25, i);
        i = testCase.encode(12);
        assertEquals(12, i);
    }

    @Test
    void testAddRotorMultiple() {
        testCase.addRotor(1,0);
        testCase.addRotor(2,0);
        testCase.addRotor(1,0);
        testCase.addRotor(2,0);
        int i = testCase.encode(0);
        assertEquals(0, i);
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
}
