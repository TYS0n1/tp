package seedu.zoomaster.command;

import org.junit.jupiter.api.Test;
import seedu.zoomaster.Parser;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Speedweener
class HelpCommandTest {
    List<String> modeZeroCommands = new ArrayList<>(Arrays.asList("mode","clear","launch now", "showsettings",
            "set", "exit"));
    List<String> modeOneCommands =  new ArrayList<>(Arrays.asList("add", "delete", "show","find","launch", "edit"));
    List<String> modeTwoCommands =  new ArrayList<>(Arrays.asList("add", "delete", "show","edit","launch"));
    List<String> modeThreeCommands =  new ArrayList<>(Arrays.asList("load", "add", "show","save"));


    @Test
    void validCommandsForEachMode() throws ZoomasterException {
        List<String> totalModeOneCommands = modeOneCommands;
        totalModeOneCommands.addAll(modeZeroCommands);

        List<String> totalModeTwoCommands = modeTwoCommands;
        totalModeTwoCommands.addAll(modeZeroCommands);

        List<String> totalModeThreeCommands = modeThreeCommands;
        totalModeThreeCommands.addAll(modeZeroCommands);

        Parser.programMode = 0;
        for (String command : modeZeroCommands) {
            Command test = new HelpCommand("help " + command);
        }

        Parser.programMode = 1;
        for (String command : totalModeOneCommands) {
            Command test = new HelpCommand("help " + command);
        }

        Parser.programMode = 2;
        for (String command : totalModeTwoCommands) {
            Command test = new HelpCommand("help " + command);
        }

        Parser.programMode = 3;
        for (String command : totalModeThreeCommands) {
            Command test = new HelpCommand("help " + command);
        }
    }

    @Test
    void invalidDetailsModeZeroThrowsUnknownHelpCommandException() {
        Parser.programMode = 0;
        ZoomasterException e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help iNvalidCommand"));
        assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());

        for (String command : modeOneCommands) {
            e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help" + command));
            assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());
        }

        for (String command : modeTwoCommands) {
            e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help" + command));
            assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());
        }

        for (String command : modeThreeCommands) {
            e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help" + command));
            assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());
        }

    }

    @Test
    void invalidDetailsModeOneThrowsUnknownHelpCommandException() {
        Parser.programMode = 1;
        List<String> nonModeOneCommands = modeTwoCommands;
        nonModeOneCommands.addAll(modeThreeCommands);
        nonModeOneCommands.removeAll(modeOneCommands);

        ZoomasterException e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help iNvalidCommand"));
        assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());

        for (String command : nonModeOneCommands) {
            e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help" + command));
            assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());
        }
    }

    @Test
    void invalidDetailsModeTwoThrowsUnknownHelpCommandException() {
        Parser.programMode = 2;
        List<String> nonModeTwoCommands = modeOneCommands;
        nonModeTwoCommands.addAll(modeThreeCommands);
        nonModeTwoCommands.removeAll(modeTwoCommands);

        ZoomasterException e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help iNvalidCommand"));
        assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());

        for (String command : nonModeTwoCommands) {
            e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help" + command));
            assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());
        }
    }

    @Test
    void invalidDetailsModeThreeThrowsUnknownHelpCommandException() {
        Parser.programMode = 3;
        List<String> nonModeThreeCommands = modeOneCommands;
        nonModeThreeCommands.addAll(modeTwoCommands);
        nonModeThreeCommands.removeAll(modeThreeCommands);

        ZoomasterException e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help iNvalidCommand"));
        assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());

        for (String command : nonModeThreeCommands) {
            e = assertThrows(ZoomasterException.class, () -> new HelpCommand("help" + command));
            assertEquals(ZoomasterExceptionType.UNKNOWN_HELP_COMMAND, e.getError());
        }
    }



}