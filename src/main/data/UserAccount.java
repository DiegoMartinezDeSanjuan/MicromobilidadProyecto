package data;

import exceptions.InvalidPairingArgsException;

/**
 * Representa una cuenta de usuario inmutable.
 */
public final class UserAccount {

    private final String username;

    /**
     * Constructor de UserAccount con validación.
     *
     * @param username El nombre de usuario o identificador.
     * @throws InvalidPairingArgsException Si el nombre de usuario es nulo, vacío o no válido.
     */
    public UserAccount(String username) throws InvalidPairingArgsException {
        validarUsername(username);
        this.username = username;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Valida el nombre de usuario.
     *
     * @param username El nombre de usuario a validar.
     * @throws InvalidPairingArgsException Si el nombre de usuario no cumple con los requisitos.
     */
    private void validarUsername(String username) throws InvalidPairingArgsException {
        if (username == null || username.isEmpty()) {
            throw new InvalidPairingArgsException("El nombre de usuario no puede ser nulo o vacío.");
        }
        if (!username.matches("[A-Za-z0-9_]{3,20}")) {
            throw new InvalidPairingArgsException("El nombre de usuario debe tener entre 3 y 20 caracteres alfanuméricos o guiones bajos.");
        }
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
        return java.util.Objects.hash(username);
    }

    @Override
    public String toString() {
        return "UserAccount {username='" + username + "'}";
    }
}
