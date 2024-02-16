import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    // EXAMPLES FROM PUBLIC TESTS

    @Test
    @DisplayName("Constructor")
    //Dependencies: getRepoHead
    public void testConstructor() {
        Repository r = new Repository("r");
        assertEquals(null, r.getRepoHead(), 
        "getRepoHead does not return null directly after constructor");
    }

    @Test
    @DisplayName("Constructor Exceptions")
    public void testConstructorExceptions() {
        assertThrows(IllegalArgumentException.class, () -> {Repository r = new Repository(null);}, 
        "null passed into constructor does not throw exception");
        assertThrows(IllegalArgumentException.class, () -> {Repository r = new Repository("");}, 
        "\"\" passed into constructor does not throw exception");
    }


    // YOUR TESTS HERE
}

