package task1.customeThreadSafeMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomThreadSafeMap<K, V> {

    private final Map<K, V> map;
    private final Object lock;

    public CustomThreadSafeMap(Map<K, V> map) {
        this.map = map;
        lock = this;
    }

    public V put(K key, V value) {
        synchronized (lock) {
            return map.put(key, value);
        }
    }

    public V get(K key) {
        synchronized (lock) {
            return map.get(key);
        }
    }

    public Collection<V> values() {
        synchronized (lock) {
            return new ArrayList<>(this.map.values());
        }
    }

    public int size() {
        return this.map.size();
    }

}
