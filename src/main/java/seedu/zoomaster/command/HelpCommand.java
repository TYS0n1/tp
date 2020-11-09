package seedu.zoomaster.command;

import seedu.zoomaster.Parser;
import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.BookmarkList;

import seedu.zoomaster.command.bookmark.AddBookmarkCommand;
import seedu.zoomaster.command.bookmark.DeleteBookmarkCommand;
import seedu.zoomaster.command.bookmark.FindBookmarkCommand;
import seedu.zoomaster.command.bookmark.LaunchBookmarkCommand;
import seedu.zoomaster.command.bookmark.ShowBookmarkCommand;
import seedu.zoomaster.command.bookmark.EditBookmarkCommand;

import seedu.zoomaster.command.planner.LoadPlannerCommand;
import seedu.zoomaster.command.planner.SavePlannerCommand;

import seedu.zoomaster.command.settings.SetSettingsCommand;
import seedu.zoomaster.command.settings.ShowSettingsCommand;

import seedu.zoomaster.command.timetable.AddSlotCommand;
import seedu.zoomaster.command.timetable.DeleteSlotCommand;
import seedu.zoomaster.command.timetable.ShowTimetableCommand;
import seedu.zoomaster.command.timetable.EditSlotCommand;
import seedu.zoomaster.command.timetable.LaunchModuleAndSlotBookmark;

import seedu.zoomaster.command.planner.AddMeetingCommand;

import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import seedu.zoomaster.slot.Timetable;

/**
 * Executes the help functionality of the program.
 */
//@@author Speedweener
public class HelpCommand extends Command {
    public static final String HELP_KW = "help";
    private String details;

    /**
     * Constructs a new HelpCommand instance.
     * Assert line catches when a non HelpCommand compatible command has triggered this constructor.
     * It extracts a String, details and check if it is the name of a valid command in the current
     * program mode.
     *
     * @param command The command sent by the user.
     * @throws ZoomasterException If details is not a valid command in the current program mode.
     */
    public HelpCommand(String command) throws ZoomasterException {
        assert command.startsWith(HELP_KW) : "input should always start with \"help\"";
        details = command.substring(HELP_KW.length()).trim();
        if (!details.isEmpty()) {
            if (!isValidCommand(details)) {
                throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, details);
            }
        }
    }

    /**
     * Executes HelpCommand.
     * If no command name is requested for help, it will print out all the command names available
     * in the current program mode.
     * If a valid command name is requested for help, it will print out help information for the
     * command specified.
     *
     * @param bookmarks BookmarkList of the program
     * @param timetable Timetable containing the lesson slots of the program
     * @param ui The User Interface used to send messages to the user.
     */
    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) {
        if (details.isEmpty()) {
            ui.printHelpMessage();
        } else {
            ui.printHelpMessage(details);
        }
    }

    /**
     * Checks if the command requested for help is a valid command in the current program mode.
     *
     * @param details The name of the command requested for help by the user.
     * @return isValid.
     */
    private boolean isValidCommand(String details) {
        boolean isValid = false;

        if (details.compareToIgnoreCase(ClearCommand.CLEAR_KW) == 0
            || details.compareToIgnoreCase(ChangeModeCommand.MODE_KW) == 0
            || details.compareToIgnoreCase(ExitCommand.EXIT_KW) == 0
            || details.compareToIgnoreCase(LaunchNowCommand.LAUNCH_NOW_KW) == 0
            || details.compareToIgnoreCase(ShowSettingsCommand.SHOW_KW) == 0
            || details.compareToIgnoreCase(SetSettingsCommand.SET_KW) == 0) {
            isValid = true;
        } else if (Parser.getProgramMode() == 1) {
            if (details.compareToIgnoreCase(AddBookmarkCommand.ADD_KW) == 0
                    || details.compareToIgnoreCase(DeleteBookmarkCommand.DEL_KW) == 0
                    || details.compareToIgnoreCase(ShowBookmarkCommand.SHOW_KW) == 0
                    || details.compareToIgnoreCase(FindBookmarkCommand.FIND_KW) == 0
                    || details.compareToIgnoreCase(LaunchBookmarkCommand.LAUNCH_KW) == 0
                    || details.compareToIgnoreCase(EditBookmarkCommand.EDIT_KW) == 0) {
                isValid =  true;
            }
        } else if (Parser.getProgramMode() == 2) {
            if (details.compareToIgnoreCase(AddSlotCommand.ADD_KW) == 0
                    || details.compareToIgnoreCase(DeleteSlotCommand.DEL_KW) == 0
                    || details.compareToIgnoreCase(ShowTimetableCommand.SHOW_KW) == 0
                    || details.compareToIgnoreCase(EditSlotCommand.EDIT_KW) == 0
                    || details.compareToIgnoreCase(LaunchModuleAndSlotBookmark.LAUNCH_KW) == 0) {
                isValid =  true;
            }
        } else if (Parser.getProgramMode() == 3) {
            if (details.compareToIgnoreCase(AddMeetingCommand.ADD_KW) == 0
                    || details.compareToIgnoreCase(LoadPlannerCommand.LOAD_KW) == 0
                    || details.compareToIgnoreCase(ShowTimetableCommand.SHOW_KW) == 0
                    || details.compareToIgnoreCase(SavePlannerCommand.SAVE_KW) == 0) {
                isValid =  true;
            }
        }

        return isValid;
    }
}
