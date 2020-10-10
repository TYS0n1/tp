package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Ui;
import seedu.duke.Storage;
import seedu.duke.Parser;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;
import seedu.duke.slot.SlotList;

public class ChangeModeCommand extends Command {
    public static final String MODE_KW = "mode";
    public int setToMode;

    /**
     * Constructs a new ChangeModeCommand instance.
     */
    public ChangeModeCommand(String command) {
        setToMode = getModeFromCommand(command);
    }


    @Override
    public void execute(ItemList items, SlotList slotList, Ui ui, Storage bookmarkStorage, Storage slotStorage)
            throws DukeException {
        if (setToMode == 1) {
            Parser.programMode = 1;
            ui.print("Changing to bookmark mode" + System.lineSeparator());
        } else if (setToMode == 2) {
            Parser.programMode = 2;
            ui.print("Changing to timetable mode" + System.lineSeparator());
        } else {
            throw new DukeException(DukeExceptionType.INVALID_MODE);
        }
    }

    public int getModeFromCommand(String input) {
        int outputData;

        if (input.length() <= 5) {
            return 0;
        }

        String modeData = input.substring(5);

        if (modeData.equals("bookmark") == true) {
            outputData = 1;
        } else if (modeData.equals("timetable") == true) {
            outputData = 2;
        } else {
            outputData = 0;
        }

        return outputData;
    }
}
