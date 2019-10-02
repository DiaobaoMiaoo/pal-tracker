package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping(path = "/time-entries", consumes = "application/json")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if (timeEntry == null) {
            ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
            return responseEntity;
        } else {
            actionCounter.increment();
            ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
            return responseEntity;
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(
            value = "/time-entries/{timeEntryId}",
            produces = "application/json",
            method = {RequestMethod.PUT})
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedtTimeEntry = timeEntryRepository.update(timeEntryId, timeEntry);
        if (updatedtTimeEntry == null) {
            ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
            return responseEntity;
        } else {
            actionCounter.increment();
            ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(updatedtTimeEntry, HttpStatus.OK);
            return responseEntity;
        }
    }

    @RequestMapping(
            value = "/time-entries/{timeEntryId}",
            produces = "application/json",
            method = {RequestMethod.DELETE})
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
