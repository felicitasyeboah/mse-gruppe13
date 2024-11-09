import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class ValidationTest {

    @Test
    public void testValidUsername() {
        assertTrue(Validation.isValidUsername("MaxMustermann"));
        assertTrue(Validation.isValidUsername("user123"));
        assertFalse(Validation.isValidUsername("user@123"));
        assertFalse(Validation.isValidUsername("too_long_username_exceeding_limit"));
    }

    @Test
    public void testValidEmail() {
        assertTrue(Validation.isValidEmail("max.mustermann@example.com"));
        assertTrue(Validation.isValidEmail("user123@mail.de"));
        assertFalse(Validation.isValidEmail("max.mustermann@com"));
        assertFalse(Validation.isValidEmail("maxmustermann@.com"));
    }

    @Test
    public void testValidComplaintTitle() {
        assertTrue(Validation.isValidComplaintTitle("Lärmbelästigung im Wohngebiet"));
        assertTrue(Validation.isValidComplaintTitle("Verkehrsunfall auf der Hauptstraße"));
        assertFalse(Validation.isValidComplaintTitle("")); // Titel darf nicht leer sein
        assertFalse(Validation.isValidComplaintTitle("a")); // Titel zu kurz
    }

    @Test
    public void testValidComplaintDescription() {
        assertTrue(Validation.isValidComplaintDescription("Es gibt zu viele Autos auf der Straße, was zu gefährlichen Situationen führt."));
        assertFalse(Validation.isValidComplaintDescription("")); // Beschreibung darf nicht leer sein
    }

    /**@Test
    public void testValidCategory() {
        assertTrue(Validation.isValidCategory("Verkehr"));
        assertTrue(Validation.isValidCategory("Umwelt"));
        assertFalse(Validation.isValidCategory("Unbekannt")); // Ungültige Kategorie
    } **/
}

