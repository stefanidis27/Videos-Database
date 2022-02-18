package sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class used to sort a hashmap after values
 */
public final class SortHashMap {
    /**
     * Method that returns a new sorted Map from the initial Map
     *
     * @param unsortedMap the initial map
     * @return the new sorted map
     */
    public static Map<String, Integer> sortByValue(final Map<String, Integer> unsortedMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(final Map.Entry<String, Integer> g1,
                               final Map.Entry<String, Integer> g2) {
                return (g2.getValue()).compareTo(g1.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /**
     * for coding style
     */
    private SortHashMap() {
    }
}
