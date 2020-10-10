package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.exception.DukeExceptionType;
import seedu.duke.slot.Slot;
import seedu.duke.exception.DukeException;
import seedu.duke.slot.SlotList;

import java.time.DateTimeException;
import java.time.LocalTime;

/**
 * Represents the user command exit the Duke program.
 */
public class AddSlotCommand extends Command {
    public static final String ADD_KW = "slotadd";
    public LocalTime startTime;
    public LocalTime endTime;
    public String day;
    public String title;

    /**
     * Constructs a new AddSlotCommand instance and stores the information of the slot given by the input.
     *
     * @param command The user input command.
     * @throws DukeException thrown if input command is invalid.
     */
    public AddSlotCommand(String command) throws DukeException {
        String[] parts = command.split(" ");
        try {
            startTime = LocalTime.parse(parts[1]);
            endTime = LocalTime.parse(parts[2]);
            day = parts[3];
            title = command.substring(command.indexOf(parts[3]) + parts[3].length());
        } catch (DateTimeException e) {
            throw new DukeException(DukeExceptionType.INVALID_TIME_FORMAT);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(DukeExceptionType.INVALID_SLOT_INPUT);
        }
    }

    /**
     * Adds the slot to the slot list and saves the slots list in the text file.
     *
     * @param items The list of slots.
     * @param ui The user interface.
     * @param storage The storage for saving and loading.
     */
    @Override
    public void execute(ItemList items, Ui ui, Storage storage) {
        SlotList slots = (SlotList) items;
        Slot slot = new Slot(startTime, endTime, day, title);
        slots.addSlot(slot);
        ui.print("Added slot: " + day + " [" + startTime + "-" + endTime + "] "
                + title + System.lineSeparator());
        try {
            storage.save(slots.getData());
        } catch (DukeException e) {
            e.printStackTrace();
        }
    }
}
