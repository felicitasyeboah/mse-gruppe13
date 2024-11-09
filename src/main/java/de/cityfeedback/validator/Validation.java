package de.cityfeedback.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    // Reguläre Ausdrücke
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$";  // Erlaubt Buchstaben, Zahlen und Unterstriche, Länge 3-20 Zeichen
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+[.][A-Za-z.]{2,}$"; // E-Mail-Validierung "^[a-z0-9_\\.\\]+@[a-z0-9_\\.\\-]+\\.[a-z\.]{2,6}$"; 
    private static final String COMPLAINT_TITLE_REGEX = "^.{3,100}$";  // Titel muss zwischen 3 und 100 Zeichen lang sein
    private static final String COMPLAINT_DESCRIPTION_REGEX = "^.{10,500}$";  // Beschreibung muss zwischen 10 und 500 Zeichen lang sein
    //private static final String CATEGORY_REGEX = "^(Verkehr|Umwelt|Sicherheit)$";  // Nur drei gültige Kategorien

    // Validierung des Benutzernamens
    public static boolean isValidUsername(String username) {
        if (username == null) return false;
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    // Validierung der E-Mail-Adresse
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Validierung des Beschwerdetitels
    public static boolean isValidComplaintTitle(String title) {
        if (title == null) return false;
        Pattern pattern = Pattern.compile(COMPLAINT_TITLE_REGEX);
        Matcher matcher = pattern.matcher(title);
        return matcher.matches();
    }

    // Validierung der Beschreibungen
    public static boolean isValidComplaintDescription(String description) {
        if (description == null) return false;
        Pattern pattern = Pattern.compile(COMPLAINT_DESCRIPTION_REGEX);
        Matcher matcher = pattern.matcher(description);
        return matcher.matches();
    }

    /**  // Validierung der Kategorien
     public static boolean isValidCategory(String category) {
     if (category == null) return false;
     Pattern pattern = Pattern.compile(CATEGORY_REGEX);
     Matcher matcher = pattern.matcher(category);
     return matcher.matches();
     } **/
}
