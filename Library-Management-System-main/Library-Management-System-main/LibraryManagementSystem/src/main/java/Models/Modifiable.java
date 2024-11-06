package Models;

import java.time.LocalDate;

public interface Modifiable {

    StandardViewResponse<User> editUser(String name, String surname, String username,
            String salary, String phoneNum, int id, Gender gender, int accessLevel, LocalDate localDate,
            String password);

    StandardViewResponse<User> addUser(String name, String surname, String username,
            String password, String salary, String phoneNum, LocalDate localDate,
            Gender gender, int accessLevel, String checkPassword);

    User findUserById(int id);

    boolean deleteUserById(int id);
    void changeAccessLevel(int newAccessLevel);
}
