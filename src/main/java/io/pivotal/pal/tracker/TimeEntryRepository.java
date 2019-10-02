package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry timeEntry);

    public TimeEntry find(Long id);
    public List<TimeEntry> list();

    public TimeEntry update(Long id, TimeEntry timeEntry);

    public void delete(Long id);

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
