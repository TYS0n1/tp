package seedu.zoomaster.command;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.slot.Day;
import seedu.zoomaster.slot.Slot;
import seedu.zoomaster.slot.Timetable;

import java.time.LocalTime;
import java.util.List;

//@@author xingrong123
/**
 * Represents the command to launch the bookmarks of the current ongoing class slot in the timetable.
 */
public class LaunchNowCommand extends Command {
    public static final String LAUNCH_NOW_KW = "launch now";

    public LaunchNowCommand() {

    }

    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws ZoomasterException {
        LocalTime currentTime = LocalTime.now().plusMinutes(5);
        String message = "";

        List<Slot> slots = timetable.getFullSlotList();
        for (Slot slot : slots) {
            if (slot.getStartTime().isBefore(currentTime) && slot.getEndTime().isAfter(currentTime)
                    && slot.getDay().compareToIgnoreCase(Day.getDayToday()) == 0) {
                message = slot.getBookmarkList().launchAllBookmarks();
                break;
            }
        }

        if (message.isBlank()) {
            message = "no lessons now." + Ui.NEW_LINE;
        }
        ui.print(message);
    }
}
