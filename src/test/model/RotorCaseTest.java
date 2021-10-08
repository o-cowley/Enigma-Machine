package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RotorCaseTest {
    RotorCase rc;

    @BeforeEach
    public void setUpRotorCase() {
        rc = new RotorCase();
    }

    @Test
    public void testConstructor() {
        List<Integer> rotor1 = rc.getRotorArray(0);
        assertEquals(26, rotor1.size());
        List<Integer> rotor4 = rc.getRotorArray(3);
        assertEquals(26, rotor4.size());
        List<Integer> rotorReflector = rc.getReflector();
        assertEquals(26, rotorReflector.size());
    }

    @Test
    public void testValuesInRotor() {
        List<Integer> rotor4 = rc.getRotorArray(0);
        List<Integer> expectedData = new ArrayList(Arrays.asList(4, 8, 9, 2, 6, 1, -3, 8, 13, 16, 3, 8, 2, 9,
                10, -8, 7, 3, 0, -4, -20, -13, -21, -6, -22, -16));
        for (int i = 0 ; i < rotor4.size() ; i++) {
            assertEquals(expectedData.get(i), rotor4.get(i));
        }
    }
}
