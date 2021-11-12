package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotorTest {
    Rotor rotor;
    Rotor reflector;

    @BeforeEach
    public void runBefore() {
        rotor = new Rotor(1);
        reflector = new Rotor();
        reflector.setReflector();
    }

    @Test
    public void testRotorConstructor() {
        rotor = new Rotor(2);
        assertEquals(8, rotor.getRotorOutData(1));
        assertEquals(8, rotor.getRotorReturnData(1));
        assertEquals(-4, reflector.getRotorOutData(4));
        assertEquals(2, rotor.getLabel());
    }
    @Test
    public void testRotorSetting() {
        assertEquals(0, rotor.getSteps());
        rotor.setRotor(7);
        assertEquals(7, rotor.getSteps());
        rotor.stepRotor();
        assertEquals(8, rotor.getSteps());
        assertEquals(7, rotor.getStartPoint());
    }

    @Test
    public void testLetterEncryption() {
        assertEquals(4, rotor.shiftLetter(0, false));
        rotor.stepRotor();
        assertEquals(9, rotor.shiftLetter(0, false));
        rotor.stepRotor();
        rotor.stepRotor();
        rotor.stepRotor();
        assertEquals(7, rotor.shiftLetter(0, false));
        rotor.setRotor(25);
        assertEquals(10, rotor.shiftLetter(0, false));
        rotor.setRotor(16);
        int testInt = rotor.shiftLetter(0, false);
        assertEquals(7, testInt);
        rotor.setRotor(3);
        testInt = rotor.shiftLetter(0, true);
        assertEquals(3, testInt);
        rotor.setRotor(2);
        testInt = reflector.shiftLetter(2, false);
        assertEquals(12, testInt);
    }

    @Test
    public void testAdjustedModOperation() {
        rotor.setRotor(25);
        assertEquals(10, rotor.shiftLetter(0, false));
        rotor.stepRotor();
        assertEquals(4, rotor.shiftLetter(0, false));
        rotor.setRotor(10);
        assertEquals(15, rotor.shiftLetter(25, false));
    }
    @Test
    public void testRealRotor() {
        rotor = new Rotor(1);
        rotor.setRotor(0);
        assertEquals(19, rotor.shiftLetter(11, false));
        rotor.stepRotor();
        assertEquals(13, rotor.shiftLetter(11, false));
        assertEquals(3, rotor.shiftLetter(25, false));
        rotor.setRotor(0);
        assertEquals(9, rotor.shiftLetter(25, false));
    }

    @Test
    public void multipleShiftsNoSteps() {
        Rotor rotor1 = new Rotor(1);
        Rotor rotor2 = new Rotor(2);
        int test = rotor2.shiftLetter
                    (rotor1.shiftLetter
                        (rotor2.shiftLetter
                                (rotor1.shiftLetter
                                        (0, false), false), false), false);
        assertEquals(25, test);
        int test2 = rotor1.shiftLetter
                    (rotor2.shiftLetter
                        (rotor1.shiftLetter
                                (rotor2.shiftLetter
                                        (3, true), true), true), true);
        assertEquals(8, test2);
    }

    @Test
    public void testToString() {
        Rotor rotor1 = new Rotor(1);
        rotor1.setRotor(12);
        Rotor rotor2 = new Rotor(2);
        rotor2.setRotor(15);

        assertEquals("Rotor: 1 Setting: 12", rotor1.toString());
        assertEquals("Rotor: 2 Setting: 15", rotor2.toString());

    }
}