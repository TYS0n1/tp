package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.slot.SlotList;

/**
 * Represents the user command exit the Duke program.
 */
public class ExitCommand extends Command {
    public static final String BYE_KW = "exit";

    /**
     * Constructs a new ExitCommand instance and sets isExitCommand to true.
     */
    public ExitCommand() {
        isExitCommand = true;
    }

    /**
     * Prints the exit screen before the program exits.
     *
     * @param items The list of bookmarks.
     * @param ui The user interface.
     */
    @Override
    public void execute(ItemList items, SlotList slotList, Ui ui, Storage bookmarkStorage, Storage slotStorage) {
        ui.showExitScreen();
    }
}
