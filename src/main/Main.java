package main;

import checker.Checkstyle;
import checker.Checker;
import commands.FavoriteCommand;
import commands.RatingCommand;
import commands.ViewCommand;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import queriesactors.QueryActorsAverage;
import queriesactors.QueryActorsAwards;
import queriesactors.QueryActorsDescription;
import queriesmovies.QueryMoviesDuration;
import queriesmovies.QueryMoviesFavorite;
import queriesmovies.QueryMoviesRating;
import queriesmovies.QueryMoviesViews;
import queriesserials.QuerySerialsDuration;
import queriesserials.QuerySerialsFavorite;
import queriesserials.QuerySerialsRating;
import queriesserials.QuerySerialsViews;
import queriesusers.QueryUsers;
import recommendationsall.RecommendBestUnseen;
import recommendationsall.RecommendStandard;
import recommendationspremium.RecommendFavorite;
import recommendationspremium.RecommendPopular;
import recommendationspremium.RecommendSearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();
        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();
        int id = 0;
        for (int i = 0; i < input.getCommands().size(); i++) {
            switch (input.getCommands().get(i).getActionType()) {
                case "command":
                    switch (input.getCommands().get(i).getType()) {
                        case "favorite" -> {
                            id++;
                            FavoriteCommand.command(input, i, id, arrayResult, fileWriter);
                        }
                        case "view" -> {
                            id++;
                            ViewCommand.command(input, i, id, arrayResult, fileWriter);
                        }
                        case "rating" -> {
                            id++;
                            RatingCommand.command(input, i, id, arrayResult, fileWriter);
                        }
                        default -> {
                        }
                    }
                    break;
                case "query":
                    switch (input.getCommands().get(i).getObjectType()) {
                        case "actors":
                            int numberFilter = input.getCommands().get(i).getFilters().size();
                            switch (input.getCommands().get(i).getCriteria()) {
                                case "average" -> {
                                    id++;
                                    QueryActorsAverage.queryActors(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                case "awards" -> {
                                    numberFilter -= 1;
                                    id++;
                                    QueryActorsAwards.queryActors(input, i, id, arrayResult,
                                            fileWriter, numberFilter);
                                }
                                case "filter_description" -> {
                                    numberFilter -= 2;
                                    id++;
                                    QueryActorsDescription.queryActors(input, i, id, arrayResult,
                                            fileWriter, numberFilter);
                                }
                                default -> {
                                }
                            }
                            break;
                        case "users":
                            id++;
                            QueryUsers.queryUsers(input, i, id, arrayResult, fileWriter);
                            break;
                        case "movies":
                            switch (input.getCommands().get(i).getCriteria()) {
                                case "ratings" -> {
                                    id++;
                                    QueryMoviesRating.queryMovies(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                case "favorite" -> {
                                    id++;
                                    QueryMoviesFavorite.queryMovies(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                case "longest" -> {
                                    id++;
                                    QueryMoviesDuration.queryMovies(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                case "most_viewed" -> {
                                    id++;
                                    QueryMoviesViews.queryMovies(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                default -> {
                                }
                            }
                            break;
                        case "shows":
                            switch (input.getCommands().get(i).getCriteria()) {
                                case "ratings" -> {
                                    id++;
                                    QuerySerialsRating.querySerials(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                case "favorite" -> {
                                    id++;
                                    QuerySerialsFavorite.querySerials(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                case "longest" -> {
                                    id++;
                                    QuerySerialsDuration.querySerials(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                case "most_viewed" -> {
                                    id++;
                                    QuerySerialsViews.querySerials(input, i, id, arrayResult,
                                            fileWriter);
                                }
                                default -> {
                                }
                            }
                            break;
                        default:
                    }
                    break;
                case "recommendation":
                    switch (input.getCommands().get(i).getType()) {
                        case "standard" -> {
                            id++;
                            RecommendStandard.recommendAll(input, i, id, arrayResult,
                                    fileWriter);
                        }
                        case "best_unseen" -> {
                            id++;
                            RecommendBestUnseen.recommendAll(input, i, id, arrayResult,
                                    fileWriter);
                        }
                        case "popular" -> {
                            id++;
                            RecommendPopular.recommendPremium(input, i, id, arrayResult,
                                    fileWriter);
                        }
                        case "favorite" -> {
                            id++;
                            RecommendFavorite.recommendPremium(input, i, id, arrayResult,
                                    fileWriter);
                        }
                        case "search" -> {
                            id++;
                            RecommendSearch.recommendPremium(input, i, id, arrayResult,
                                    fileWriter);
                        }
                        default -> {
                        }
                    }
                    break;
                default:
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
