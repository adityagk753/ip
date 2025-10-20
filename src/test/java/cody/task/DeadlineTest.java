package cody.task;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import cody.exception.CodyException;

public class DeadlineTest {
    @Test
    public void convertStringToTask_validEventTaskString_success() throws CodyException {
        String eventTaskString = "[E][ ] hello (from: 2020-06-01 to: 2020-06-02)";
        Event event = Event.convertStringToTask(eventTaskString);
        LocalDate expectedStartDate = LocalDate.parse("2020-06-01");
        LocalDate expectedEndDate = LocalDate.parse("2020-06-02");
        String expectedDescription = "hello";
        boolean expectedIsDone = false;
        assertAll("Event fields", () -> assertEquals(expectedStartDate, event.startDate, "startDate should match"),
                () -> assertEquals(expectedEndDate, event.endDate, "endDate should match."),
                () -> assertEquals(expectedIsDone, event.isDone, "isDone should match"),
                () -> assertEquals(expectedDescription, event.description, "description should match"));
    }

    @Test
    public void convertStringToTask_invalidEventStartDate_exceptionThrown() throws CodyException {
        String eventTaskString = "[E][ ] hello (from: 6 June 2020 to: 2020-06-02)";
        try {
            Event.convertStringToTask(eventTaskString);
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Text '6 June 2020' could not be parsed at index 0", e.getMessage());
        }
    }
}
