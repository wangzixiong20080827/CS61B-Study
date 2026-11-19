package hashmap;

import java.util.Set;
//你的实现需要实现这个接口
/* Your implementation should implement this interface. To do so,
 * append "implements Map61B<K, V>" to the end of your "public class..."//追加"implements Map61B<K,V>到你的"public class"后面
 * declaration, though you can and should use other type parameters when
 * necessary.//声明，尽管你可以且应该使用其他类型参数在必要的时候。
 */
public interface Map61B<K, V> extends Iterable<K> {
//必须实现Iterable<K><----------------------------->这是一个整体；
    /** Associates the specified value with the specified key in this map.
     *  If the map already contains the specified key, replaces the key's mapping
     *  with the value specified. */
    //结合特定的值，和特定的钥匙，在这个图里面。
    //如果图已经含有了特定的钥匙，替代钥匙的映射（值）
    void put(K key, V value);//1.
//put方法，K key和V value两个参数，无返回值。
    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. */
    //返回指定的key 所映射的value,如果这个map里没有这个key的映射，则返回null;
    V get(K key);//2.

    /** Returns whether this map contains a mapping for the specified key. */
    //返回是否这张图含有这个指定的key对应的映射。
    boolean containsKey(K key);//3.

    /** Returns the number of key-value mappings in this map. */
    //返回这个map中键值对映射的数量，即返回map里存了多少数据。
    int size();//4.

    /** Removes every mapping from this map. */
    //将所有映射从map里移除
    void clear();//5.

    /** Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException. */
    //返回这个map里所有key的set视图,这里返回的是一个集合对象，
    Set<K> keySet();//6.

    /** Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    //将映射对特定的钥匙从map里移除，如果存在的话，
    //或者返回null如果没有对应的映射
    //对本次实验并不是要求的，如果你不能实现，抛出异常即可。
    V remove(K key);//7.
}
