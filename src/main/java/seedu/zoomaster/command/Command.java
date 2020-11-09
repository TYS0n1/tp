package seedu.zoomaster.command;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.BookmarkList;

import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.slot.Timetable;

//@@author xingrong123
/**
 * Represents the a command which executes a procedure.
 */
public abstract class Command {

    protected boolean isExitCommand = false;

    /**
     * Execution of command depends on which command subclass the command belongs to.
     */
    public abstract void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws ZoomasterException;

    /**
     * Returns true if the command is an ExitCommand.
     */
    public boolean isExit() {
        return isExitCommand;
    }
}

