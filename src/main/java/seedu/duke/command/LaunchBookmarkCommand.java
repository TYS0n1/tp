package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;
import seedu.duke.slot.SlotList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaunchBookmarkCommand extends Command {
    public static final String LAUNCH_KW = "launch";
    private int index;
    private List<String> moduleAndDescription;
    private int launchTypeFlag;


    /**
     * Constructs a new LaunchBookmarkCommand instance and and gets the index of the bookmark to launch.
     * Determines whether the launch command was input with an integer or string. and sets launchTypeFlag
     * accordingly
     * Integer (launchTypeFlag = 1)
     * Strings (launchTypeFlag = 2)
     * @param command The command input by the user.
     */
    public LaunchBookmarkCommand(String command) throws DukeException {
        String details = command.substring(LAUNCH_KW.length());
        if (!details.startsWith(" ")) {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }
        try {
            index = Integer.parseInt(details.trim()) - 1;
            launchTypeFlag = 1; // (flag to launch bookmark at specified index)
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new DukeException(DukeExceptionType.INVALID_BOOKMARK_NUMBER);
        } catch (NumberFormatException e) {
            moduleAndDescription = new ArrayList<>(Arrays.asList(details.trim().split(" ", 2)));
            if (moduleAndDescription.size() == 1) {
                moduleAndDescription.add("");  // Blank entry for description
            }
            launchTypeFlag = 2; // (flag to launch bookmarks with matching module and description)
        }
    }

    /**
     * Launches the bookmark based on the launchTypeFlag previously determined
     * in LaunchBookmarkCommand initialization.
     *
     * @param items The list of bookmarks.
     * @param ui The user interface.
     * @param storage The storage for saving and loading.
     */
    @Override
    public void execute(ItemList items, SlotList slotList, Ui ui, Storage bookmarkStorage, Storage slotStorage) throws DukeException {
        BookmarkList bookmarks = (BookmarkList) items;
        if (launchTypeFlag == 1) { // Launch based on index
            try {
                Bookmark bookmark = bookmarks.getBookmark(index);
                bookmark.launch();
                ui.print(getMessage(bookmark));
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException(DukeExceptionType.INVALID_BOOKMARK_NUMBER, ""
                        + bookmarks.getBookmarkList().size());
            }
            return;
        }

        if (launchTypeFlag == 2) { // Launch based on matching module and description
            ui.print(bookmarks.launchBookmarks(moduleAndDescription));
        }
    }

    private String getMessage(Bookmark bookmark) {
        String message = "\tNice! I've launched this bookmark!:\n"
                + "\t  [" + bookmark.getModule() + "] " + bookmark.getDescription() + " "
                + bookmark.getUrl() + "\n";
        return message;
    }
}
