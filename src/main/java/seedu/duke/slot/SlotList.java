package seedu.duke.slot;

import java.time.LocalTime;
import seedu.duke.ItemList;
import java.util.ArrayList;

import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

/**
 * This class represents the timetable used to store and organize the schedule slots.
 */
public class SlotList extends ItemList {
    private ArrayList<Slot> slots;

    /**
     * Constructs a SlotList object with an empty ArrayList to store Slot objects.
     */
    public SlotList() {
        this.slots = new ArrayList<>();
    }

    /**
     * Constructs the SlotList object containing an ArrayList to store Slot objects.
     * This constructor is used when loading Slots from a text file.
     *
     * @param slotStrings the list of strings of Slots.
     */
    public SlotList(ArrayList<String> slotStrings) {
        this.slots = new ArrayList<>();
        loadSlotList(slotStrings);
    }

    private void loadSlotList(ArrayList<String> slots) {
        for (String slot: slots) {
            loadSlot(slot);
        }
    }

    /**
     * This method adds the given Slot object to the timetable.
     *
     * @param slot the slot to be added to the list.
     */
    public void addSlot(Slot slot)  {
        slots.add(slot);
    }

    private void loadSlot(String line) {
        try {
            slots.add(Slot.initSlot(line));
        } catch (IndexOutOfBoundsException e) {
            // TODO: to be fixed
            System.out.println("Error");
        }
    }

    /**
     * Returns the the data of all Slots in the list to be saved in the text file.
     *
     * @return the data of all the Slots.
     */

    public String getData() {
        StringBuilder data = new StringBuilder();
        for (Slot slot : slots) {
            data.append(slot.getExport()).append(System.lineSeparator());
        }
        return data.toString().trim();
    }

    /**
     * Returns the number of slots in the timetable.
     *
     * @return the size of the list.
     */
    public int getSize() {
        return slots.size();
    }

    /**
     * Returns a slot based on the index.
     *
     * @param index The index of the slot in the list.
     * @return The slot with the corresponding index in the list.
     */
    public Slot getSlot(int index) {
        return slots.get(index);
    }

    /**
     * Returns the slot list.
     *
     * @return The slot list.
     */
    public ArrayList<Slot> getSlotList() {
        return slots;
    }

    /**
     * This method deletes the slot from the list.
     *
     * @param slot The slot to be deleted.
     */
    public void deleteSlot(Slot slot) {
        slots.remove(slot);
    }

    public static void printSlotsInADay(ArrayList<Slot> slots, String day) {
        boolean hasSlotOnDay = false;

        System.out.println(day);

        for (Slot s: slots) {
            if (s.getDay().equals(day)) {
                System.out.println(s.toString());
                hasSlotOnDay = true;
            }
        }

        if (hasSlotOnDay == false) {
            System.out.println("No lessons");
        }

        System.out.println();
    }

    public void printTimetable(ArrayList<Slot> slots) {
        for (String d: Slot.days) {
            printSlotsInADay(slots, d);
        }
    }

    public void printLessonAtTime(ArrayList<Slot> slots, String dayInput) throws DukeException {
        if (slots.size() == 0) {
            throw new DukeException(DukeExceptionType.EMPTY_TIMETABLE);
        } else if (dayInput == null) {
            throw new DukeException(DukeExceptionType.INVALID_TIMETABLE_DAY);
        } else if (dayInput.equals("ALL") == true) {
            printTimetable(slots);
            return;
        }

        System.out.println("Lessons for " + dayInput);
        printSlotsInADay(slots, dayInput);
    }
}
