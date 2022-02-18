package recommendationspremium;

import entertainment.Genre;
import entertainment.Season;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortShowsByRatingAsc;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class used for the search recommendation
 */
public final class RecommendSearch {
    /**
     * Method that implements the query for the search recommendation
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
            message.append("SearchRecommendation cannot be applied!");
            String finalMessage = message.toString();
            arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
        } else {
            String genreName = input.getCommands().get(i).getGenre();
            int ok1 = 0;
            for (Genre genre : Genre.values()) {
                if (genre == Utils.stringToGenre(genreName)) {
                    ok1 = 1;
                    break;
                }
            }
            if (ok1 == 0) {
                message.append("SearchRecommendation cannot be applied!");
                String finalMessage = message.toString();
                arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
            } else {
                for (MovieInputData movie : input.getMovies()) {
                    movie.setTotalRating(0);
                    if (!movie.getRatings().isEmpty()) {
                        movie.setTotalRating(movie.getRatings().stream().mapToDouble(d
                                -> d).sum() / (double) movie.getRatings().size());
                    }
                }
                ArrayList<MovieInputData> copyMovies = new ArrayList<>(input.
                        getMovies());
                for (SerialInputData serial : input.getSerials()) {
                    serial.setTotalRating(0);
                    for (Season season : serial.getSeasons()) {
                        double seasonRatingSum = season.getRatings().stream().
                                mapToDouble(d -> d).sum();
                        if (seasonRatingSum != 0.0) {
                            serial.setTotalRating(serial.getTotalRating()
                                    + (seasonRatingSum / season.getRatings().size()));
                        }
                    }
                    if (serial.getTotalRating() != 0.0) {
                        serial.setTotalRating(serial.getTotalRating()
                                / serial.getNumberSeason());
                    }
                }
                ArrayList<SerialInputData> copySerials = new ArrayList<>(input.
                        getSerials());
                List<ShowInput> showList = Stream.concat(copyMovies.stream(),
                        copySerials.stream()).sorted(new SortShowsByRatingAsc()).
                        collect(Collectors.toList());
                long count = showList.stream().filter(s -> s.getGenres().
                        contains(genreName) && user.isPresent() && !user.get().getHistory().
                        containsKey(s.getTitle())).
                        count();
                if (count != 0.0) {
                    int showNumber = 0;
                    message.append("SearchRecommendation result: [");
                    for (ShowInput show : showList) {
                        if (show.getGenres().contains(genreName) && !user.get().
                                getHistory().containsKey(show.getTitle())) {
                            message.append(show.getTitle());
                            showNumber++;
                            if (showNumber != count) {
                                message.append(", ");
                            }
                        }
                    }
                    message.append("]");
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                } else {
                    message.append("SearchRecommendation cannot be applied!");
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                }
            }
        }
    }

    /**
     * for coding style
     */
    private RecommendSearch() {
    }
}
