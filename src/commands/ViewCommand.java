package commands;

import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.Optional;

/**
 * Class used for the view command given by a user
 */
public final class ViewCommand {
    /**
     * Method that implements the view command given by a user
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
                user.get().getHistory().put(showTitle, 1);
            } else {
                user.get().getHistory().put(showTitle,
                        user.get().getHistory().get(showTitle) + 1);
            }
            message.append("success -> ");
            message.append(showTitle);
            message.append(" was viewed with total views of ");
            message.append(user.get().getHistory().get(showTitle));
            String finalMessage = message.toString();
            arrayResult.add(fileWriter.writeFile(id, "",
                    finalMessage));
        }
    }

    /**
     * for coding style
     */
    private ViewCommand() {
    }
}
