package seedu.zoomaster.slot;

import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public boolean isTimeAGreaterEqualsTimeB(LocalTime timeA, LocalTime timeB) {
        boolean isGreaterEquals = false;
        if (timeA.isAfter(timeB) || timeA.equals(timeB)) {
            isGreaterEquals = true;
        }
        return isGreaterEquals;
    }

    public Slot getSlotByIndexInDay(String day, int index) throws ZoomasterException {
        // TODO: Implement this method. This is a dummy implementation.
        for (Module module: modules) {
            if (!module.getSlotList().isEmpty()) {
                return module.getSlotList().get(0);
            }
        }
        return null;
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
            newModule = addModule(newModuleCode);
        }

        newModule.addSlot(slot);
    }

}
