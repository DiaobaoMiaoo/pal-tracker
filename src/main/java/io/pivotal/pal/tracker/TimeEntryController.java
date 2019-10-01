package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

//    @Autowired
    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping(path = "/time-entries", consumes = "application/json")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
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
            ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
            return responseEntity;
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedtTimeEntry = timeEntryRepository.update(timeEntryId, timeEntry);
        if (updatedtTimeEntry == null) {
            ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
            return responseEntity;
        } else {
            ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<TimeEntry>(updatedtTimeEntry, HttpStatus.OK);
            return responseEntity;
        }
    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
