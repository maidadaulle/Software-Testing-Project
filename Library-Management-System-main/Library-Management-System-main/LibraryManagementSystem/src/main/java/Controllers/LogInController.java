package Controllers;

import Models.User;
import Models.StandardViewResponse;

public class LogInController {

    public StandardViewResponse<User> OnLogInBtnClick(String username, String password) {
        if (username.isEmpty()) {
            return new StandardViewResponse<User>(null, "Username cannot be null!");
        }
        if (password.isEmpty()) {
            return new StandardViewResponse<User>(null, "Password cannot be null!");

        }

        for (User user : FileController.users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    return new StandardViewResponse<User>(user, "");
                } else {
                    return new StandardViewResponse<User>(null, "Wrong Password!");
                }
            }
        }
        return new StandardViewResponse<User>(null, "Wrong Credentials!");

    }
}
