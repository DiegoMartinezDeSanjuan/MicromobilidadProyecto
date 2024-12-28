package data;

import exceptions.InvalidPairingArgsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test para la clase UserAccount.
 * Verifica la creación, validaciones y comportamiento de igualdad.
 */
class UserAccountTest {

    /**
     * Verifica que se pueda crear un UserAccount válido.
     */
    @Test
    void testCreateValidUserAccount() throws InvalidPairingArgsException {
        UserAccount user = new UserAccount("diego123");
        assertEquals("diego123", user.getUsername(), "El nombre de usuario debería coincidir con el proporcionado.");
    }

    /**
     * Verifica que un nombre de usuario nulo lance una excepción.
     */
    @Test
    void testUsernameNullThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new UserAccount(null), "Un nombre de usuario nulo debería lanzar una excepción.");
    }

    /**
     * Verifica que un nombre de usuario vacío lance una excepción.
     */
    @Test
    void testUsernameEmptyThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new UserAccount(""), "Un nombre de usuario vacío debería lanzar una excepción.");
    }

    /**
     * Verifica que un nombre de usuario demasiado corto lance una excepción.
     */
    @Test
    void testUsernameTooShortThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new UserAccount("d1"), "Un nombre de usuario demasiado corto debería lanzar una excepción.");
    }

    /**
     * Verifica que un nombre de usuario con caracteres inválidos lance una excepción.
     */
    @Test
    void testUsernameInvalidCharactersThrowsException() {
        assertThrows(InvalidPairingArgsException.class, () -> new UserAccount("diego@123"), "Un nombre de usuario con caracteres inválidos debería lanzar una excepción.");
    }

    /**
     * Verifica los métodos equals y hashCode.
     */
    @Test
    void testEqualsAndHashCode() throws InvalidPairingArgsException {
        UserAccount user1 = new UserAccount("diego123");
        UserAccount user2 = new UserAccount("diego123");
        UserAccount user3 = new UserAccount("maria456");

        assertEquals(user1, user2, "Los usuarios deberían ser iguales.");
        assertNotEquals(user1, user3, "Los usuarios deberían ser diferentes.");
        assertEquals(user1.hashCode(), user2.hashCode(), "Los hashCode deberían coincidir.");
        assertNotEquals(user1.hashCode(), user3.hashCode(), "Los hashCode deberían ser diferentes.");
    }
}
