package sort;

import fileio.MovieInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortMoviesByViewsAsc implements Comparator<MovieInputData> {
    /**
     * Method used to compare in ascending order the total views of two movies
     * Ascending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final MovieInputData a, final MovieInputData b) {
        if (a.getTotalViews() != b.getTotalViews()) {
            return a.getTotalViews() - b.getTotalViews();
        } else {
            return a.getTitle().compareTo(b.getTitle());
        }
    }
}
