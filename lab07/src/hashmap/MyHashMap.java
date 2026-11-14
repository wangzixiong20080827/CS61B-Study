package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    /** The backing array of buckets. There must be exactly one field of type
     *  Collection[], because the tests find it by reflection. */
    private Collection<Node>[] buckets;
    /** Number of key/value pairs currently in the map. */
    private int size;
    /** Maximum allowed (#items / #buckets) before the array grows. */
    private final double loadFactor;

    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    /** The array doubles when it grows, so resizing stays O(1) amortized. */
    private static final int GROWTH_FACTOR = 2;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, double loadFactor) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("initialCapacity must be at least 1");
        }
        if (loadFactor <= 0) {
            throw new IllegalArgumentException("loadFactor must be positive");
        }
        this.buckets = (Collection<Node>[]) new Collection[initialCapacity];
        this.loadFactor = loadFactor;
        this.size = 0;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // LinkedList supports add / remove / iterator, and keeps insertion
        // order inside a bucket, which the collision tests rely on.
        return new LinkedList<>();
    }

    /** Returns the index in buckets where key belongs.
     *  floorMod keeps the result non-negative even for negative hash codes
     *  (e.g. Integer.MIN_VALUE), which a plain % would not. */
    private int bucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    /** Returns the bucket for key, creating an empty bucket on first use. */
    private Collection<Node> bucketFor(K key) {
        int i = bucketIndex(key);
        if (buckets[i] == null) {
            buckets[i] = createBucket();
        }
        return buckets[i];
    }

    /** Returns the node in key's bucket whose key equals key, or null if absent. */
    private Node findNode(K key) {
        for (Node node : bucketFor(key)) {
            if (key.equals(node.key)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        Node node = findNode(key);
        if (node != null) {
            // Key already present: replace the value, don't add a second pair.
            node.value = value;
            return;
        }
        bucketFor(key).add(new Node(key, value));
        size += 1;
        // The load factor must always stay <= loadFactor, so grow as soon as
        // this insertion pushes #items / #buckets over the limit.
        if (size > loadFactor * buckets.length) {
            resize(buckets.length * GROWTH_FACTOR);
        }
    }

    @Override
    public V get(K key) {
        Node node = findNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean containsKey(K key) {
        // Must not be written as get(key) != null: a key can map to null.
        return findNode(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        // Keep the current capacity, just drop all the pairs.
        buckets = (Collection<Node>[]) new Collection[buckets.length];
        size = 0;
    }

    /** Adds node to the bucket its key hashes to, without touching size.
     *  Used by resize(), which moves every existing pair. */
    private void insert(Node node) {
        int i = Math.floorMod(node.key.hashCode(), buckets.length);
        if (buckets[i] == null) {
            buckets[i] = createBucket();
        }
        buckets[i].add(node);
    }

    /** Grows the backing array to newCapacity and rehashes every pair.
     *  Every index changes, so no pair can be copied over in place. */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Collection<Node>[] oldBuckets = buckets;
        buckets = (Collection<Node>[]) new Collection[newCapacity];
        for (Collection<Node> bucket : oldBuckets) {
            if (bucket == null) {
                continue;
            }
            for (Node node : bucket) {
                insert(node);
            }
        }
    }

    @Override
    public V remove(K key) {
        int i = bucketIndex(key);
        Collection<Node> bucket = buckets[i];
        if (bucket == null) {
            return null;
        }
        Iterator<Node> it = bucket.iterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (key.equals(node.key)) {
                // Removing through the iterator is safe for every Collection
                // type used as a bucket (iterator().remove() is supported).
                it.remove();
                size -= 1;
                return node.value;
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (K key : this) {
            keys.add(key);
        }
        return keys;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    /** Iterates over every key in the map, bucket by bucket. */
    private class KeyIterator implements Iterator<K> {
        /** Index of the next bucket to look at. */
        private int nextBucket;
        /** Iterator over the current bucket, or null when it's exhausted. */
        private Iterator<Node> nodes;

        @Override
        public boolean hasNext() {
            while ((nodes == null || !nodes.hasNext()) && nextBucket < buckets.length) {
                Collection<Node> bucket = buckets[nextBucket++];
                nodes = bucket == null ? null : bucket.iterator();
            }
            return nodes != null && nodes.hasNext();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return nodes.next().key;
        }
    }
}
