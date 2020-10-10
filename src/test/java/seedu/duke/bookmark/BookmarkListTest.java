package seedu.duke.bookmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BookmarkListTest {

    private BookmarkList bookmarks;
    private final Bookmark bookmark = new Bookmark("CS2113T", "tutorial", "www.google.com");

    @BeforeEach
    void init() {
        bookmarks = new BookmarkList();
        bookmarks.addBookmark(bookmark);
    }


    @Test
    void addBookmark() {
        // test
        assertTrue(true);
    }

    @Test
    void getData() {
    }

    @Test
    void getSize() {
    }

    @Test
    void getBookmark() {

    }

    @Test
    void getBookmarkList() {
    }

    @Test
    void deleteBookmark() {

    }
}