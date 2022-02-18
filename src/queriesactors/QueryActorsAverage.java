package queriesactors;

import entertainment.Season;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import sort.SortActorsByAverageAsc;
import sort.SortActorsByAverageDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class used for the query actors by average action
 */
public final class QueryActorsAverage {
    /**
     * Method that implements the query for actors by average
     *
     * @param input       represents the date base
     * @param i           represents the index of the action
     * @param id          represents the index written in output file
     * @param arrayResult represents the Json array written in output file
     * @param fileWriter  for writing in output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void queryActors(final Input input, final int i, final int id,
                                   final JSONArray arrayResult,
                                   final Writer fileWriter) throws IOException {
        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        int n = input.getCommands().get(i).getNumber();
        for (ActorInputData actor : input.getActors()) {
            actor.setAverage(0.0);
            double numberOfRatedShows = 0.0;
            double totalRatings = 0.0;
            for (String showName : actor.getFilmography()) {
                Optional<MovieInputData> movie = input.getMovies().stream().
                        filter(m -> m.getTitle().equals(showName)).findFirst();
                Optional<SerialInputData> serial = input.getSerials().stream().
                        filter(s -> s.getTitle().equals(showName)).findFirst();
                if (movie.isPresent() && !movie.get().getRatings().isEmpty()) {
                    totalRatings += movie.get().getRatings().stream().
                            mapToDouble(d -> d).sum()
                            / (double) movie.get().getRatings().size();
                    numberOfRatedShows++;
                } else if (serial.isPresent()) {
                    double totalRatingsSerial = 0.0;
                    for (Season season : serial.get().getSeasons()) {
                        if (!season.getRatings().isEmpty()) {
                            totalRatingsSerial += season.getRatings().stream().
                                    mapToDouble(d -> d).sum()
                                    / (double) season.getRatings().size();
                        }
                    }
                    totalRatings += totalRatingsSerial / (double) serial.get().
                            getNumberSeason();
                    if (totalRatings != 0) {
                        numberOfRatedShows++;
                    }
                }
            }
            if (numberOfRatedShows != 0.0) {
                actor.setAverage(totalRatings / numberOfRatedShows);
            }
        }
        List<ActorInputData> copy = new ArrayList<>(input.getActors());
        if (input.getCommands().get(i).getSortType().equals("asc")) {
            copy.sort(new SortActorsByAverageAsc());
        } else {
            copy.sort(new SortActorsByAverageDesc());
        }
        int actorNumber = 0;
        long count = input.getActors().stream().filter(a ->
                a.getAverage() != 0.0).count();
        for (ActorInputData actor : copy) {
            if (actor.getAverage() != 0.0) {
                actorNumber++;
                if (actorNumber > n) {
                    break;
                }
                message.append(actor.getName());
                if (actorNumber != n && actorNumber != count) {
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
    private QueryActorsAverage() {
    }
}
