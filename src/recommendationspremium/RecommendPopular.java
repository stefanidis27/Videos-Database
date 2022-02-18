package recommendationspremium;

import entertainment.Genre;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortHashMap;
import utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class used for the popular recommendation
 */
public final class RecommendPopular {
    /**
     * Method that implements the query for the popular recommendation
     *
     * @param input       represents the date base
     * @param i           represents the index of the action
     * @param id          represents the index written in output file
     * @param arrayResult represents the Json array written in output file
     * @param fileWriter  for writing in output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void recommendPremium(final Input input, final int i, final int id,
                                        final JSONArray arrayResult,
                                        final Writer fileWriter) throws IOException {
        String username = input.getCommands().get(i).getUsername();
        Optional<UserInputData> user = input.getUsers().stream().filter(u ->
                u.getUsername().equals(username)).findFirst();
        StringBuilder message = new StringBuilder();
        if (user.isPresent() && !user.get().getSubscriptionType().equals("PREMIUM")) {
            message.append("PopularRecommendation cannot be applied!");
            String finalMessage = message.toString();
            arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
        } else {
            for (MovieInputData movie : input.getMovies()) {
                movie.setTotalViews(0);
                for (UserInputData currentUser : input.getUsers()) {
                    if (currentUser.getHistory().containsKey(movie.getTitle())) {
                        movie.setTotalViews(movie.getTotalViews()
                                + currentUser.getHistory().get(movie.getTitle()));
                    }
                }
            }
            for (SerialInputData serial : input.getSerials()) {
                serial.setTotalViews(0);
                for (UserInputData currentUser : input.getUsers()) {
                    if (currentUser.getHistory().containsKey(serial.getTitle())) {
                        serial.setTotalViews(serial.getTotalViews()
                                + currentUser.getHistory().get(serial.getTitle()));
                    }
                }
            }
            Map<String, Integer> popularGenres = new HashMap<>();
            for (Genre genre : Genre.values()) {
                popularGenres.put(genre.toString(), 0);
                for (MovieInputData movie : input.getMovies()) {
                    if (movie.getGenres().contains(Utils.
                            genreNameToString(genre.toString()))) {
                        popularGenres.put(genre.toString(), popularGenres.
                                get(genre.toString()) + movie.getTotalViews());
                    }
                }
                for (SerialInputData serial : input.getSerials()) {
                    if (serial.getGenres().contains(Utils.
                            genreNameToString(genre.toString()))) {
                        popularGenres.put(genre.toString(), popularGenres.
                                get(genre.toString()) + serial.getTotalViews());
                    }
                }
            }
            Map<String, Integer> popularGenresSorted = SortHashMap.
                    sortByValue(popularGenres);
            int okEntry = 0;
            for (Map.Entry<String, Integer> entry : popularGenresSorted.entrySet()) {
                int ok = 0;
                for (MovieInputData movie : input.getMovies()) {
                    if (user.isPresent() && !user.get().getHistory().containsKey(movie.getTitle())
                            && movie.getGenres().contains(Utils.
                            genreNameToString(entry.getKey()))) {
                        ok = 1;
                        okEntry = 1;
                        message.append("PopularRecommendation result: ");
                        message.append(movie.getTitle());
                        String finalMessage = message.toString();
                        arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                        break;
                    }
                }
                if (ok == 0) {
                    for (SerialInputData serial : input.getSerials()) {
                        if (user.isPresent() && !user.get().getHistory().
                                containsKey(serial.getTitle())
                                && serial.getGenres().contains(Utils.
                                genreNameToString(entry.getKey()))) {
                            ok = 1;
                            okEntry = 1;
                            message.append("PopularRecommendation result: ");
                            message.append(serial.getTitle());
                            String finalMessage = message.toString();
                            arrayResult.add(fileWriter.writeFile(id, "",
                                    finalMessage));
                            break;
                        }
                    }
                }
                if (okEntry == 1) {
                    break;
                }
            }
            if (okEntry == 0) {
                message.append("PopularRecommendation cannot be applied!");
                String finalMessage = message.toString();
                arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
            }
        }
    }

    /**
     * for coding style
     */
    private RecommendPopular() {
    }
}
