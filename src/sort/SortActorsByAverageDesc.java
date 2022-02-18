package sort;

import fileio.ActorInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortActorsByAverageDesc implements Comparator<ActorInputData> {
    /**
     * Method used to compare in descending order the average of two actors
     * Descending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final ActorInputData a, final ActorInputData b) {
        if (a.getAverage() != b.getAverage()) {
            return Double.compare(b.getAverage(), a.getAverage());
        } else {
            return b.getName().compareTo(a.getName());
        }
    }
}
