package recommendationsall;

import entertainment.Season;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortShowsByRatingNoNameDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class used for the best unseen recommendation
 */
public final class RecommendBestUnseen {
    /**
     * Method that implements the query for the best unseen recommendation
     *
     * @param input       represents the date base
     * @param i           represents the index of the action
     * @param id          represents the index written in output file
     * @param arrayResult represents the Json array written in output file
     * @param fileWriter  for writing in output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void recommendAll(final Input input, final int i, final int id,
                                    final JSONArray arrayResult,
                                    final Writer fileWriter) throws IOException {
        String username = input.getCommands().get(i).getUsername();
        Optional<UserInputData> user = input.getUsers().stream().filter(u ->
                u.getUsername().equals(username)).findFirst();
        StringBuilder message = new StringBuilder();
        for (MovieInputData movie : input.getMovies()) {
            movie.setTotalRating(0);
            if (!movie.getRatings().isEmpty()) {
                movie.setTotalRating(movie.getRatings().stream().mapToDouble(d ->
                        d).sum() / (double) movie.getRatings().size());
            }
        }
        ArrayList<MovieInputData> copyMovies = new ArrayList<>(input.getMovies());
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
        ArrayList<SerialInputData> copySerials = new ArrayList<>(input.getSerials());
        List<ShowInput> showList = Stream.concat(copyMovies.stream(),
                copySerials.stream()).sorted(new SortShowsByRatingNoNameDesc()).
                collect(Collectors.toList());
        int ok = 0;
        for (ShowInput show : showList) {
            if (user.isPresent() && !user.get().getHistory().containsKey(show.getTitle())) {
                ok = 1;
                message.append("BestRatedUnseenRecommendation result: ");
                message.append(show.getTitle());
                String finalMessage = message.toString();
                arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                break;
            }
        }
        if (ok == 0) {
            message.append("BestRatedUnseenRecommendation cannot be applied!");
            String finalMessage = message.toString();
            arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
        }
    }

    /**
     * for coding style
     */
    private RecommendBestUnseen() {
    }
}
