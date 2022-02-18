package queriesactors;

import fileio.ActorInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortActorsByNameAsc;
import sort.SortActorsByNameDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class used for the query actors by filter description action
 */
public final class QueryActorsDescription {
    /**
     * Method that implements the query for actors by description
     *
     * @param input        represents the date base
     * @param i            represents the index of the action
     * @param id           represents the index written in output file
     * @param arrayResult  represents the Json array written in output file
     * @param fileWriter   for writing in output file
     * @param numberFilter represents the filter index
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void queryActors(final Input input, final int i, final int id,
                                   final JSONArray arrayResult,
                                   final Writer fileWriter, final int numberFilter)
            throws IOException {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        for (ActorInputData actor : input.getActors()) {
            actor.setQueryWords(0);
            for (String word : input.getCommands().get(i).getFilters().
                    get(numberFilter)) {
                word = word.toLowerCase();
                if (Arrays.asList(actor.getCareerDescription().toLowerCase().
                        split("[, ?!:;.@-]+")).contains(word)) {
                    actor.setQueryWords(actor.getQueryWords() + 1);
                }
            }
            if (actor.getQueryWords() != input.getCommands().get(i).getFilters().
                    get(numberFilter).size()) {
                actor.setQueryWords(0);
            }
        }
        ArrayList<ActorInputData> copy = new ArrayList<>(input.getActors());
        if (input.getCommands().get(i).getSortType().equals("asc")) {
            copy.sort(new SortActorsByNameAsc());
        } else {
            copy.sort(new SortActorsByNameDesc());
        }
        int actorNumber = 0;
        long count = input.getActors().stream().filter(a ->
                a.getQueryWords() != 0).count();
        for (ActorInputData actor : copy) {
            if (actor.getQueryWords() != 0) {
                actorNumber++;
                message.append(actor.getName());
                if (actorNumber != count) {
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
    private QueryActorsDescription() {
    }
}
