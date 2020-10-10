package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindBookmarkCommand extends Command {
    public static final String FIND_KW = "find";
    private final List<String> moduleAndDescription;


    /**
     * Constructs a new FindBookmarkCommand instance and and gets the module and description
     * to be searched.
     * If only one term is entered (term meaning string without a space), method asserts
     * this to be the module and inserts an empty description
     * @param command The command input by the user.
     */

    public FindBookmarkCommand(String command) throws DukeException {
        String details = command.substring(FIND_KW.length());
        if (!details.startsWith(" ")) {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }
        moduleAndDescription = new ArrayList<>(Arrays.asList(details.trim().split(" ", 2)));
        if (moduleAndDescription.size() == 1) {
            moduleAndDescription.add("");  // Empty entry for description
        }
    }

    /**
     * prints the matching bookmark.
     *
     * @param items The list of bookmarks.
     * @param ui The user interface.
     * @param storage The storage for saving and loading.
     */
    @Override
    public void execute(ItemList items, Ui ui, Storage storage) {
        BookmarkList bookmarks = (BookmarkList) items;
        ui.print(bookmarks.findBookmarks(moduleAndDescription));
    }

    private String getMessage(Bookmark bookmark) {
        String message = "\tNice! I've launched this bookmark!:\n"
                + "\t  [" + bookmark.getModule() + "] " + bookmark.getDescription() + " " + bookmark.getUrl() + "\n";
        return message;
    }
}
