package Models;

import java.io.Serial;
import java.time.LocalDate;

public class Manager extends User {
    @Serial
    private static final long serialVersionUID = -5568420036250481739L;
    // private ArrayList<Date> datesSold;
    // private ArrayList<Double> moneyMadeDates;

    public Manager(String username, String password) {
        super(username, password, Roles.Manager);
    }

    public Manager(String name, String surname, String username, Roles userRole, String password,
            double salary, String phoneNum, Gender gender, LocalDate date, int accesslevel) {
        super(name, surname, username, userRole, password, salary, phoneNum, date, gender, accesslevel);
    }

    public Manager() {

    }

    // public double moneyMadeInDay() {

    // if (this.datesSold==null) {
    // return 0;
    // }

    // double ans=0;
    // Date today = new Date();

    // for (int i=0;i<this.datesSold.size();i++) {

    // if ( datesSold.get(i).getYear()==today.getYear() &&
    // datesSold.get(i).getMonth()==today.getMonth() && datesSold.get(i).getDay() ==
    // today.getDay()) {
    // ans+=moneyMadeDates.get(i);
    // }

    // }

    // return ans;

    // }

    // public double moneyMadeInMonth() {

    // if (this.datesSold==null) {
    // return 0;
    // }

    // double ans=0;
    // Date today = new Date();

    // for (int i=0;i<datesSold.size();i++) {

    // if (datesSold.get(i).getYear() == today.getYear() &&
    // datesSold.get(i).getMonth()==today.getMonth()) {
    // ans+=moneyMadeDates.get(i);
    // }
    // }

    // return ans;

    // }

    // public double moneyMadeInYear() {

    // if (this.datesSold==null) {
    // return 0;
    // }

    // double ans=0;
    // Date today = new Date();

    // for (int i=0;i<datesSold.size();i++) {

    // if (datesSold.get(i).getYear() == today.getYear()) {
    // ans+=moneyMadeDates.get(i);
    // }
    // }

    // return ans;

    // }

}
