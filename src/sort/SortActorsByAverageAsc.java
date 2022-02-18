package sort;

import fileio.ActorInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortActorsByAverageAsc implements Comparator<ActorInputData> {
    /**
     * Method used to compare in ascending order the average of two actors
     * Ascending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final ActorInputData a, final ActorInputData b) {
        if (a.getAverage() != b.getAverage()) {
            return Double.compare(a.getAverage(), b.getAverage());
        } else {
            return a.getName().compareTo(b.getName());
        }
    }
}
