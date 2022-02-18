package sort;

import fileio.ActorInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortActorsByNameAsc implements Comparator<ActorInputData> {
    /**
     * Method used to compare in ascending order the name of two actors
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final ActorInputData a, final ActorInputData b) {
        return a.getName().compareTo(b.getName());
    }
}
