package seedu.duke;

import seedu.duke.command.AddBookmarkCommand;
import seedu.duke.command.AddSlotCommand;
import seedu.duke.command.DeleteBookmarkCommand;
import seedu.duke.command.DeleteSlotCommand;
import seedu.duke.command.LaunchBookmarkCommand;
import seedu.duke.command.FindBookmarkCommand;
import seedu.duke.command.ShowTimetableCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.Command;
import seedu.duke.command.ChangeModeCommand;

import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

/**
 * This class deals with making sense of the user command.
 */
public class Parser {
    /*
     * Variable programMode controls the mode Zoomaster program is in.
     * programMode == 0: Main menu mode
     * programMode == 1: Bookmark mode
     * programMode == 2: Timetable mode
     */
    public static int programMode = 0;

    /**
     * Creates and returns the command based on user input.
     *
     * @param input full user input.
     * @return command.
     * @throws DukeException If input command is unknown.
     */
    public static Command parse(String input) throws DukeException {
        Command command = createCommand(input);
        return command;
    }

    private static Command createCommand(String input) throws DukeException {
        Command command;

        if (input.compareToIgnoreCase(ExitCommand.BYE_KW) == 0) {
            command = new ExitCommand();
        } else if (input.startsWith(ChangeModeCommand.MODE_KW)) {
            command = new ChangeModeCommand(input);
        } else if (programMode == 1) {
            command = createBookmarkCommand(input);
        } else if (programMode == 2) {
            command = createTimetableCommand(input);
        } else {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }

        return command;
    }

    private static Command createBookmarkCommand(String input) throws DukeException {
        if (input.compareToIgnoreCase(ListCommand.LIST_KW) == 0) {
            return new ListCommand();
        } else if (input.startsWith(DeleteBookmarkCommand.DEL_KW)) {
            return new DeleteBookmarkCommand(input);
        } else if (input.startsWith(AddBookmarkCommand.ADD_KW)) {
            return new AddBookmarkCommand(input);
        } else if (input.startsWith(LaunchBookmarkCommand.LAUNCH_KW)) {
            return new LaunchBookmarkCommand(input);
        } else if (input.startsWith(FindBookmarkCommand.FIND_KW)) {
            command = new FindBookmarkCommand(input);
        } else {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }
    }

    private static Command createTimetableCommand(String input) throws DukeException {
        // Add Timetable Commands on merge
        if (input.startsWith(AddSlotCommand.ADD_KW)) {
            command = new AddSlotCommand(input);
        } else if (input.startsWith(DeleteSlotCommand.DEL_KW)) {
            command = new DeleteSlotCommand(input);
        } else if (input.startsWith(ShowTimetableCommand.SHOW_KW)) {
            return new ShowTimetableCommand(input);
        } else {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }
    }
}
