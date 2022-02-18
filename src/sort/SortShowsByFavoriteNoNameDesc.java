package sort;

import fileio.ShowInput;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortShowsByFavoriteNoNameDesc implements Comparator<ShowInput> {
    /**
     * Method used to compare in descending order the totalFavorites vies of two shows
     * No alphabetical ordering
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final ShowInput a, final ShowInput b) {
        return Double.compare(b.getTotalFavorite(), a.getTotalFavorite());
    }
}
