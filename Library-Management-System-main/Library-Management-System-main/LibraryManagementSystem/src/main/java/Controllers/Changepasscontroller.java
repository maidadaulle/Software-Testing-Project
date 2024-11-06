package Controllers;

import Models.StandardViewResponse;
import Models.User;

public class Changepasscontroller {
    public User findUserById(int id) {
        for (User user : FileController.users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public StandardViewResponse<User> changepass(String password, int id) {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        User user=findUserById(id);
        try {
            if (password.length() < 8) {
                return new StandardViewResponse<>(user, "Password Minimum length is 8!");
            }
            if(password.length()>20){
                return new StandardViewResponse<>(user, "Password Maximum length is 20!");
            }
            for (char ch : password.toCharArray()) {
                if (Character.isUpperCase(ch)) {
                    hasUppercase = true;
                } else if (Character.isLowerCase(ch)) {
                    hasLowercase = true;
                } else if (Character.isDigit(ch)) {
                    hasDigit = true;
                } else if (isSpecialChar(ch)) {
                    hasSpecialChar = true;
                }
            }
            if (!hasUppercase) {
                return new StandardViewResponse<>(user, "Password must have at least 1 upper case!");
            } else if (!hasLowercase) {
                return new StandardViewResponse<>(user, "Password must have at least 1 lower case!");
            } else if (!hasSpecialChar) {
                return new StandardViewResponse<>(user, "Password must contain at least 1 special character!");
            } else if (!hasDigit) {
                return new StandardViewResponse<>(user, "Password must contain at least 1 digit!");
            }
            user.setPassword(password);
            System.out.println("Password was successfully edited");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(user, e.getMessage());

        }
        return new StandardViewResponse<>(user, "");
    }

    static boolean isSpecialChar(char ch) {
        String specialChars = "!@#$%^&*()._";
        return specialChars.contains(String.valueOf(ch));
    }
}
