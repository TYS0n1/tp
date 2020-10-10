package seedu.duke.bookmark;

import seedu.duke.exception.DukeException;
import seedu.duke.ItemList;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the bookmark list used to store and organize bookmarks.
 */
public class BookmarkList extends ItemList {
    private ArrayList<Bookmark> bookmarks;

    /**
     * Constructs a BookmarkList object with an empty ArrayList to store Bookmark objects.
     */
    public BookmarkList() {
        this.bookmarks = new ArrayList<>();
    }

    /**
     * Constructs the BookmarkList object containing an ArrayList to store Bookmark objects.
     * This constructor is used when loading bookmarks from a text file.
     *
     * @param bookmarkStrings the list of strings of bookmarks.
     */
    public BookmarkList(ArrayList<String> bookmarkStrings) {
        this.bookmarks = new ArrayList<>();
        loadBookmarkList(bookmarkStrings);
    }

    private void loadBookmarkList(ArrayList<String> bookmarks) {
        for (String line : bookmarks) {
            loadBookmark(line);
        }
    }

    /**
     * This method adds the given bookmark object to the bookmark list.
     *
     * @param bookmark the bookmark to be added to the list.
     */
    public void addBookmark(Bookmark bookmark)  {
        bookmarks.add(bookmark);
    }

    private void loadBookmark(String line) {
        try {
            bookmarks.add(Bookmark.initBookmark(line));
        } catch (IndexOutOfBoundsException e) {
            // Invalid task data, skips to the next entry
        }
    }

    /**
     * Returns the the data of all bookmarks in the list to be saved in the text file.
     *
     * @return the data of all the bookmarks.
     */
    public String getData() {
        StringBuilder data = new StringBuilder();
        for (Bookmark bookmark : bookmarks) {
            data.append(bookmark.getExport()).append(System.lineSeparator());
        }
        return data.toString().trim();
    }

    /**
     * Returns the number of bookmarks in the list.
     *
     * @return the size of the bookmark list.
     */
    public int getSize() {
        return bookmarks.size();
    }

    /**
     * Returns bookmark based on the index.
     *
     * @param index The index of the bookmark in the list.
     * @return The bookmark with the corresponding index in the list.
     */
    public Bookmark getBookmark(int index) {
        return bookmarks.get(index);
    }

    /**
     * Returns the bookmark list.
     *
     * @return The bookmark list.
     */
    public ArrayList<Bookmark> getBookmarkList() {
        return bookmarks;
    }

    /**
     * This method deletes the bookmark from the list.
     *
     * @param bookmark The bookmark to be deleted.
     */
    public void deleteBookmark(Bookmark bookmark) {
        bookmarks.remove(bookmark);
    }


    /**
     * This method searches the bookmarks from the list with matching module
     * and description.
     *
     * @param list The List<String></String> containing the module and description to be searched
     * @return The string message containing the matching bookmarks
     */
    public String findBookmarks(List<String> list) {
        if (bookmarks.size() == 0) {
            return ("Empty List");
        }

        String module = list.get(0).toUpperCase();
        String description = list.get(1).toUpperCase();
        String message = "";

        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).getModule().toUpperCase().contains(module)
                    && bookmarks.get(i).getDescription().toUpperCase().contains(description)) {
                message += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString() + "\n";
            }
        }

        if (message.isEmpty()) {
            return  "No tasks contain the specified keyword!" + "\n";
        }
        return "Here are your matching bookmarks" + "\n" + message;
    }

    /**
     * This method searches the bookmarks from the list with matching module
     * and description and launches them accordingly.
     *
     * @param list The List<String></String> containing the module and description to be searched
     * @return The string message containing the matching bookmarks
     */
    public String launchBookmarks(List<String> list) {
        if (bookmarks.size() == 0) {
            return ("Empty List");
        }

        String module = list.get(0).toUpperCase();
        String description = list.get(1).toUpperCase();
        String message = "";

        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).getModule().toUpperCase().contains(module)
                    && bookmarks.get(i).getDescription().toUpperCase().contains(description)) {
                message += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString() + "\n";
                try {
                    bookmarks.get(i).launch();
                } catch (DukeException e) {
                    e.printStackTrace();
                }
            }
        }

        if (message.isEmpty()) {
            return  "No tasks contain the specified keyword!" + "\n";
        }
        return "Launched these bookmarks:" + "\n" + message;
    }
}
