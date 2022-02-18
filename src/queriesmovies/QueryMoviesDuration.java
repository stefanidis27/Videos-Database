package queriesmovies;

import fileio.Input;
import fileio.MovieInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortMoviesByLongestAsc;
import sort.SortMoviesByLongestDesc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used for the query movies by duration
 */
public final class QueryMoviesDuration {
    /**
     * Method that implements the query for movies by duration
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
        ArrayList<MovieInputData> copy = new ArrayList<>(input.getMovies());
        if (input.getCommands().get(i).getSortType().equals("asc")) {
            copy.sort(new SortMoviesByLongestAsc());
        } else {
            copy.sort(new SortMoviesByLongestDesc());
        }
        int movieNumber = 0;
        for (MovieInputData movie : input.getMovies()) {
            int yearNumber = movie.getYear();
            String year = Integer.toString(yearNumber);
            String genre = input.getCommands().get(i).getFilters().get(1).get(0);
            if (input.getCommands().get(i).getFilters().get(0).contains(null)) {
                if (!(genre == null)) {
                    if (movie.getGenres().contains(genre)) {
                        movieNumber++;
                    }
                } else {
                    movieNumber++;
                }
            } else {
                if (genre == null) {
                    if (input.getCommands().get(i).getFilters().get(0).
                            contains(year)) {
                        movieNumber++;
                    }
                } else {
                    if (input.getCommands().get(i).getFilters().get(0).
                            contains(year)
                            && movie.getGenres().contains(genre)) {
                        movieNumber++;
                    }
                }
            }
        }
        int count = movieNumber;
        movieNumber = 0;
        for (MovieInputData movie : copy) {
            int yearNumber = movie.getYear();
            String year = Integer.toString(yearNumber);
            String genre = input.getCommands().get(i).getFilters().get(1).get(0);
            if (!(input.getCommands().get(i).getFilters().get(0).contains(null)
                    && genre == null)) {
                if ((input.getCommands().get(i).getFilters().get(0).contains(year)
                        || input.getCommands().get(i).getFilters().get(0).
                        contains(null)) && movie.getGenres().contains(genre)) {
                    movieNumber++;
                    if (movieNumber > n) {
                        break;
                    }
                    message.append(movie.getTitle());
                    if (movieNumber != n && movieNumber != count) {
                        message.append(", ");
                    }
                }
            } else {
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
    private QueryMoviesDuration() {
    }
}
