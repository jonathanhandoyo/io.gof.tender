package io.gof.tender.util;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomMap {
    public static <K, V> Pair[] unwind(Map<K, V> map) {
        if (map != null && !map.isEmpty()) {
            List<Pair<K, V>> pairs = new ArrayList<>();
            for (K key: map.keySet()) {
                pairs.add(Pair.of(key, map.get(key)));
            }

            return pairs.toArray(new Pair[pairs.size()]);
        }
        return null;
    }

    public static <K, V> Map<K, V> map(Pair<K, V>... pairs) {
        Map<K, V> result = new HashMap<K, V>();
        for (Pair<K, V> pair: pairs) {
            if (pair.getKey() != null && pair.getValue()!= null) {
                result.put(pair.getKey(), pair.getValue());
            }
        }
        return result;
    }
}
