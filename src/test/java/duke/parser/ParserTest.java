package duke.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import duke.exception.CodyException;

public class ParserTest {
    @Test
    public void getDescriptionFromValidAddToDoCommand_validToDoCommand_success() throws CodyException {
        Parser parser = new Parser("todo this is my todo task");
        parser.getDescriptionFromValidAddToDoCommand();
        assertEquals(parser.getDescriptionFromValidAddToDoCommand(), "this is my todo task");
    }

    @Test
    public void getDescriptionFromValidAddToDoCommand_missingDescription_exceptionThrown() {
        Parser parser = new Parser("todo ");
        try {
            parser.getDescriptionFromValidAddToDoCommand();
            fail();
        } catch (CodyException e) {
            assertEquals(e.getMessage(), "Invalid add todo task command");
        }
    }

    @Test
    public void getDescriptionFromValidAddToDoCommand_missingToDoKeyword_exceptionThrown() {
        Parser parser = new Parser("wrong command");
        try {
            parser.getDescriptionFromValidAddToDoCommand();
            fail();
        } catch (CodyException e) {
            assertEquals(e.getMessage(), "Invalid add todo task command");
        }
    }
}
