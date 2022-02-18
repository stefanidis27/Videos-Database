package sort;

import fileio.UserInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortUsersByGivenRatingsAsc implements Comparator<UserInputData> {
    /**
     * Method used to compare in ascending order the total given ratings of two users
     * Ascending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final UserInputData a, final UserInputData b) {
        if (a.getGivenRatings() != b.getGivenRatings()) {
            return a.getGivenRatings() - b.getGivenRatings();
        } else {
            return a.getUsername().compareTo(b.getUsername());
        }
    }
}
