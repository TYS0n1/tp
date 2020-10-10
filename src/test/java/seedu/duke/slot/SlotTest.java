package seedu.duke.slot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

class SlotTest {

    @Test
    void getDay_oneTimeSlot_returnsDay() {
        Slot s = new Slot(LocalTime.parse("10:00"), LocalTime.parse("11:00"), "Monday", "Test");
        assertEquals(s.getDay(), "Monday");
    }

}