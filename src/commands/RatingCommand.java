package commands;

import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.Optional;

/**
 * Class used for the rating command given by a user
 */
public final class RatingCommand {
    /**
     * Method that implements the rating command given by a user
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
        String title = input.getCommands().get(i).getTitle();
        String username = input.getCommands().get(i).getUsername();
        StringBuilder message = new StringBuilder();
        double grade = input.getCommands().get(i).getGrade();
        int seasonNumber = input.getCommands().get(i).getSeasonNumber();
        if (seasonNumber != 0) {
            Optional<SerialInputData> serial =
                    input.getSerials().stream().filter(s ->
                            s.getTitle().equals(title)).findFirst();
            if (serial.isPresent() && serial.get().getSeasons().get(seasonNumber - 1).
                    getUsernames().contains(username)) {
                message.append("error -> ");
                message.append(title);
                message.append(" has been already rated");
                String finalMessage = message.toString();
                arrayResult.add(fileWriter.writeFile(id, "",
                        finalMessage));
            } else {
                Optional<UserInputData> user =
                        input.getUsers().stream().filter(u ->
                                u.getUsername().equals(username)).findFirst();
                if (user.isPresent() && !user.get().getHistory().containsKey(title)) {
                    message.append("error -> ");
                    message.append(title);
                    message.append(" is not seen");
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "",
                            finalMessage));
                } else if (user.isPresent() && serial.isPresent()) {
                    user.get().setGivenRatings(user.get().getGivenRatings() + 1);
                    serial.get().getSeasons().get(seasonNumber - 1).getUsernames().
                            add(username);
                    serial.get().getSeasons().get(seasonNumber - 1).
                            getRatings().add(grade);
                    message.append("success -> ");
                    message.append(title);
                    message.append(" was rated with ");
                    message.append(grade);
                    message.append(" by ");
                    message.append(username);
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "",
                            finalMessage));
                }
            }
        } else {
            Optional<MovieInputData> movie =
                    input.getMovies().stream().filter(m ->
                            m.getTitle().equals(title)).findFirst();
            if (movie.isPresent() && movie.get().getUsernames().contains(username)) {
                message.append("error -> ");
                message.append(title);
                message.append(" has been already rated");
                String finalMessage = message.toString();
                arrayResult.add(fileWriter.writeFile(id, "",
                        finalMessage));
            } else {
                Optional<UserInputData> user =
                        input.getUsers().stream().filter(u ->
                                u.getUsername().equals(username)).findFirst();
                if (user.isPresent() && !user.get().getHistory().containsKey(title)) {
                    message.append("error -> ");
                    message.append(title);
                    message.append(" is not seen");
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "",
                            finalMessage));
                } else if (user.isPresent() && movie.isPresent()) {
                    user.get().setGivenRatings(user.get().getGivenRatings() + 1);
                    movie.get().getUsernames().add(username);
                    movie.get().getRatings().add(grade);
                    message.append("success -> ");
                    message.append(title);
                    message.append(" was rated with ");
                    message.append(grade);
                    message.append(" by ");
                    message.append(username);
                    String finalMessage = message.toString();
                    arrayResult.add(fileWriter.writeFile(id, "",
                            finalMessage));
                }
            }
        }
    }

    /**
     * for coding style
     */
    private RatingCommand() {
    }
}
