package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.slot.SlotList;
import seedu.duke.exception.DukeException;

public abstract class Command {

    protected boolean isExitCommand = false;

    /**
     * Execution of command depends on which command subclass the command belongs to.
     */
    public abstract void execute(ItemList items, SlotList slotList, Ui ui, Storage bookmarkStorage, Storage slotStorage) throws DukeException;

    /**
     * Returns true if the command is an ExitCommand.
     */
    public boolean isExit() {
        return isExitCommand;
    }
}
