package seedu.zoomaster;

import org.junit.jupiter.api.Test;
import seedu.zoomaster.bookmark.Bookmark;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;
import seedu.zoomaster.settings.UserSettings;
import seedu.zoomaster.slot.Timetable;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author Speedweener
class StorageTest {

    @Test
    void loadTimetableWithInvalidFilePath() throws ZoomasterException {
        Storage<Timetable> test = new Storage<>("iNvAlIdPaThNaMe/data/timetable/", Timetable.class);
        assertTrue(test.load() instanceof Timetable);
    }

    @Test
    void loadBookmarkListWithInvalidFilePath() throws ZoomasterException {
        Storage<BookmarkList> test = new Storage<>("iNvAlIdPaThNaMe/data/bookmark/", BookmarkList.class);
        assertTrue(test.load() instanceof BookmarkList);
    }

    @Test
    void loadUserSettingsWithInvalidFilePath() throws ZoomasterException {
        Storage<UserSettings> test = new Storage<>("iNvAlIdPaThNaMe/data/settings/", UserSettings.class);
        assertTrue(test.load() instanceof UserSettings);
    }

    @Test
    void loadWithBaseClassThrowsErrorLoadingFileException() {
        Storage<Bookmark> test = new Storage<>("iNvAlIdPaThNaMe/data/timetable/", Bookmark.class);
        ZoomasterException e = assertThrows(ZoomasterException.class, () -> test.load());
        assertEquals(ZoomasterExceptionType.ERROR_LOADING_FILE, e.getError());
    }

    @Test
    void getFiles() {
    }

    @Test
    void loadPlanner() {
    }

    @Test
    void saveNullObjectThrowsWriteFileErrorException() {
        Storage<Timetable> test = new Storage<>("/data/timetable/", Timetable.class);
        ZoomasterException e = assertThrows(ZoomasterException.class, () -> test.save(null));
        assertEquals(ZoomasterExceptionType.WRITE_FILE_ERROR, e.getError());
    }

    @Test
    void loadModuleListWithInvalidPathThrowsFileNotFoundException() {
        Storage<Timetable> test = new Storage<>("iNvAlIdPaThNaMe/data/timetable/", Timetable.class);
        assertThrows(FileNotFoundException.class, () -> test.loadModuleList());
    }
}