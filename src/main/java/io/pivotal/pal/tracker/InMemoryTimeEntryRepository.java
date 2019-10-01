package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTimeEntryRepository {

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
}
