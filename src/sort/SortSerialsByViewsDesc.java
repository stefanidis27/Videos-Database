package sort;

import fileio.SerialInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortSerialsByViewsDesc implements Comparator<SerialInputData> {
    /**
     * Method used to compare in descending order the total views of two serials
     * Descending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final SerialInputData a, final SerialInputData b) {
        if (a.getTotalViews() != b.getTotalViews()) {
            return b.getTotalViews() - a.getTotalViews();
        } else {
            return b.getTitle().compareTo(a.getTitle());
        }
    }
}
