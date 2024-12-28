package payment;

import data.GeographicPoint;
import data.ServiceID;
import data.UserAccount;
import micromobility.JourneyRealizeHandler;
import micromobility.payment.Wallet;
import micromobility.JourneyService;
import exceptions.InvalidPairingArgsException;
import exceptions.NotEnoughWalletException;
import exceptions.ProceduralException;
import exceptions.ConnectException;
import mocks.MockServer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para los métodos opcionales de la clase JourneyRealizeHandler.
 */
public class JourneyRealizeHandlerOptionalMethodTest {

    /**
     * Verifica que se pueda procesar un pago exitoso usando el monedero.
     */
    @Test
    void testSelectPaymentMethodWalletSuccess() throws InvalidPairingArgsException, ConnectException, ProceduralException {
        Wallet wallet = new Wallet(new BigDecimal("100.00"));
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());
        journey.setImportValue(new BigDecimal("50.00"));
        JourneyRealizeHandler handler = new JourneyRealizeHandler(null, null, null, null);

        handler.setWallet(wallet);
        handler.setCurrentJourney(journey);

        // Ejecutar y verificar
        assertDoesNotThrow(() -> handler.selectPaymentMethod('W'), "El método de pago debería procesarse correctamente usando el monedero");
        assertEquals(new BigDecimal("50.00"), wallet.getBalance(), "El saldo restante del monedero debería ser 50.00");
    }

    /**
     * Verifica que un método de pago inválido lance una excepción.
     */
    @Test
    void testSelectPaymentMethodInvalidOption() throws InvalidPairingArgsException {
        JourneyRealizeHandler handler = new JourneyRealizeHandler(null, null, null, null);

        // Ejecutar y verificar
        assertThrows(ProceduralException.class, () -> handler.selectPaymentMethod('X'), "Debería lanzar ProceduralException para un método de pago inválido");
    }

    /**
     * Verifica que se lance una excepción cuando el saldo del monedero es insuficiente.
     */
    @Test
    void testSelectPaymentMethodInsufficientWalletFunds() throws InvalidPairingArgsException {
        Wallet wallet = new Wallet(new BigDecimal("20.00"));
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());
        journey.setImportValue(new BigDecimal("50.00"));
        JourneyRealizeHandler handler = new JourneyRealizeHandler(null, null, null, null);

        handler.setWallet(wallet);
        handler.setCurrentJourney(journey);

        // Ejecutar y verificar
        assertThrows(NotEnoughWalletException.class, () -> handler.selectPaymentMethod('W'), "Debería lanzar NotEnoughWalletException cuando el saldo es insuficiente");
    }

    /**
     * Verifica que se pueda procesar un pago exitoso usando tarjeta de crédito.
     */
    @Test
    void testSelectPaymentMethodCreditCardSuccess() throws InvalidPairingArgsException, ConnectException {
        // Configurar el mock del servidor
        MockServer mockServer = new MockServer();

        // Configurar los datos del JourneyService
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());
        journey.setImportValue(new BigDecimal("50.00"));

        // Configurar un ServiceID y un UserAccount necesarios para el registro del pago
        ServiceID serviceID = new ServiceID("SVC123", new BigDecimal("50.00"));
        UserAccount user = new UserAccount("diego123");

        journey.setUser(user); // Asociar el usuario al JourneyService
        journey.setServiceID(serviceID); // Asociar el ServiceID al JourneyService

        // Crear la instancia de JourneyRealizeHandler con el mock del servidor
        JourneyRealizeHandler handler = new JourneyRealizeHandler(mockServer, null, null, null);
        handler.setCurrentJourney(journey);

        // Ejecutar y verificar
        assertDoesNotThrow(() -> handler.selectPaymentMethod('C'), "El método de pago debería procesarse correctamente usando tarjeta de crédito");
    }


    /**
     * Verifica que se pueda procesar un pago exitoso usando PayPal.
     */
    @Test
    void testSelectPaymentMethodPayPalSuccess() throws InvalidPairingArgsException, ConnectException {
        // Configurar el mock del servidor
        MockServer mockServer = new MockServer();

        // Configurar los datos del JourneyService
        GeographicPoint point = new GeographicPoint(41.3851f, 2.1734f);
        JourneyService journey = new JourneyService(point, LocalDate.now(), LocalTime.now());
        journey.setImportValue(new BigDecimal("50.00"));

        // Configurar un ServiceID y un UserAccount necesarios para el registro del pago
        ServiceID serviceID = new ServiceID("SVC123", new BigDecimal("50.00"));
        UserAccount user = new UserAccount("diego123");

        journey.setUser(user); // Asociar el usuario al JourneyService
        journey.setServiceID(serviceID); // Asociar el ServiceID al JourneyService

        // Crear la instancia de JourneyRealizeHandler con el mock del servidor
        JourneyRealizeHandler handler = new JourneyRealizeHandler(mockServer, null, null, null);
        handler.setCurrentJourney(journey);

        // Ejecutar y verificar
        assertDoesNotThrow(() -> handler.selectPaymentMethod('P'), "El método de pago debería procesarse correctamente usando PayPal");
    }

}
