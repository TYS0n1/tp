package seedu.zoomaster.command.timetable;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.Bookmark;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.command.Command;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;
import seedu.zoomaster.slot.Day;
import seedu.zoomaster.slot.Module;
import seedu.zoomaster.slot.Slot;
import seedu.zoomaster.slot.Timetable;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the command add a module, slot, module bookmark or slot bookmark to the timetable.
 */
//@@author xingrong123
public class AddSlotCommand extends Command {
    public static final String ADD_KW = "add";

    // ui messages
    private static final String ALREADY_EXISTS_MSG = " already exists";
    private static final String ADDED_MSG = " added";
    private static final String INCORRECT_SLOT_FORMAT_MSG = "incorrect format for slot: ";
    private static final String ADD_MOD_BOOKMARK_MSG = "  bookmark added to ";
    private static final String SLOT_FORMAT_ERROR_MSG = ". (please follow the format: add {MODULE} "
            + "{DESCRIPTION} {DAY} {START_TIME} {END_TIME})";
    private static final String INVALID_DAY_MSG = "  Invalid day input: ";
    private static final String INVALID_TIME_FORMAT_MSG = "  Invalid time format for slot. (";
    private static final String SLOT_ALREADY_EXISTS_MSG = "  Slot already exists.";
    private static final String INVALID_TIME_MSG = "  Invalid time for slot. (";
    private static final String TIME_CLASH_MSG = "  Slot clash. (";
    private static final String CHECK_TIMETABLE_MSG = " Please check timetable.";
    private static final String SLOT_ADDED_MSG = " slot added";
    private static final String INVALID_COMMAND_FORMAT_MSG = "  Invalid command format.";
    private static final String INVALID_SLOT_INDEX_MSG = "  Invalid slot index.";
    private static final String SLOT_INDEX_OUT_OF_RANGE_MSG = "  Slot index out of range.";
    private static final String ADD_SLOT_BOOKMARK_MSG = "    bookmark added to ";
    private static final String INVALID_URL_FORMAT_MSG = "    Invalid url format. Please provide one valid url.";
    private static final String INVALID_URL_FORMAT_FOR_SLOT_MSG = "invalid url format: ";
    private static final String URL_FORMAT_MSG = "URL must start with either 'www.', 'http://' or 'https://'";
    private static final String BOOKMARK_ADDED_TO_MODULE_MSG = "  bookmark added to module";

    private String moduleCode;
    private List<String> commands;

    /**
     * Constructs a new AddSlotCommand instance and stores the information of the slot given by the input.
     *
     * @param command The user input command.
     * @throws ZoomasterException if input command is invalid.
     */
    public AddSlotCommand(String command) throws ZoomasterException {
        assert command.startsWith(ADD_KW);
        String details = command.substring(ADD_KW.length());
        if (details.isBlank()) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_COMMAND, ADD_KW);
        } else if (!details.startsWith(" ")) {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        }
        String[] stringArray = details.trim().split("\\s+", 2);
        moduleCode = stringArray[0].toUpperCase();
        if (stringArray.length > 1) {
            commands = Arrays.asList(stringArray[1].split(","));
        }
    }

    /**
     * Adds modules, slots, module bookmarks and slot bookmarks into the timetable.
     * Supports one-shot-command when adding multiple multiple components to a module by using the separator ',' to
     * separate the addition of each component.
     *
     * @param bookmarks The list of bookmarks
     * @param timetable The timetable
     * @param ui The user interface
     * @throws ZoomasterException if the module code is invalid.
     */
    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws ZoomasterException {
        String message = "";
        Module module;
        if (timetable.moduleExists(moduleCode)) {
            module = timetable.getModule(moduleCode);
            message += moduleCode + ALREADY_EXISTS_MSG + Ui.NEW_LINE;
        } else if (!isValidModule(moduleCode)) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_MODULE);
        } else {
            module = new Module(moduleCode);
            timetable.addModule(module);
            message += moduleCode + ADDED_MSG + Ui.NEW_LINE;
        }

        if (commands != null) {
            for (String command : commands) {
                message += createSlotAndBookmark(module, command.trim(), timetable);
            }
        }
        ui.print(message);
    }

    protected String createSlotAndBookmark(Module module, String command, Timetable timetable) {
        assert module != null : "module should mot be null";
        String message = "";
        try {
            message += create(command, module, timetable);
        } catch (ZoomasterException e) {
            message += e.getInfo() + Ui.NEW_LINE;
        } catch (IndexOutOfBoundsException e) {
            message += INCORRECT_SLOT_FORMAT_MSG + command + Ui.NEW_LINE;
        }
        return message;
    }

    protected String create(String command, Module module, Timetable timetable) throws ZoomasterException {
        String message = "";
        List<String> slotAndBookmark = Arrays.asList(command.trim().split("\\s+"));
        if (isAddModuleBookmark(slotAndBookmark)) {
            message = addBookmarkToModule(module, slotAndBookmark);
        } else if (slotAndBookmark.size() == 2) {
            Slot slot;
            slot = retrieveSlotFromModule(module, slotAndBookmark);
            createBookmark(slotAndBookmark.get(1), slot.getTitle(), slot);
            message = ADD_MOD_BOOKMARK_MSG + moduleCode + " " + slot.getTitle() + Ui.NEW_LINE;
        } else if (slotAndBookmark.size() >= 4) {
            String lesson = slotAndBookmark.get(0);
            String day = slotAndBookmark.get(1);
            LocalTime startTime;
            LocalTime endTime;
            if (!Day.isDay(day)) {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIMETABLE_DAY,
                        INVALID_DAY_MSG + day + SLOT_FORMAT_ERROR_MSG);
            }
            try {
                startTime = LocalTime.parse(slotAndBookmark.get(2));
                endTime = LocalTime.parse(slotAndBookmark.get(3));
            } catch (DateTimeParseException e) {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIME_FORMAT,
                        INVALID_TIME_FORMAT_MSG + slotAndBookmark.get(2)
                        + " " + slotAndBookmark.get(3) + ")");
            }
            Slot newSlot;
            if (module.slotExists(lesson, day, startTime, endTime)) {
                newSlot = module.getSlot(lesson, day, startTime, endTime);
                message += SLOT_ALREADY_EXISTS_MSG + Ui.NEW_LINE;
            } else {
                if (Timetable.isTimeAGreaterEqualsTimeB(startTime, endTime)) {
                    throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIME_FORMAT,
                            INVALID_TIME_MSG + slotAndBookmark.get(2) + " " + slotAndBookmark.get(3) + ")");
                } else if (timetable.isOverlapTimeSlot(day, startTime, endTime)) {
                    throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIME_FORMAT,
                            TIME_CLASH_MSG + slotAndBookmark.get(2) + " " + slotAndBookmark.get(3) + ")"
                            + CHECK_TIMETABLE_MSG);
                } else {
                    newSlot = new Slot(startTime, endTime, day, lesson);
                    module.addSlot(newSlot);
                    message +=  "  " + lesson + SLOT_ADDED_MSG + Ui.NEW_LINE;
                }
            }
            message += checkForAndAddBookmarkToSlot(slotAndBookmark, lesson, newSlot);
        } else {
            message = INVALID_COMMAND_FORMAT_MSG + Ui.NEW_LINE;
        }
        return message;
    }

    private Slot retrieveSlotFromModule(Module module, List<String> slotAndBookmark) throws ZoomasterException {
        Slot slot;
        try {
            int index = Integer.parseInt(slotAndBookmark.get(0)) - 1;
            slot = module.getSlot(index);
        } catch (NumberFormatException e) {
            throw new ZoomasterException(ZoomasterExceptionType.NON_INTEGER_INPUT, INVALID_SLOT_INDEX_MSG);
        } catch (ZoomasterException e) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_SLOT_NUMBER, SLOT_INDEX_OUT_OF_RANGE_MSG);
        }
        return slot;
    }

    protected String checkForAndAddBookmarkToSlot(List<String> slotAndBookmark, String lesson, Slot newSlot)
            throws ZoomasterException {
        String message = "";
        if (slotAndBookmark.size() == 5) {
            createBookmark(slotAndBookmark.get(4), lesson, newSlot);
            message = ADD_SLOT_BOOKMARK_MSG + moduleCode + " " + lesson + Ui.NEW_LINE;
        } else if (slotAndBookmark.size() > 5) {
            message = INVALID_URL_FORMAT_MSG + Ui.NEW_LINE;
        }
        return message;
    }

    protected String addBookmarkToModule(Module module, List<String> slotAndBookmark) {
        String description = slotAndBookmark.get(0);
        String url = slotAndBookmark.get(1);
        Bookmark bookmark = new Bookmark(description, url);
        module.addBookmark(bookmark);
        return BOOKMARK_ADDED_TO_MODULE_MSG + Ui.NEW_LINE;
    }

    protected boolean isAddModuleBookmark(List<String> slotAndBookmark) {
        try {
            int dummy = Integer.parseInt(slotAndBookmark.get(0));
            return false;
        } catch (NumberFormatException e) {
            return Bookmark.isUrlValid(slotAndBookmark.get(1)) && slotAndBookmark.size() == 2;
        }
    }

    private void createBookmark(String url, String lesson, Slot newSlot) throws ZoomasterException {
        if (!Bookmark.isUrlValid(url)) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_URL, INVALID_URL_FORMAT_FOR_SLOT_MSG + url
                    + URL_FORMAT_MSG
                    + Ui.NEW_LINE);
        }
        Bookmark bookmark = new Bookmark(lesson, url);
        newSlot.addBookmark(bookmark);
    }

    /**
     * Validates the module code with the list of modules moduleList.
     *
     * @param module The module code to be added.
     *
     * @return
     * true if module exist in the list or list is null.
     *     false if module does not exists in the list.
     */
    private boolean isValidModule(String module) {
        if (Module.getModuleList() == null) { // If unable to get list of modules, always return true.
            return true;
        } else if (Module.getModuleList().contains(module)) {
            return true;
        }
        return false;
    }

}
