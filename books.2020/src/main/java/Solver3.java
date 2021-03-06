import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver3 {
    public Solution solve(File fileIn) throws IOException {
        ProblemParser parser = new ProblemParser();
        Problem problem = parser.parse(fileIn);
        return solve(problem);
    }

    private Solution solve(Problem problem) {
        Solution solution = new Solution(problem);
        assign(problem, solution);
        return solution;
    }

    private void assign(Problem problem, Solution solution) {
        List<Library> librariesProblem = problem.libraries;
        List<Library> librariesSolution = solution.libraries;
        librariesSolution.clear();
        int daysRemaining = problem.D;
        Collection<Book> booksScanned;
        Library library;
        Comparator<Library> sorter = new SortLibraries3();
        int size = librariesProblem.size();

        for (int i = 0; i < size; i++) {
            library = librariesProblem.get(i);
            library.init(daysRemaining);
        }
        Collections.sort(librariesProblem, sorter);

        while (size > 0) {
            library = librariesProblem.remove(0);
            size--;
            booksScanned = library.scan(daysRemaining);
            if (booksScanned.size() > 0) {
                librariesSolution.add(library);
                daysRemaining -= library.T;
                if (daysRemaining <= 0) break;

                for (int i = 0; i < size; i++) {
                    library = librariesProblem.get(i);
                    library.books.removeAll(booksScanned);
                }
                Collections.sort(librariesProblem, sorter);
            }
        }
    }
}
