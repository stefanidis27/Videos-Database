package queriesmovies;

import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortMoviesByViewsAsc;
import sort.SortMoviesByViewsDesc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used for the query movies by views
 */
public final class QueryMoviesViews {
    /**
     * Method that implements the query for movies by views
     *
     * @param input       represents the date base
     * @param i           represents the index of the action
     * @param id          represents the index written in output file
     * @param arrayResult represents the Json array written in output file
     * @param fileWriter  for writing in output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void queryMovies(final Input input, final int i, final int id,
                                   final JSONArray arrayResult,
                                   final Writer fileWriter) throws IOException {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        int n = input.getCommands().get(i).getNumber();
        for (MovieInputData movie : input.getMovies()) {
            movie.setTotalViews(0);
            for (UserInputData user : input.getUsers()) {
                if (user.getHistory().containsKey(movie.getTitle())) {
                    movie.setTotalViews(movie.getTotalViews()
                            + user.getHistory().get(movie.getTitle()));
                }
            }
            int yearNumber = movie.getYear();
            String year = Integer.toString(yearNumber);
            String genre = input.getCommands().get(i).getFilters().get(1).get(0);
            if (input.getCommands().get(i).getFilters().get(0).contains(null)) {
                if (!(genre == null)) {
                    if (!movie.getGenres().contains(genre)) {
                        movie.setTotalViews(0);
                    }
                }
            } else {
                if (!(genre == null)) {
                    if ((!input.getCommands().get(i).getFilters().get(0).
                            contains(year)) || !movie.getGenres().
                            contains(genre)) {
                        movie.setTotalViews(0);
                    }
                } else {
                    if (!input.getCommands().get(i).getFilters().get(0).
                            contains(year)) {
                        movie.setTotalViews(0);
                    }
                }
            }
        }
        ArrayList<MovieInputData> copy = new ArrayList<>(input.getMovies());
        if (input.getCommands().get(i).getSortType().equals("asc")) {
            copy.sort(new SortMoviesByViewsAsc());
        } else {
            copy.sort(new SortMoviesByViewsDesc());
        }
        long count = input.getMovies().stream().filter(m ->
                m.getTotalViews() != 0).count();
        int movieNumber = 0;
        for (MovieInputData movie : copy) {
            if (movie.getTotalViews() != 0) {
                movieNumber++;
                if (movieNumber > n) {
                    break;
                }
                message.append(movie.getTitle());
                if (movieNumber != n && movieNumber != count) {
                    message.append(", ");
                }
            }
        }
        message.append("]");
        String finalMessage = message.toString();
        arrayResult.add(fileWriter.writeFile(id, "",
                finalMessage));
    }

    /**
     * for coding style
     */
    private QueryMoviesViews() {
    }
}
