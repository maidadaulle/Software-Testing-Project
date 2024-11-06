package Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Models.Admin;
import Models.Author;
import Models.Bill;
import Models.Book;
import Models.Category;
import Models.User;

public class FileController {
	public static ArrayList<User> users;
	public static ArrayList<Category> categories;
	public static ArrayList<Book> books;
	public static ArrayList<Bill> transactions;
	public static ArrayList<Author> authors;
	private static File authorsFile;
	private static File transactionsFile;
	private static File booksFile;
	private static File categoriesFile;
	private static File usersFile;

	private final static String USER_ADD = "binaryFiles/users.dat";
	private final static String CATEGORY_ADD = "binaryFiles/categories.dat";
	private final static String BOOK_ADD = "binaryFiles/books.dat";
	private final static String TRANSACTION_ADD = "binaryFiles/transactions.dat";
	private final static String AUTHOR_ADD = "binaryFiles/authors.dat";

	public FileController() {
		try {
			users = new ArrayList<>();
			usersFile = new File(USER_ADD);
			if (usersFile.exists()) {
				readUsers();
			} else {
				users.add(new Admin("admin", "admin"));
				writeUsers();
			}

			categories = new ArrayList<>();
			categoriesFile = new File(CATEGORY_ADD);
			if (categoriesFile.exists()) {
				readCategories();
			}

			books = new ArrayList<>();
			booksFile = new File(BOOK_ADD);
			if (booksFile.exists()) {
				readBooks();
			}

			transactions = new ArrayList<>();
			transactionsFile = new File(TRANSACTION_ADD);
			if (transactionsFile.exists()) {
				readTransactions();
			}

			authors = new ArrayList<>();
			authorsFile = new File(AUTHOR_ADD);
			if (authorsFile.exists()) {
				readAuthors();
			}
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void readUsers() {
		try {
			FileInputStream fis = new FileInputStream(usersFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			users = (ArrayList<User>) ois.readObject();
			User.setNoUsers(users.size());
			fis.close();
			ois.close();
			printAllUser();
			System.out.println("Users read from file successfully.");
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e.getMessage());
		}
	}

	public static void writeUsers() {
		try {
			FileOutputStream fos = new FileOutputStream(usersFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			oos.close();
			fos.close();
			System.out.println("Users written successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void printAllUser() {
		System.out.println();
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i));
		}
	}

	@SuppressWarnings("unchecked")
	private void readCategories() {
		try {
			FileInputStream fis = new FileInputStream(categoriesFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			categories = (ArrayList<Category>) ois.readObject();
			Category.setNoCategory(categories.size());
			fis.close();
			ois.close();
			System.out.println("Categories read successfully.");
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e.getMessage());
		}
	}

	public static void writeCategories() {
		try {
			FileOutputStream fos = new FileOutputStream(categoriesFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(categories);
			oos.close();
			fos.close();
			System.out.println("Categories written successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void readBooks() {
		try {
			FileInputStream fis = new FileInputStream(booksFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			books = (ArrayList<Book>) ois.readObject();
			fis.close();
			ois.close();
			printBooks();
			System.out.println("Books read successfully.");
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e.getMessage());
		}
	}

	public static void writeBooks() {
		try {
			FileOutputStream fos = new FileOutputStream(booksFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(books);
			oos.close();
			fos.close();
			System.out.println("Books written successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void printBooks() {
		for (int i = 0; i < books.size(); i++) {
			System.out.println(books.get(i));
		}
	}

	@SuppressWarnings("unchecked")
	public static void readTransactions() {
		try {
			FileInputStream fis = new FileInputStream(transactionsFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			transactions = (ArrayList<Bill>) ois.readObject();
			Bill.setTotalBills(transactions.size());
			fis.close();
			ois.close();
			System.out.println("Transactions read successfully.");
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e.getMessage());
		}
	}

	// public void print() {
	// 	System.out.println();
	// 	for (int i = 0; i < transactions.size(); i++) {
	// 		System.out.println(transactions.get(i));
	// 	}
	// }

	public static void writeTransactions() {
		try {
			FileOutputStream fos = new FileOutputStream(transactionsFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(transactions);
			oos.close();
			fos.close();
			System.out.println("Transactions written successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void readAuthors() {
		try {
			FileInputStream fis = new FileInputStream(authorsFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			authors = (ArrayList<Author>) ois.readObject();
			Author.setNoAuthor(authors.size());
			fis.close();
			ois.close();
			System.out.println("Authors read successfully.");
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e.getMessage());
		}
	}

	public static void writeAuthors() {
		try {
			FileOutputStream fos = new FileOutputStream(authorsFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(authors);
			oos.close();
			fos.close();
			System.out.println("Authors written successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
