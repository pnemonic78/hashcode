import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Library {
    final int id;
    final List<Book> books = new ArrayList<>();

    /**
     * the number of books
     */
    int N;
    /**
     * the number of days it takes to finish the library signup process
     */
    int T;
    /**
     * the number of books that can be shipped
     */
    int M;

    long booksScore;
    long booksScorePerSignup;
    long booksScorePerDay;
    final List<Book> booksToScan = new ArrayList<>();
    int daysScanning;
    int daysIdle;

    private static Comparator<Book> sorter;

    public Library(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" + id + ", N: " + N + ", T: " + T + ", M: " + M + "}";
    }

    public void init(int daysRemaining) {
        if (sorter == null) {
            sorter = new SortBooks();
        }
        Collections.sort(books, sorter);

        int daysMax = max(daysRemaining - T, 0);
        long booksMax = ((long) daysMax) * M;
        int booksTotal = (int) min(booksMax, books.size());

        Book book;
        booksScore = 0;
        daysScanning = max(1, booksTotal / M);
        daysIdle = max(daysMax - daysScanning, 0);

        for (int i = 0; i < booksTotal; i++) {
            book = books.get(i);
            booksScore += book.score;
        }
        booksScorePerSignup = booksScore / T;
        booksScorePerDay = booksScore / daysScanning;
    }

    public void scan(int daysRemaining) {
        booksToScan.clear();

        int daysMax = max(daysRemaining - T, 0);
        long booksMax = ((long) daysMax) * M;
        int booksTotal = (int) min(booksMax, books.size());

        for (int i = 0; i < booksTotal; i++) {
            Book book = books.get(i);
            booksToScan.add(book);
        }
    }

    public void add(Book book) {
        books.add(book);
    }
}
