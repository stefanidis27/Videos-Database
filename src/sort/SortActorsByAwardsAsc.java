package sort;

import fileio.ActorInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortActorsByAwardsAsc implements Comparator<ActorInputData> {
    /**
     * Method used to compare in ascending order the number of awards for two actors
     * Ascending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final ActorInputData a, final ActorInputData b) {
        if (a.getQueryAwards() != b.getQueryAwards()) {
            return a.getQueryAwards() - b.getQueryAwards();
        } else {
            return a.getName().compareTo(b.getName());
        }
    }
}
