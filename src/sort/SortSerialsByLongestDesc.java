package sort;

import fileio.SerialInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortSerialsByLongestDesc implements Comparator<SerialInputData> {
    /**
     * Method used to compare in ascending order the total duration of two serials
     * Ascending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final SerialInputData a, final SerialInputData b) {
        if (a.getTotalDuration() != b.getTotalDuration()) {
            return b.getTotalDuration() - a.getTotalDuration();
        } else {
            return b.getTitle().compareTo(a.getTitle());
        }
    }
}
