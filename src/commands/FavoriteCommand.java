package commands;

import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.Optional;

/**
 * Class used for the favorite command given by a user
 */
public final class FavoriteCommand {
    /**
     * Method that implements the favorite command given by a user
     *
     * @param input       represents the date base
     * @param i           represents the index of the action
     * @param id          represents the index written in output file
     * @param arrayResult represents the Json array written in output file
     * @param fileWriter  for writing in output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void command(final Input input, final int i, final int id,
                               final JSONArray arrayResult,
                               final Writer fileWriter) throws IOException {
        String showTitle = input.getCommands().get(i).getTitle();
        String username = input.getCommands().get(i).getUsername();
        Optional<UserInputData> user =
                input.getUsers().stream().filter(u ->
                        u.getUsername().equals(username)).findFirst();
        StringBuilder message = new StringBuilder();
        if (user.isPresent()) {
            if (!user.get().getHistory().containsKey(showTitle)) {
                message.append("error -> ");
                message.append(showTitle);
                message.append(" is not seen");
                String finalMessage = message.toString();
                arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
            } else {
                if (!user.get().getFavoriteMovies().contains(showTitle)) {
                    user.get().getFavoriteMovies().add(showTitle);
                    message.append("success -> ");
                    message.append(showTitle);
                    message.append(" was added as favourite");
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                } else {
                    message.append("error -> ");
                    message.append(showTitle);
                    message.append(" is already in favourite list");
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "", finalMessage));
                }
            }
        }
    }

    /**
     * for coding style
     */
    private FavoriteCommand() {
    }
}
