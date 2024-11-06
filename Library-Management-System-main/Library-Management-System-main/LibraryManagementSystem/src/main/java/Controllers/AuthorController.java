package Controllers;

import Models.*;


public class AuthorController {

    public void addAuthor(Author a) {
        FileController.authors.add(a);

    }

    public StandardViewResponse<Author> createAuthor(String name, String surname, Gender gender) {
        Author author=null;
        try {

            if (name.isEmpty() || surname.isEmpty()) {
                return new StandardViewResponse<>(author, "Fields are empty!");
            }

            if (name.length() < 3 || name.length() > 20) {
                return new StandardViewResponse<>(author, "Name can't have this length!");
            } else if (name.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(author, "Name can't contain numbers!");
            }
            for (char c : name.toCharArray()) {
                if (isSpecialChar(c)) {
                    return new StandardViewResponse<>(author, "Name can't contain special characters!");
                }
            }
            if (surname.length() < 3 || surname.length() > 20) {
                return new StandardViewResponse<>(author, "Surname cannot have this length!");
            } else if (surname.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(author, "Surname can't contain numbers!");
            }
            for (char c : surname.toCharArray()) {
                if (isSpecialChar(c)) {
                    return new StandardViewResponse<>(author, "Surname can't contain special characters!");
                }
            }
            if(!isUniqueAuthor(name, surname)) {
                return new StandardViewResponse<>(author, "There already exists an Author with this credentials");
            }
            System.out.println("Author was successfully added");
            author = new Author(name, surname, gender);
            addAuthor(author);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(author, e.getMessage());
        }

        return new StandardViewResponse<>(author, "");
    }

    public Author findAuthor(int id) {
        for (Author a : FileController.authors) {
            if (a.getID() == id)
                return a;
        }
        return null;
    }
    static boolean isSpecialChar(char c) {
        String specialChars = "!@#$%^&*()_";
        return specialChars.contains(String.valueOf(c));
    }
    static boolean isUniqueAuthor(String name, String surname) {
        for (Author author : FileController.authors) {
            if (author.getName().equals(name) && author.getSurname().equals(surname)) {
                return false;
            }
        }
        return true;
    }

    public StandardViewResponse<Author> editAuthor(int ID, String name, String surname, Gender gender) {
        Author author = findAuthor(ID);
        try {

            if (name.isEmpty() || surname.isEmpty()) {
                return new StandardViewResponse<>(author, "Fields are empty!");
            }

            if (name.length() < 3 || name.length() > 20) {
                return new StandardViewResponse<>(author, "Name can't have this length!");
            } else if (name.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(author, "Name can't contain numbers!");
            }
            for (char c : name.toCharArray()) {
                if (isSpecialChar(c)) {
                    return new StandardViewResponse<>(author, "Name can't contain special characters!");
                }
            }
            if (surname.length() < 3 || surname.length() > 20) {
                return new StandardViewResponse<>(author, "Surname cannot have this length!");
            } else if (surname.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(author, "Surname can't contain numbers!");
            }
            for (char c : surname.toCharArray()) {
                if (isSpecialChar(c)) {
                    return new StandardViewResponse<>(author, "Surname can't contain special characters!");
                }
            }
            if(!isUniqueAuthor(name, surname)) {
                return new StandardViewResponse<>(author, "There already exists an Author with this credentials");
            }
            author.setName(name);
            author.setSurname(surname);
            author.setGender(gender);
            System.out.println("Author was successfully edited");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(author, e.getMessage());
        }
        return new StandardViewResponse<>(author, "");
    }
}
