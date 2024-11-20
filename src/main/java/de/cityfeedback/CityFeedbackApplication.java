package de.cityfeedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CityFeedbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityFeedbackApplication.class, args);
//
//        try {
//            String username = "asdb@asda";
//            Validation.validateUsername(username);
//        } catch (Exception e) {
//            if (e instanceof WrongUserInputException) {
//                System.out.println(e.getMessage());
//            }
//        }
    }

}
