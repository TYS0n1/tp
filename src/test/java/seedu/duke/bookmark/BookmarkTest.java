package seedu.duke.bookmark;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookmarkTest {

    @Test
    void extractModuleDescriptionAndUrl_missingModule_returnsDescAndUrl() throws DukeException {
        List<String> expectedUrlAndDescription = new ArrayList<>();
        expectedUrlAndDescription.add("");
        expectedUrlAndDescription.add("tutorial");
        expectedUrlAndDescription.add("www.google.com");
        String input = "add tutorial www.google.com";
        assertEquals(expectedUrlAndDescription, Bookmark.extractModuleDescriptionAndUrl(input));
    }

    @Test
    void extractModuleDescriptionAndUrl_insertModuleDescAndUrl_returnsModuleDescAndUrl() throws DukeException {
        List<String> expectedUrlAndDescription = new ArrayList<>();
        expectedUrlAndDescription.add("");
        expectedUrlAndDescription.add("tutorial");
        expectedUrlAndDescription.add("www.google.com");
        String input = "add tutorial www.google.com";
        assertEquals(expectedUrlAndDescription, Bookmark.extractModuleDescriptionAndUrl(input));
    }

    @Test
    void extractModuleDescriptionAndUrl_missingDescription_throwsDukeException() {
        String input = "add CS2113T  www.google.com";
        DukeException e = assertThrows(DukeException.class, () -> Bookmark.extractModuleDescriptionAndUrl(input));
        assertEquals(DukeExceptionType.EMPTY_DESCRIPTION, e.getError());
    }

    @Test
    void extractModuleDescriptionAndUrl_invalidUrl_throwsDukeException() {
        String input = "add CS2113T google.com";
        DukeException e = assertThrows(DukeException.class, () -> Bookmark.extractModuleDescriptionAndUrl(input));
        assertEquals(DukeExceptionType.INVALID_URL, e.getError());
    }

    @Test
    void getUrl_validModuleDescriptionAndUrl_returnsUrl() {
        String module = "CS2113T";
        String description = "something";
        String url = "www.google.com";
        Bookmark bookmark = new Bookmark(module, description, url);
        assertEquals(url, bookmark.getUrl());
    }

    @Test
    void getDescription_validModuleDescriptionAndUrl_returnsDescription() {
        String module = "CS2113T";
        String description = "something";
        String url = "www.google.com";
        Bookmark bookmark = new Bookmark(module, description, url);
        assertEquals(description, bookmark.getDescription());
    }

    @Test
    void initBookmark_validDataString_bookmark() {
        String data = "CS2113T | tutorial | www.google.com";
        String module = "CS2113T";
        String description = "tutorial";
        String url = "www.google.com";
        Bookmark expectedBookmark = new Bookmark(module, description, url);
        assertEquals(expectedBookmark.getBookmarkAsString(), Bookmark.initBookmark(data).getBookmarkAsString());
    }
}