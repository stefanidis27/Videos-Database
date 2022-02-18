package sort;

import fileio.ShowInput;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortShowsByRatingAsc implements Comparator<ShowInput> {
    /**
     * Method used to compare in ascending order the total rating of two shows
     * ascending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final ShowInput a, final ShowInput b) {
        if (a.getTotalRating() != b.getTotalRating()) {
            return Double.compare(a.getTotalRating(), b.getTotalRating());
        } else {
            return a.getTitle().compareTo(b.getTitle());
        }
    }
}
