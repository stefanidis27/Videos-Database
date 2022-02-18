package sort;

import fileio.SerialInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortSerialsByFavoritesAsc implements Comparator<SerialInputData> {
    /**
     * Method used to compare in ascending order the totalFavorites of two serials
     * Ascending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final SerialInputData a, final SerialInputData b) {
        if (a.getTotalFavorite() != b.getTotalFavorite()) {
            return a.getTotalFavorite() - b.getTotalFavorite();
        } else {
            return a.getTitle().compareTo(b.getTitle());
        }
    }
}
