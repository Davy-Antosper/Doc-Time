package com.docTime.validation;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.regex.Pattern;

@Component
@Slf4j
public class Validator {

    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    public void validateHoraires(DayOfWeek day, LocalTime start, LocalTime end, Long doctorId, Long currentId) {
        if (day == null || start == null || end == null) {
            throw new ValidationException(ErrorType.INVALID_INPUT, "Le jour, l'heure de début et de fin sont obligatoires.");
        }
        if (!start.isBefore(end)) {
            throw new ValidationException(ErrorType.INVALID_INPUT, "L'heure de début doit être antérieure à l'heure de fin.");
        }

    }

    public void validate(String firstName, String lastName,String email,String phone, String countryCode) {

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ValidationException(ErrorType.INVALID_INPUT, "Le prénom est obligatoire.");
        }
        if (lastName == null || lastName.trim().length() < 2) {
            throw new ValidationException(ErrorType.INVALID_INPUT, "Le nom doit contenir au moins 2 caractères.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException(ErrorType.INVALID_INPUT, "L'adresse e-mail est obligatoire.");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            log.warn("Échec de validation : L'e-mail [{}] a un format incorrect.", email);
            throw new ValidationException(ErrorType.INVALID_INPUT, "L'adresse e-mail n'est pas valide.");
        }

        String defaultRegion = (countryCode != null) ? countryCode.toUpperCase() : "BI";

        if (phone != null && !phone.trim().isEmpty()) {
            try {
                //  un numéro du Burundi "+257".
                Phonenumber.PhoneNumber number = phoneUtil.parse(phone, defaultRegion);

                if (!phoneUtil.isValidNumber(number)) {
                    log.warn("Échec de validation : Le numéro [{}] n'est pas un numéro valide.", phone);
                    throw new ValidationException(ErrorType.INVALID_INPUT, "Le numéro de téléphone est incorrect ou invalide.");
                }

                //reformatte la donnée au standard mondial (E.164) [INDEX]
                // Si l'utilisateur a écrit "79 12 34 56", cela devient "+25779123456"
                String formattedPhone = phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);

                log.info("Téléphone validé  : {}", formattedPhone);

            } catch (Exception e) {
                log.warn("Échec de l'analyse du numéro de téléphone : {}", phone);
                throw new ValidationException(ErrorType.INVALID_INPUT, "Le format du numéro de téléphone n'est pas reconnu.");
            }
        }
    }
}
