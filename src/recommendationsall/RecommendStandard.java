package recommendationsall;

import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.Optional;

/**
 * Class used for the standard recommendation
 */
public final class RecommendStandard {
    /**
     * Method that implements the query for the standard recommendation
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
        int ok = 0;
        for (MovieInputData movie : input.getMovies()) {
            if (user.isPresent() && !user.get().getHistory().containsKey(movie.getTitle())) {
                ok = 1;
                message.append("StandardRecommendation result: ");
                message.append(movie.getTitle());
                String finalMessage = message.toString();
                arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                break;
            }
        }
        if (ok == 0) {
            for (SerialInputData serial : input.getSerials()) {
                if (user.isPresent() && !user.get().getHistory().containsKey(serial.getTitle())) {
                    ok = 1;
                    message.append("StandardRecommendation result: ");
                    message.append(serial.getTitle());
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                    break;
                }
            }
        }
        if (ok == 0) {
            message.append("StandardRecommendation cannot be applied!");
            String finalMessage = message.toString();
            arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
        }
    }

    /**
     * for coding style
     */
    private RecommendStandard() {
    }
}
