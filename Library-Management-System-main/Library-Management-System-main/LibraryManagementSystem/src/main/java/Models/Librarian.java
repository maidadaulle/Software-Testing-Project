package Models;

import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Librarian extends User {

    @Serial
    private static final long serialVersionUID = -2328426394838809039L;
    private ArrayList<Date> datesSold;
    private ArrayList<Double> moneyMadeDates;

    public Librarian(String username, String password) {
        super(username, password, Roles.Librarian);
    }

    public Librarian(String name, String surname, String username, String password, double salary, String phoneNum,
            Gender gender, LocalDate date, int accesslevel) {
        super(name, surname, username, Roles.Librarian, password, salary, phoneNum, date, gender, accesslevel);
    }

    public Librarian() {

    }

    public double moneyMadeInDay() {

        if (this.datesSold == null) {
            return 0;
        }

        double ans = 0;
        Date today = new Date();

        for (int i = 0; i < this.datesSold.size(); i++) {

            if (datesSold.get(i).getYear() == today.getYear() && datesSold.get(i).getMonth() == today.getMonth()
                    && datesSold.get(i).getDay() == today.getDay()) {
                ans += moneyMadeDates.get(i);
            }

        }

        return ans;

    }

    public double moneyMadeInMonth() {

        if (this.datesSold == null) {
            return 0;
        }

        double ans = 0;
        Date today = new Date();

        for (int i = 0; i < datesSold.size(); i++) {

            if (datesSold.get(i).getYear() == today.getYear() && datesSold.get(i).getMonth() == today.getMonth()) {
                ans += moneyMadeDates.get(i);
            }
        }

        return ans;

    }

    public double moneyMadeInYear() {

        if (this.datesSold == null) {
            return 0;
        }

        double ans = 0;
        Date today = new Date();

        for (int i = 0; i < datesSold.size(); i++) {

            if (datesSold.get(i).getYear() == today.getYear()) {
                ans += moneyMadeDates.get(i);
            }
        }

        return ans;

    }

}
