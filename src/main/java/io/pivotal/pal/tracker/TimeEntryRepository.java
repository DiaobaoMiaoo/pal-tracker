package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeEntryRepository {
    private Map<Long, TimeEntry> timeEntryMap = new HashMap<>();

    private Long idCounter = 1L;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntryMap.put(idCounter,timeEntry);
        timeEntry.setId(idCounter);
        idCounter++;
        return timeEntry;
    }

    public TimeEntry find(long id) {
        return timeEntryMap.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntryMap.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(timeEntryMap.get(id) == null) return null;
        timeEntryMap.put(id, timeEntry);
        timeEntry.setId(id);
        return timeEntry;
    }

    public void delete(long id) {
        timeEntryMap.remove(id);
    }

//    public TimeEntry create(TimeEntry any);
//
//    public TimeEntry find(long timeEntryId);
//
//    public List<TimeEntry> list();
//
//    public TimeEntry update(long eq, TimeEntry any);
//
//    public void delete(long timeEntryId);
}
