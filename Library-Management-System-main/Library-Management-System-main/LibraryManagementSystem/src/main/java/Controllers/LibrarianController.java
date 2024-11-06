package Controllers;

import Models.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import static Controllers.BookController.isSameDay;

public class LibrarianController implements Modifiable {
    @Override
    public StandardViewResponse<User> editUser(String name, String surname, String username,
            String salary, String phoneNum, int id, Gender gender, int accessLevel, LocalDate localDate,
            String password) {
        double salaryDouble;
        Librarian librarian = findUserById(id);
        try {

            if (name.isEmpty() || surname.isEmpty() || username.isEmpty()
                    || salary.isEmpty()) {
                return new StandardViewResponse<>(librarian, "Fields are empty!");
            }

            if (name.length() < 3 || name.length() > 20) {
                return new StandardViewResponse<>(librarian, "Name can't have this length!");
            } else if (name.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(librarian, "Name can't contain numbers!");
            }
            for (char ch : name.toCharArray()) {
                if (isSpecialChar(ch)) {
                    return new StandardViewResponse<>(librarian, "Name can't contain special characters!");
                }
            }
            if (!Character.isUpperCase(name.charAt(0))) {
                // Convert the first letter to uppercase
                name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            }

            if (surname.length() < 3 || surname.length() > 20) {
                return new StandardViewResponse<>(librarian, "Surname cannot have this length!");
            } else if (surname.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(librarian, "Surname can't contain numbers!");
            }
            for (char ch : surname.toCharArray()) {
                if (isSpecialChar(ch)) {
                    return new StandardViewResponse<>(librarian, "Surname can't contain special characters!");
                }
            }
            if (!Character.isUpperCase(surname.charAt(0))) {
                // Convert the first letter to uppercase
                surname = Character.toUpperCase(surname.charAt(0)) + surname.substring(1);
            }

            if (!phoneNum.matches("^\\+355 6[0-9] [0-9]{3} [0-9]{4}$")) {
                return new StandardViewResponse<>(librarian,
                        "Phone number must be of specified format +355 6X XXX XXXX!");
            } else if (!isUniqueUsername(username) && !findUserById(id).getUsername().equals(username)) {
                return new StandardViewResponse<>(librarian, "There already exists a user with this username");
            }
            if (!isValidPassword(password).isEmpty()) {
                return new StandardViewResponse<>(librarian, isValidPassword(password));
            }
            LocalDate localDateCompare = LocalDate.now();
            if (localDate.isAfter(localDateCompare)) {
                return new StandardViewResponse<>(librarian, "BirthDate cannot be after actual date!");
            } else if (localDate.isBefore(localDate.minusYears(100))) {
                return new StandardViewResponse<>(librarian, "You cannot be this old!");
            }
            salaryDouble = Double.parseDouble(salary);

            librarian.setName(name);
            librarian.setSurname(surname);
            librarian.setUsername(username);
            librarian.setPhoneNum(phoneNum);
            librarian.setSalary(salaryDouble);
            librarian.setBirthDate(localDate);
            librarian.setGender(gender);
            librarian.setAccessLevel(accessLevel);
            librarian.setPassword(password);

            System.out.println("Librarian was successfully edited");
        } catch (NumberFormatException n) {
            System.out.println(n.getMessage());
            return new StandardViewResponse<>(librarian, "Cannot parse to double!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(librarian, e.getMessage());
        }
        return new StandardViewResponse<>(librarian, "");
    }

    @Override
    public StandardViewResponse<User> addUser(String name, String surname, String username,
            String password, String salary, String phoneNum, LocalDate localDate,
            Gender gender, int accessLevel, String checkPassword) {
        double salaryDouble;
        User librarian = null;
        try {

            if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()
                    || salary.isEmpty()) {
                return new StandardViewResponse<>(librarian, "Fields are empty!");
            }

            if (name.length() < 3 || name.length() > 20) {
                return new StandardViewResponse<>(librarian, "Name cannot have this length!");
            } else if (name.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(librarian, "Name can't contain numbers!");
            }
            for (char ch : name.toCharArray()) {
                if (isSpecialChar(ch)) {
                    return new StandardViewResponse<>(librarian, "Name can't contain special characters!");
                }
            }
            if (!Character.isUpperCase(name.charAt(0))) {
                // Convert the first letter to uppercase
                name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            }

            if (surname.length() < 3 || surname.length() > 20) {
                return new StandardViewResponse<>(librarian, "Surname cannot have this length!");
            } else if (surname.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(librarian, "Surname can't contain numbers!");
            }
            for (char ch : surname.toCharArray()) {
                if (isSpecialChar(ch)) {
                    return new StandardViewResponse<>(librarian, "Surname can't contain special characters!");
                }
            }
            if (!Character.isUpperCase(surname.charAt(0))) {
                // Convert the first letter to uppercase
                surname = Character.toUpperCase(surname.charAt(0)) + surname.substring(1);
            }

            if (!isValidPassword(password).isEmpty()) {
                return new StandardViewResponse<>(librarian, isValidPassword(password));
            }
            if (!phoneNum.matches("^\\+355 6[0-9] [0-9]{3} [0-9]{4}$")) {
                return new StandardViewResponse<>(librarian,
                        "Phone number must be of specified format +355 6X XXX XXXX!");
            } else if (!isUniqueUsername(username)) {
                return new StandardViewResponse<>(librarian, "There already exists a user with this username");
            }
            if (!checkPassword.equals(password)) {
                return new StandardViewResponse<>(librarian, "The password must match with verify password!");
            }
            salaryDouble = Double.parseDouble(salary);
            LocalDate localDateCompare = LocalDate.now();
            if (localDate.isAfter(localDateCompare)) {
                return new StandardViewResponse<>(librarian, "BirthDate cannot be after actual date!");
            } else if (localDate.isBefore(localDate.minusYears(100))) {
                return new StandardViewResponse<>(librarian, "You cannot be this old!");
            }
            librarian = new Librarian(name, surname, username,
                    password, salaryDouble, phoneNum, gender, localDate, accessLevel);
            addLibrarian(librarian);
            System.out.println("Librarian was successfully added");
        } catch (NumberFormatException n) {
            System.out.println(n.getMessage());
            return new StandardViewResponse<>(librarian, "Cannot parse to double!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(librarian, e.getMessage());
        }
        return new StandardViewResponse<>(librarian, "");
    }

    static String isValidPassword(String password) {
        // Define validation criteria
        int minLength = 8;
        int maxLength = 20;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        if (password.length() < minLength) {
            return "Password Minimum length is 8!";
        } else if (password.length() > maxLength) {
            return "Password Maximum length is 20!";
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
            return "Password must have at least 1 upper case!";
        } else if (!hasLowercase) {
            return "Password must have at least 1 lower case!";
        } else if (!hasSpecialChar) {
            return "Password must contain at least 1 special character!";
        } else if (!hasDigit) {
            return "Password must contain at least 1 digit!";
        }
        // Check all criteria
        return "";
    }

    static boolean isSpecialChar(char ch) {
        // Define your set of special characters
        String specialChars = "!@#$%^&*()._";
        return specialChars.contains(String.valueOf(ch));
    }

    static boolean isUniqueUsername(String username) {
        for (User librarian : FileController.users) {
            if (librarian.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Librarian findUserById(int id) {
        for (User librarian : FileController.users) {
            if (librarian.getId() == id)
                return (Librarian) librarian;
        }
        return null;
    }

    public void addLibrarian(User librarian) {
        FileController.users.add(librarian);
    }

    public Librarian findLibrarian(int index) {
        ArrayList<Librarian> librarians = new ArrayList<>();
        for (User librarian : FileController.users) {
            if (librarian instanceof Librarian) {
                librarians.add((Librarian) librarian);
            }
        }
        return librarians.get(index);
    }

    public void deleteLibrarianByUsername(String username) {
        for (int i = 0; i < FileController.users.size(); i++) {
            if (FileController.users.get(i).getUsername().equals(username)) {
                deleteUserById(i);
                return;
            }
        }
    }

    @Override
    public boolean deleteUserById(int id) {
        var exists = findUserById(id);
        if (exists == null) {
            return false;
        }
        for (User librarian : FileController.users) {
            if (librarian instanceof Librarian) {
                if (librarian.getId() == id) {
                    FileController.users.remove(librarian);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void changeAccessLevel(int newAccessLevel) {

        var users = FileController.users;
        for(User user : users)
        {
            if(user instanceof Librarian)
            {
                user.setAccessLevel(newAccessLevel);
            }
        }
    }

    public double calculateMoneyMadeToday(Librarian librarian) {
        double result = 0;
        var bills = FileController.transactions;
        for (var bill : bills) {
            if(bill.getType() == BillsType.Sold) {
                if (isSameDay(bill.getCreatedDate(), new Date()) && bill.getSoldBy() == librarian.getId()) {
                    result += bill.getTotalPrice();
                }
            }
        }
        return result;
    }

    public double calculateMoneyMadeThisMonth(Librarian librarian) {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        double result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            if (bill.getCreatedDate().toInstant().isAfter(beforeMonth.toInstant())
                    && bill.getSoldBy() == librarian.getId() && bill.getType() == BillsType.Sold) {
                result += bill.getTotalPrice();
            }
        }
        return result;
    }

    public double calculateMoneyMadeThisYear(Librarian librarian) {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        double result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            if (bill.getCreatedDate().toInstant().isAfter(beforeMonth.toInstant())
                    && bill.getSoldBy() == librarian.getId() && bill.getType() == BillsType.Sold) {
                result += bill.getTotalPrice();
            }
        }
        return result;
    }

    public int totalNoBillsByLibrarian(Librarian librarian) {
        int result = 0;
        var bills = FileController.transactions;
        for (var bill : bills) {
            if (bill.getSoldBy() == librarian.getId() && bill.getType() == BillsType.Sold) {
                result++;
            }
        }
        return result;
    }

    public int totalNoBooksSoldByLibrarian(Librarian librarian) {
        int result = 0;
        var bills = FileController.transactions;
        for (var bill : bills) {
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Sold && bill.getSoldBy() == librarian.getId()) {
                for (var quantity : quantities) {
                    result+=quantity;
                }
            }
        }
        return result;
    }

}
