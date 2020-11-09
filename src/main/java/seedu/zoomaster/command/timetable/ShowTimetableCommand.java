package seedu.zoomaster.command.timetable;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.command.Command;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;
import seedu.zoomaster.slot.Day;
import seedu.zoomaster.slot.Module;
import seedu.zoomaster.slot.Slot;
import seedu.zoomaster.slot.SlotContainer;
import seedu.zoomaster.slot.Timetable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//@@author xingrong123
public class ShowTimetableCommand extends Command {
    public static final String SHOW_KW = "show";
    private String day;
    private boolean showBookmarks = false;
    private String module = null;

    /**
     * Constructs a new ShowTimetableCommand instance.
     * Decodes if the user is requesting to print out the timetable lesson or
     * the details of a module or slot that has been added.
     * Also decodes which mode the timetable is to be printed out in: entire timetable, timetable of a day
     * or timetable today.
     *
     * @param command The command sent by the user.
     * @throws ZoomasterException if the input is invalid
     */
    public ShowTimetableCommand(String command) throws ZoomasterException {
        assert command.startsWith(SHOW_KW) : "command should start with show keyword";
        if (command.compareTo(SHOW_KW) == 0) { // show timetable for all days
            day = "ALL";
        } else if (command.charAt(SHOW_KW.length()) != ' ') {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        } else {
            String details = command.substring(SHOW_KW.length() + 1).trim();
            if (details.toLowerCase().compareToIgnoreCase("today") == 0) {
                day = Day.getDayToday();
            } else if (Day.isDay(details)) {
                day = Day.getDayFromCommand(details);
            } else {
                getModuleInfo(details);
            }
        }
    }

    private void getModuleInfo(String details) throws ZoomasterException {
        String[] input = details.split("\\s+", 2);
        if (input.length == 1) {
            module = input[0];
        } else if (input[1].compareTo("bookmarks") == 0) {
            module = input[0];
            showBookmarks = true;
        } else {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        }
    }

    /**
     * Executes ShowTimetableCommand.
     *
     * @param bookmarks BookmarkList of the program.
     * @param timetable Timetable containing the lesson slots of the program.
     * @param ui The User Interface used to send messages to the user.
     * @throws ZoomasterException if the module is invalid, the timetable is empty or the day input is invalid
     */
    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws ZoomasterException {
        String message = "";
        List<Module> modules = timetable.getFullModuleList();
        if (day != null) { // "show" and "show day" and "show today"
            List<Slot> list = new ArrayList<>(timetable.getFullSlotList());
            message += getMessageLessonAtTime(modules, list, day);
        } else if (isShowModule()) {
            if (!timetable.moduleExists(module)) {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_MODULE);
            }
            Module matchedModule = timetable.getModule(module);
            message += getMessageForModule(matchedModule);
        } else if (isShowModuleBookmarks()) {
            if (!timetable.moduleExists(module)) {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_MODULE);
            }
            Module matchedModule = timetable.getModule(module);
            message += matchedModule.getBookmarksToString();
        }
        ui.print(message);
    }

    //@@author TYS0n1
    /**
     * Builds a message string containing information of lesson slot(s) in the input day.
     * Lesson slot(s) are sorted by timing from 00:00 to 23:59
     *
     * @param modules List of modules codes stored in the program.
     * @param slots List of lesson slots stored in the program.
     * @param day The input day.
     * @return message.toString()
     */
    private String getMessageSlotsInADay(List<Module> modules, List<Slot> slots, String day) {
        StringBuilder message = new StringBuilder();
        boolean hasSlotOnDay = false;
        boolean hasIndicatorOnDay = false;
        if (day.equals(Day.getDayToday())) {
            hasIndicatorOnDay = true;
        }

        ArrayList<Slot> thisDaySlots = new ArrayList<>();
        ArrayList<String> thisDayModuleCodesList = new ArrayList<>();
        for (Slot s: slots) {
            for (Module module : modules) {
                if (module.slotExists(s) && s.getDay().equals(day)) {
                    thisDaySlots.add(s);
                    thisDayModuleCodesList.add(module.getModuleCode());
                }
            }
        }
        SlotContainer slotContainer = new SlotContainer(thisDaySlots, thisDayModuleCodesList);
        SlotContainer sortedSlotContainer = SlotContainer.sortSlotsByTime(slotContainer);
        ArrayList<Slot> sortedSlots = sortedSlotContainer.getSlotList();
        ArrayList<String> sortedModuleCodes = sortedSlotContainer.getModuleCodesList();

        for (int i = 0; i < sortedSlots.size(); i++) {
            if (hasLessonNow(sortedSlots.get(i)) && !sortedSlots.get(i).getTitle().equals("<empty slot>")) {
                message.append(getHighlighBoxUpperMessage());
                message.append(i + 1).append(". ")
                        .append(sortedSlots.get(i).toString()).append(" ")
                        .append(sortedModuleCodes.get(i)).append("\n");
                message.append(getHighlighBoxLowerMessage());
                hasIndicatorOnDay = false;
            } else {
                if (sortedSlots.get(i).getStartTime().isAfter(LocalTime.now())
                        && hasIndicatorOnDay) {
                    message.append(getIndicatorMessage());
                    hasIndicatorOnDay = false;
                }
                message.append(i + 1).append(". ")
                        .append(sortedSlots.get(i).toString()).append(" ")
                        .append(sortedModuleCodes.get(i)).append("\n");
            }
            hasSlotOnDay = true;
        }

        if (!hasSlotOnDay) {
            message.append("No lessons" + "\n");
        }

        if (hasIndicatorOnDay) {
            message.append(getIndicatorMessage());
        }

        message.append("\n");
        return message.toString();
    }

    //@@author TYS0n1
    /**
     * Builds a message string containing information of lesson slot(s) in the entire timetable.
     * Lesson slot(s) are sorted by timing from 00:00 to 23:59
     *
     * @param modules List of modules codes stored in the program.
     * @param slots List of lesson slots stored in the program.
     * @return message.toString()
     */
    private String getMessageTimetable(List<Module> modules, List<Slot> slots) {
        StringBuilder message = new StringBuilder();
        for (Day day: Day.values()) {
            message.append(day.toString()).append("\n");
            message.append(getMessageSlotsInADay(modules, slots, day.toString()));
        }
        return message.toString();
    }

    //@@author
    /**
     * Builds a message string of the timetable to be printed.
     * Lesson slot(s) are sorted by timing from 00:00 to 23:59
     *
     * @param modules List of modules codes stored in the program.
     * @param slots List of lesson slots stored in the program.
     * @param dayInput The input day.
     * @return message
     * @throws ZoomasterException if an timetable is empty or an invalid day is submitted.
     */
    private String getMessageLessonAtTime(List<Module> modules, List<Slot> slots,
                                          String dayInput) throws ZoomasterException {
        String message = "";
        if (slots.isEmpty()) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_TIMETABLE);
        } else if (dayInput == null) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIMETABLE_DAY);
        } else if (dayInput.compareTo("ALL") == 0) {
            return getMessageTimetable(modules,slots);
        }
        message += "Lessons for " + dayInput + "\n";
        message += getMessageSlotsInADay(modules, slots, dayInput);
        return message;
    }

    private String getMessageForModule(Module module) {
        String message = "";
        List<Slot> slots = module.getSlotList();
        if (!slots.isEmpty()) {
            message += module.getModuleCode() + "\n";
            for (int i = 0; i < slots.size(); i++) {
                Slot slot = slots.get(i);
                String dayString = (isShowModule() ? slot.getDay() + " " : "");
                message += "  " + (i + 1) + ". " + dayString + slot.toString() + "\n";
            }
        } else {
            message += "no slots for " + module.getModuleCode() + "\n";
        }
        return message;
    }

    //@@author TYS0n1
    /**
     * Checks if there is an overlap with the input slot timing and the current system time.
     *
     * @param slot The selected slots to be tested.
     * @return isOverlap
     */
    public static boolean hasLessonNow(Slot slot) {
        boolean isOverlap = false;
        LocalTime timeNow = LocalTime.now();
        if (slot.getStartTime().isBefore(timeNow) && slot.getEndTime().isAfter(timeNow)
                && Day.getDayToday().equals(slot.getDay())) {
            isOverlap = true;
        }
        return isOverlap;
    }

    //@@author TYS0n1
    /**
     * Returns an indicator with the current time.
     *
     * @return "\u001b[33m" + currentTimeMessage + "\u001b[0m"
     */
    public static String getIndicatorMessage() {
        DateTimeFormatter hoursAndMinutes = DateTimeFormatter.ofPattern("HH:mm");
        String currentTimeMessage = "<----" + "Current Time: " + LocalTime.now().format(hoursAndMinutes)
                + "---->" + "\n";

        return "\u001b[33m" + currentTimeMessage + "\u001b[0m";
    }

    //@@author TYS0n1
    /**
     * Returns the upper highlighted box for lesson now indicator.
     *
     * @return "\u001b[32m" + message + "\u001b[0m"
     */
    public static String getHighlighBoxUpperMessage() {
        String message = "[====" + "Lesson now" + "====]" + "\n";

        return "\u001b[32m" + message + "\u001b[0m";
    }

    //@@author TYS0n1
    /**
     * Returns the lower highlighted box for lesson now indicator.
     *
     * @return "\u001b[32m" + message + "\u001b[0m"
     */
    public static String getHighlighBoxLowerMessage() {
        String message = "[==================]" + "\n";

        return "\u001b[32m" + message + "\u001b[0m";
    }


    //@@author
    private boolean isShowModuleBookmarks() {
        return module != null && showBookmarks;
    }

    private boolean isShowModule() {
        return module != null && !showBookmarks;
    }
}
