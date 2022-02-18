package sort;

import fileio.MovieInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortMoviesByFavoritesDesc implements Comparator<MovieInputData> {
    /**
     * Method used to compare in descending order the totalFavorites of two movies
     * Descending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final MovieInputData a, final MovieInputData b) {
        if (a.getTotalFavorite() != b.getTotalFavorite()) {
            return b.getTotalFavorite() - a.getTotalFavorite();
        } else {
            return b.getTitle().compareTo(a.getTitle());
        }
    }
}
