package sort;

import fileio.MovieInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortMoviesByViewsDesc implements Comparator<MovieInputData> {
    /**
     * Method used to compare in descending order the total views of two movies
     * Descending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final MovieInputData a, final MovieInputData b) {
        if (a.getTotalViews() != b.getTotalViews()) {
            return b.getTotalViews() - a.getTotalViews();
        } else {
            return b.getTitle().compareTo(a.getTitle());
        }
    }
}
