package queriesserials;

import fileio.Input;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortSerialsByFavoritesAsc;
import sort.SortSerialsByFavoritesDesc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used for the query serials by favorite
 */
public final class QuerySerialsFavorite {
    /**
     * Method that implements the query for serials by favorite
     *
     * @param input       represents the date base
     * @param i           represents the index of the action
     * @param id          represents the index written in output file
     * @param arrayResult represents the Json array written in output file
     * @param fileWriter  for writing in output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void querySerials(final Input input, final int i, final int id,
                                    final JSONArray arrayResult,
                                    final Writer fileWriter) throws IOException {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        int n = input.getCommands().get(i).getNumber();
        for (SerialInputData serial : input.getSerials()) {
            serial.setTotalFavorite(0);
            for (UserInputData user : input.getUsers()) {
                if (user.getFavoriteMovies().contains(serial.getTitle())) {
                    serial.setTotalFavorite(serial.getTotalFavorite() + 1);
                }
            }
            int yearNumber = serial.getYear();
            String year = Integer.toString(yearNumber);
            String genre = input.getCommands().get(i).getFilters().get(1).get(0);
            if (input.getCommands().get(i).getFilters().get(0).contains(null)) {
                if (!(genre == null)) {
                    if (!serial.getGenres().contains(genre)) {
                        serial.setTotalFavorite(0);
                    }
                }
            } else {
                if (!(genre == null)) {
                    if ((!input.getCommands().get(i).getFilters().get(0).
                            contains(year)) || !serial.getGenres().
                            contains(genre)) {
                        serial.setTotalFavorite(0);
                    }
                } else {
                    if (!input.getCommands().get(i).getFilters().get(0).
                            contains(year)) {
                        serial.setTotalFavorite(0);
                    }
                }
            }
        }
        ArrayList<SerialInputData> copy = new ArrayList<>(input.getSerials());
        if (input.getCommands().get(i).getSortType().equals("asc")) {
            copy.sort(new SortSerialsByFavoritesAsc());
        } else {
            copy.sort(new SortSerialsByFavoritesDesc());
        }
        long count = input.getSerials().stream().filter(m ->
                m.getTotalFavorite() != 0).count();
        int serialNumber = 0;
        for (SerialInputData serial : copy) {
            if (serial.getTotalFavorite() != 0) {
                serialNumber++;
                if (serialNumber > n) {
                    break;
                }
                message.append(serial.getTitle());
                if (serialNumber != n && serialNumber != count) {
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
    private QuerySerialsFavorite() {
    }
}
