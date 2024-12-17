package data;

import exceptions.InvalidPairingArgsException;

/**
 * Represents an immutable user account.
 */
public final class UserAccount {

    private final String username;

    /**
     * Constructor for UserAccount with validation.
     *
     * @param username The user's name or identifier.
     * @throws InvalidPairingArgsException If username is null, empty, or invalid.
     */
    public UserAccount(String username) throws InvalidPairingArgsException {
        if (username == null || username.isEmpty()) {
            throw new InvalidPairingArgsException("Username cannot be null or empty.");
        }
        if (!username.matches("[A-Za-z0-9_]{3,20}")) {
            throw new InvalidPairingArgsException("Username must be 3 to 20 alphanumeric characters or underscores.");
        }
        this.username = username;
    }

    /**
     * Getter for the user's username.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "UserAccount {username='" + username + "'}";
    }
}
