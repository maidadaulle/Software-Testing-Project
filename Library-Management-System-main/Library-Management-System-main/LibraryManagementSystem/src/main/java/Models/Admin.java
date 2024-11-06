package Models;

import java.io.Serial;
import java.time.LocalDate;

public class Admin extends User {
    @Serial
    private static final long serialVersionUID = -6732666656741093238L;

    public Admin(String username, String password) {
        super(username, password, Roles.Admin);
    }

    public Admin(String name, String surname, String username, Roles userRole, String password,
            double salary, String phoneNum, Gender gender, LocalDate date, int accessLevel) {
        super(name, surname, username, userRole, password, salary, phoneNum, date, gender, accessLevel);

    }

}
