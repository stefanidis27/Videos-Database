package queriesusers;

import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortUsersByGivenRatingsAsc;
import sort.SortUsersByGivenRatingsDesc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used for the query users action
 */
public final class QueryUsers {
    /**
     * Method that implements the query for users
     *
     * @param input       represents the date base
     * @param i           represents the index of the action
     * @param id          represents the index written in output file
     * @param arrayResult represents the Json array written in output file
     * @param fileWriter  for writing in output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void queryUsers(final Input input, final int i, final int id,
                                  final JSONArray arrayResult,
                                  final Writer fileWriter) throws IOException {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        int n = input.getCommands().get(i).getNumber();
        ArrayList<UserInputData> copy = new ArrayList<>(input.getUsers());
        if (input.getCommands().get(i).getSortType().equals("asc")) {
            copy.sort(new SortUsersByGivenRatingsAsc());
        } else {
            copy.sort(new SortUsersByGivenRatingsDesc());
        }
        long count = input.getUsers().stream().filter(u -> u.getGivenRatings() != 0)
                .count();
        int userNumber = 0;
        for (UserInputData user : copy) {
            if (user.getGivenRatings() != 0) {
                userNumber++;
                if (userNumber > n) {
                    break;
                }
                message.append(user.getUsername());
                if (userNumber != n && userNumber != count) {
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
    private QueryUsers() {
    }
}
