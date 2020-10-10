package seedu.duke.command;

import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.slot.Slot;
import seedu.duke.slot.SlotList;
import seedu.duke.exception.DukeException;

public class ShowTimetableCommand extends Command {
    public static final String SHOW_KW = "show";
    public String day;

    /**
     * Constructs a new ShowTimetableCommand instance.
     */
    public ShowTimetableCommand(String command) {
        day = getDayFromCommand(command);
    }

    /**
     * Prints the exit screen before the program exits.
     *
     * @param bookmarks The list of bookmarks.
     * @param ui The user interface.
     * @param storage The storage for saving and loading.
     */

    @Override
    public void execute(BookmarkList bookmarks, SlotList slotList, Ui ui, Storage bookmarkStorage, Storage slotStorage) throws DukeException {
        slotList.printLessonAtTime(slotList.getSlotsList(), day);
    }

    public String getDayFromCommand(String input) {
        String outputData;

        if (input.length() == 4){
            return "ALL";
        } else if (input.substring(4 , 5).equals(" ") == false) {
            return null;
        }

        String dayData = input.substring(5);

        if (dayData.compareToIgnoreCase(Slot.MON) == 0) {
            outputData = Slot.MON;
        } else if (dayData.compareToIgnoreCase(Slot.TUE) == 0) {
            outputData = Slot.TUE;
        } else if (dayData.compareToIgnoreCase(Slot.WED) == 0) {
            outputData = Slot.WED;
        } else if (dayData.compareToIgnoreCase(Slot.THU) == 0) {
            outputData = Slot.THU;
        } else if (dayData.compareToIgnoreCase(Slot.FRI) == 0) {
            outputData = Slot.FRI;
        } else if (dayData.compareToIgnoreCase(Slot.SAT) == 0) {
            outputData = Slot.SAT;
        } else if (dayData.compareToIgnoreCase(Slot.SUN) == 0) {
            outputData = Slot.SUN;
        } else {
            outputData = null;
        }

        return outputData;
    }
}
