package de.cityfeedback.shared.validator;

import de.cityfeedback.exception.WrongUserInputException;
import java.util.regex.Pattern;

public class Validation {

  // Reguläre Ausdrücke
  private static final String USERNAME_REGEX =
      "^[a-zA-Z0-9_]{3,20}$"; // Erlaubt Buchstaben, Zahlen und Unterstriche, Länge 3-20 Zeichen
  private static final String EMAIL_REGEX =
      "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+[.][A-Za-z.]{2,}$"; // E-Mail-Validierung
  private static final String FEEDBACK_TITLE_REGEX =
      "^.{3,100}$"; // Titel muss zwischen 3 und 100 Zeichen lang sein
  private static final String FEEDBACK_DESCRIPTION_REGEX =
      "^.{10,500}$"; // Beschreibung muss zwischen 10 und 500 Zeichen lang sein
  private static final String EMPLOYEE_FEEDBACK = "^.{3,200}$";

  // Validierung des Benutzernamens
  public static void validateUsername(String username) {
    if (username == null || !Pattern.matches(USERNAME_REGEX, username)) {
      throw new WrongUserInputException(
          "Ungültiger Benutzername. Der Benutzername muss zwischen 3 und 20 Zeichen lang sein und nur Buchstaben, Zahlen oder Unterstriche enthalten.");
    }
  }

  // Validierung der E-Mail-Adresse
  public static void validateEmail(String email) {
    if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
      throw new WrongUserInputException("Ungültige E-Mail-Adresse.");
    }
  }

  // Validierung des Passworts
  public static void validatePassword(String password) {
    if (password == null) {
      throw new WrongUserInputException("Passwort darf nicht leer sein.");
    }
  }

  // Validierung des Beschwerdetitels
  public static void validateFeedbackTitle(String title) {
    if (title == null || !Pattern.matches(FEEDBACK_TITLE_REGEX, title)) {
      throw new WrongUserInputException("Der Titel muss zwischen 3 und 100 Zeichen lang sein.");
    }
  }

  // Validierung der Beschreibungen
  public static void validateFeedbackContent(String content) {
    if (content == null || !Pattern.matches(FEEDBACK_DESCRIPTION_REGEX, content)) {
      throw new WrongUserInputException(
          "Die Beschreibung muss zwischen 10 und 500 Zeichen lang sein.");
    }
  }

  public static void validateComment(String comment) {
    if (comment == null || !Pattern.matches(EMPLOYEE_FEEDBACK, comment)) {
      throw new WrongUserInputException("Der Kommentar muss zwischen 3 und 200 Zeichen lang sein.");
    }
  }
}
