package recommendationspremium;

import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortShowsByFavoriteNoNameDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class used for the favorite recommendation
 */
public final class RecommendFavorite {
    /**
     * Method that implements the query for the favorite recommendation
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
            message.append("FavoriteRecommendation cannot be applied!");
            String finalMessage = message.toString();
            arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
        } else {
            for (MovieInputData movie : input.getMovies()) {
                movie.setTotalFavorite(0);
                for (UserInputData currentUser : input.getUsers()) {
                    if (currentUser.getFavoriteMovies().contains(movie.getTitle())) {
                        movie.setTotalFavorite(movie.getTotalFavorite() + 1);
                    }
                }
            }
            ArrayList<MovieInputData> copyMovies = new ArrayList<>(input.getMovies());
            for (SerialInputData serial : input.getSerials()) {
                serial.setTotalFavorite(0);
                for (UserInputData currentUser : input.getUsers()) {
                    if (currentUser.getFavoriteMovies().contains(serial.getTitle())) {
                        serial.setTotalFavorite(serial.getTotalFavorite() + 1);
                    }
                }
            }
            ArrayList<SerialInputData> copySerials
                    = new ArrayList<>(input.getSerials());
            List<ShowInput> showList = Stream.concat(copyMovies.stream(),
                    copySerials.stream()).sorted(new SortShowsByFavoriteNoNameDesc()).
                    collect(Collectors.toList());
            int ok = 0;
            for (ShowInput show : showList) {
                if (user.isPresent() && !user.get().getHistory().containsKey(show.getTitle())) {
                    ok = 1;
                    message.append("FavoriteRecommendation result: ");
                    message.append(show.getTitle());
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                    break;
                }
            }
            if (ok == 0) {
                message.append("FavoriteRecommendation cannot be applied!");
                String finalMessage = message.toString();
                arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
            }
        }
    }

    /**
     * for coding style
     */
    private RecommendFavorite() {
    }
}
