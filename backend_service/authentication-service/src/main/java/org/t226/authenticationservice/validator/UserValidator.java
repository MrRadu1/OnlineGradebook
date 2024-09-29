package org.t226.authenticationservice.validator;

import org.t226.authenticationservice.exception.AppException;
import org.t226.authenticationservice.exception.ExceptionType;
import org.t226.authenticationservice.repository.UserRepository;
import org.t226.authenticationservice.user.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public static boolean validateUser(User user, UserRepository userRepository) {
        validateEmail(user.getEmail(), userRepository);
        validateUsername(user.getUsername(), userRepository);
        validatePassword(user.getPassword());
        return true;
    }

    public static boolean validateUserForUpdate(User user, UserRepository userRepository) {
        validateEmail(user.getEmail(), userRepository);
        validateUsername(user.getUsername(), userRepository);
        return true;
    }


    /**
     * Validates the email of the user.
     * <p>
     * The following restrictions are imposed in the email address' local part by using this regex:
     * <p>
     * It allows numeric values from 0 to 9.
     * Both uppercase and lowercase letters from a to z are allowed.
     * Allowed are underscore “_”, hyphen “-“, and dot “.”
     * Dot isn't allowed at the start and end of the local part.
     * Consecutive dots aren't allowed.
     * For the local part, a maximum of 64 characters are allowed.
     * <p>
     * Restrictions for the domain part in this regular expression include:
     * <p>
     * It allows numeric values from 0 to 9.
     * We allow both uppercase and lowercase letters from a to z.
     * Hyphen “-” and dot “.” aren't allowed at the start and end of the domain part.
     * No consecutive dots.
     *
     * @param email
     * @throws RuntimeException
     */
    public static void validateEmail(String email, UserRepository userRepository) throws RuntimeException {
        userRepository.findByEmail(email).ifPresent(
                user -> {
                    throw new AppException("Email already used", ExceptionType.EMAIL_ALREADY_EXISTS);
                });
        validateElement(email, "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    /**
     * Validates the user's username. Restrictions:
     * <p>
     * The username consists of 6 to 30 characters inclusive. If the username
     * consists of less than 6 or greater than 30 characters, then it is an invalid username.
     * The username can only contain alphanumeric characters and underscores (_). Alphanumeric characters describe
     * the character set consisting of lowercase characters [a – z], uppercase characters [A – Z], and digits [0 – 9].
     * The first character of the username must be an alphabetic character, i.e., either lowercase character
     * [a – z] or uppercase character [A – Z].
     *
     * @param username
     * @throws RuntimeException
     */
    public static void validateUsername(String username, UserRepository userRepository) throws RuntimeException {
        userRepository.findByUsername(username).ifPresent(
                user -> {
                    throw new AppException("Username already used", ExceptionType.USERNAME_ALREADY_EXISTS);
                });
        validateElement(username, "^[A-Za-z][A-Za-z0-9_]{5,29}$");
    }

    /**
     * Validates the user's password. Restrictions:
     * <p>
     * It contains at least 8 characters and at most 20 characters.
     * It contains at least one digit.
     * It contains at least one upper case alphabet.
     * It contains at least one lower case alphabet.
     * It contains at least one special character which includes !@#$%&*()-+=^.
     * It doesn’t contain any white space.
     *
     * @param password
     * @throws RuntimeException
     */
    public static void validatePassword(String password) throws RuntimeException {
        validateElement(password, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&-+=().])(?=\\S+$).{8,20}$");
    }


    private static void validateElement(String element, String regex) throws RuntimeException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(element);
        if (!matcher.matches()) {
            throw new AppException("The field " + element + " is not valid", ExceptionType.VALIDATION);
        }
    }


}
