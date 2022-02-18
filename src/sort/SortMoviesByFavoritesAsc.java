package sort;

import fileio.MovieInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortMoviesByFavoritesAsc implements Comparator<MovieInputData> {
    /**
     * Method used to compare in ascending order the totalFavorites of two movies
     * Ascending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final MovieInputData a, final MovieInputData b) {
        if (a.getTotalFavorite() != b.getTotalFavorite()) {
            return a.getTotalFavorite() - b.getTotalFavorite();
        } else {
            return a.getTitle().compareTo(b.getTitle());
        }
    }
}
