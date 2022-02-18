package sort;

import fileio.ActorInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortActorsByNameDesc implements Comparator<ActorInputData> {
    /**
     * Method used to compare in descending order the name of two actors
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final ActorInputData a, final ActorInputData b) {
        return b.getName().compareTo(a.getName());
    }
}
