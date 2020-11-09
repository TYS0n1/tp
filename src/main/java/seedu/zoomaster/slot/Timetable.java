package seedu.zoomaster.slot;

import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//@@author xingrong123
public class Timetable {
    private List<Module> modules;

    public Timetable() {
        modules = new ArrayList<>();
    }

    public boolean moduleExists(String moduleCode) {
        boolean moduleExists = false;
        for (Module module : modules) {
            if (module.isModule(moduleCode)) {
                moduleExists = true;
            }
        }
        return moduleExists;
    }

    public Module addModule(String moduleCode) {
        Module module = new Module(moduleCode);
        modules.add(module);
        return module;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public List<Slot> getFullSlotList() {
        List<Slot> slotList = new ArrayList<>();
        for (Module module : modules) {
            slotList.addAll(module.getSlotList());
        }
        return slotList;
    }

    //@@author jusufnathanael
    public void clearAllModule() {
        modules.removeAll(modules);
    }

    public List<Module> getFullModuleList() {
        return modules;
    }

    public Module getModule(String moduleCode) {
        Module module = null;
        for (Module mod : modules) {
            if (mod.isModule(moduleCode)) {
                module = mod;
            }
        }
        assert module != null : "module should not be null";
        return module;
    }

    //@@author fchensan
    public Module getModuleContainingSlot(Slot slot) {
        for (Module module: modules) {
            if (module.getSlotList().contains(slot)) {
                return module;
            }
        }
        return null;
    }

    public void deleteModule(Module module) {
        modules.remove(module);
    }

    //@@author TYS0n1
    public boolean isOverlapTimeSlot(String day, LocalTime startTime, LocalTime endTime) {
        boolean isOverlap = false;
        List<Slot> slotList = getFullSlotList();
        for (Slot slot : slotList) {
            if (slot.getDay().equals(day)) {
                if ((isTimeAGreaterEqualsTimeB(startTime, slot.getEndTime())
                        && isTimeAGreaterEqualsTimeB(endTime, slot.getEndTime()))
                        || (isTimeAGreaterEqualsTimeB(slot.getStartTime(), startTime)
                        && isTimeAGreaterEqualsTimeB(slot.getStartTime(), endTime))) {
                    continue;
                }
                isOverlap = true;
                break;
            }
        }
        return isOverlap;
    }


    //@@author TYS0n1
    public static boolean isTimeAGreaterEqualsTimeB(LocalTime timeA, LocalTime timeB) {
        boolean isGreaterEquals = false;
        if (timeA.isAfter(timeB) || timeA.equals(timeB)) {
            isGreaterEquals = true;
        }
        return isGreaterEquals;
    }

    //@@author fchensan
    public Slot getSlotByIndexInDay(String day, int index) throws ZoomasterException {
        ArrayList<Slot> slots = new ArrayList<>();
        ArrayList<String> moduleCodeList = new ArrayList<>();

        for (Module module: modules) {
            for (Slot s: module.getSlotList()) {
                if (s.getDay().equals(day)) {
                    slots.add(s);
                    moduleCodeList.add(module.getModuleCode());
                }
            }
        }

        SlotContainer slotContainer = new SlotContainer(slots, moduleCodeList);
        slotContainer = SlotContainer.sortSlotsByTime(slotContainer);

        ArrayList<Slot> slotList = slotContainer.getSlotList();

        if (slotList.size() == 0) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_DAY);
        }

        try {
            return slotList.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_INDEX_IN_DAY,
                Integer.toString(slotList.size()));
        }
    }

    /**
     * Move a slot to another module given a user input module code.
     *
     * @param dayOfSlot The day of slot to be moved.
     * @param indexInDay The index of the slot within its day.
     * @param newModuleCode The module code where the slot will be moved to.
     */
    public void moveSlotToAnotherModule(String dayOfSlot, int indexInDay, String newModuleCode)
            throws ZoomasterException {
        Slot slot = getSlotByIndexInDay(dayOfSlot, indexInDay);
        getModuleContainingSlot(slot).removeSlot(slot);

        Module newModule;
        if (moduleExists(newModuleCode)) {
            newModule = getModule(newModuleCode);
        } else {
            newModule = new Module(newModuleCode);
            addModule(newModule);
        }

        newModule.addSlot(slot);
    }

    //@@author
    public boolean isEmpty() {
        return modules.isEmpty();
    }

}
