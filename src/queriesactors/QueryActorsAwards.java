package queriesactors;

import actor.ActorsAwards;
import fileio.ActorInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortActorsByAwardsAsc;
import sort.SortActorsByAwardsDesc;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used for the query actors by awards action
 */
public final class QueryActorsAwards {
    /**
     * Method that implements the query for actors by awards
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
            actor.setQueryAwards(0);
            int awardsTaken = 0;
            for (String award : input.getCommands().get(i).getFilters().
                    get(numberFilter)) {
                ActorsAwards awardName = Utils.stringToAwards(award);
                if (actor.getAwards().containsKey(awardName)) {
                    awardsTaken++;
                }
            }
            actor.setQueryAwards(actor.getAwards().values().stream().
                    mapToInt(j -> j).sum());
            if (awardsTaken != input.getCommands().get(i).getFilters().
                    get(numberFilter).size()) {
                actor.setQueryAwards(0);
            }
        }
        ArrayList<ActorInputData> copy = new ArrayList<>(input.getActors());
        if (input.getCommands().get(i).getSortType().equals("asc")) {
            copy.sort(new SortActorsByAwardsAsc());
        } else {
            copy.sort(new SortActorsByAwardsDesc());
        }
        int actorNumber = 0;
        long count = input.getActors().stream().filter(a ->
                a.getQueryAwards() != 0).count();
        for (ActorInputData actor : copy) {
            if (actor.getQueryAwards() != 0) {
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
    private QueryActorsAwards() {
    }
}
