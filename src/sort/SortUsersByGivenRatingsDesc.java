package sort;

import fileio.UserInputData;

import java.util.Comparator;

/**
 * Class used to implement comparator
 */
public final class SortUsersByGivenRatingsDesc implements Comparator<UserInputData> {
    /**
     * Method used to compare in descending order the total given ratings of two users
     * Descending order alphabetically, also
     *
     * @param a first element to be compared
     * @param b second element to be compared
     * @return the comparison result
     */
    public int compare(final UserInputData a, final UserInputData b) {
        if (a.getGivenRatings() != b.getGivenRatings()) {
            return b.getGivenRatings() - a.getGivenRatings();
        } else {
            return b.getUsername().compareTo(a.getUsername());
        }
    }
}
