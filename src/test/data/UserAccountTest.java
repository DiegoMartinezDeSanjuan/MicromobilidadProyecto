package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {

    @Test
    void testValidUserAccount() throws InvalidPairingArgsException {
        UserAccount user = new UserAccount("diego123");
        assertEquals("diego123", user.getUsername());
    }

    @Test
    void testInvalidUsername_Null() {
        assertThrows(InvalidPairingArgsException.class, () -> new UserAccount(null));
    }

    @Test
    void testInvalidUsername_Empty() {
        assertThrows(InvalidPairingArgsException.class, () -> new UserAccount(""));
    }

    @Test
    void testInvalidUsername_TooShort() {
        assertThrows(InvalidPairingArgsException.class, () -> new UserAccount("d1"));
    }

    @Test
    void testInvalidUsername_InvalidCharacters() {
        assertThrows(InvalidPairingArgsException.class, () -> new UserAccount("diego@123"));
    }

    @Test
    void testEqualsAndHashCode() throws InvalidPairingArgsException {
        UserAccount user1 = new UserAccount("diego123");
        UserAccount user2 = new UserAccount("diego123");
        UserAccount user3 = new UserAccount("maria456");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}
