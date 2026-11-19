package hashmap;
//package:包，在java利用来给一组归类，并避免类名冲突，hashmap是默认包名；有package,中大型项目必须，可以import,没有package,小型项目，不能被有包名的类import.
//import 作用范围：整个文件。
import java.util.Collection;//Collection是java.util包里的接口，能让这个文件的任何地方写Collection,不用写全名：java.util.Collection
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.//基于哈希表实现的map
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().//假定不会插入null作为键，并且删除元素后不会缩小容量。
 *
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs//用于存储键值对的受保护类
     * The protected qualifier allows subclass access//proteted 修饰府允许子类访问，protected是同一包内可见，或仅对子类可见。
     */
    //protected class Node{
    //K key;
    //V value;
    //Node(K k,V v){
    //this.key=k;
    //this.value=v;
    //}
//}
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {//构造器，
            key = k;//参数叫k,字段叫key,不重名，可以直接赋值。
            value = v;//参数叫v,字段叫value,不重名，可以直接赋值。
        }
    }

    /* Instance Variables *///实例变量，定义在类中，每个对象个有一份的字段。静态变量:整个类只有一份。
    /**
     * The backing array of buckets. There must be exactly one field of type////放置桶的底层数组，
     * Collection[], because the tests find it by reflection.
     *///有且只有一个，且不能为零或有多个。
    private Collection<Node>[] buckets;//import java.util.Collection
    //没有import:private java.util.Collection<Node>[] buckets;测试要用到，必须。
    /**
     * Number of key/value pairs currently in the map.
     *///现在键值对在map里面的数量。
    private int size;
    /**
     * Maximum allowed (#items / #buckets) before the array grows.
     *///允许的最大值在数组增长过程中。
    private final double loadFactor;
    //final表示最终，意思是一旦赋值后就不能再更改。
    private static final int DEFAULT_CAPACITY = 16;//桶总数
    private static final double DEFAULT_LOAD_FACTOR = 0.75;//负载因子
    /**
     * The array doubles when it grows, so resizing stays O(1) amortized.
     *///数组在他增长过程中翻倍,因此扩容均摊下来是o(1);
    private static final int GROWTH_FACTOR = 2;//增长因子，扩容时数组翻的倍数
//public MyHashMap(){
    //this(16,0.75);
//}
    //public MyHashMap(int initialCapacity){
    //this(initialCapacity,DEFAULT_LOAD_FACTOR);
//}
    //public MyHashMap(int initialCapacity,double loadFactor){
    //if(initialCapacity<1)
//throw new IllegalArgumentException("initialCapacity must >1")
    //if(loadFactor<0)
    //throw new IllegalArgumentException("loadFactor must >0“);
    //this.buckets=(Collection<Node>[])new Collection[initialCapacity];
    //this.loadFactor=loadFactor;
    //this.size=0;

    /**
     * Constructors
     *///构造器，在new 创建对象时自动调用的特殊方法，用来初始化对象的字段。
    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);//相当于：this(16,0.75);
    }
    //new MyHashMap()->MyHashMap调用this(16,0.75);->MyHashMap(int double)执行下真正的初始化；

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);//this的作用是调用本类的另一个构造器
    }
//构造器：和类名完全一样，方法名：随便起，构造器返回值无，且不用写void;普通方法必须int void V;
    //构造器new 创建对象时自动调用，属于初始化；

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.//这个MyHashMap构造器会创建一个长度为initialCapacity的底层数组，
     * The load factor (# items / # buckets) should always be <= loadFactor//负载因子（元素数/桶数）<=loadFactor
     *
     * @param initialCapacity initial size of backing array//（parameter):参数；
     * @param loadFactor      maximum load factor
     */
    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, double loadFactor) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("initialCapacity must be at least 1");//异常处理；
        }
        if (loadFactor <= 0) {
            throw new IllegalArgumentException("loadFactor must be positive");
        }
        this.buckets = (Collection<Node>[]) new Collection[initialCapacity];//this.字段名=值；泛型数组需要强制类型转换；
        // <-强制类型转换    ->   <-创建原始数组                 ->
        this.loadFactor = loadFactor;//两边字段和参数名字相同，需要加this加以区分；
        this.size = 0;
    }
    //字段出现的意义：变量（局部变量或参数）会随方法的结束而消失，字段使数据能跨方法保存；
    //public MyHashMa(int initialCapacity,double loadFactor){
    //if(.....)
    //this.buckets=(Collection<Node[]>new Collection[initialCapacity];
    //this.loadFactor=loadFactor;
    //this.size=0;
//java不允许写new Collection<Node>[n],只能先创建原始类型数组再进行强制类型转换

    /**
     * Returns a data structure to be a hash table bucket//返回一个数据结构作为哈希表的桶，
     * <p>
     * The only requirements of a hash table bucket are that we can://我们要求哈希桶只需要实现以下功能:
     * 1. Insert items (`add` method)//存入物品；（add 方法）
     * 2. Remove items (`remove` method)//移除物品；（remove 方法）
     * 3. Iterate through items (`iterator` method)//遍历所有元素（iterator方法)
     * Note that that this is referring to the hash table bucket itself,//注意，这里指的是哈希表的桶本身
     * not the hash map itself.//与哈希表本身无关；
     * <p>
     * Each of these methods is supported by java.util.Collection,//这三个发给发不是要你全部实现，而是Collection统一规定好的
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.//java里大多数数据结构都继承自Collection,所以我们几乎可以用任何数据来当桶；；
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type//重写这个方法，就能换用不同的数据结构来当作底层桶的类型
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!//务必调用这个工厂方法，不要自己new创建桶的数据结构；
     */
    //protected Collection<Node> createBucket(){
    //
    //protected Collection<Node> creatBucket(){
    //return new LinkedList<>();}
    //protected int bucketIndex(K key){
    //return Math.floorMod(key.hashCode(),buckets.length);//直接写bucket.length,表示求数组长度；
    //}

    //桶可以是任何Collection,想换桶就重写createBucket();所以代码里所有造桶的方法，不能new,直接creatBucket();
    //写成protected；子类能重写；public 子类也能重写；
    protected Collection<Node> createBucket() {
        // LinkedList supports add / remove / iterator, and keeps insertion////LinkedList支持add,remove,iterator,iterator
        //并且会保持桶内元素的插入顺序，碰撞测试正是依赖这一点
        // order inside a bucket, which the collision tests rely on.
        return new LinkedList<>();//protected:子类可重写，Collection<Node>返回类型：一个能装Node的集合了；
    }//子类才能@Override;//new LinkList<>():LinkList满足Collection<Node>的要求，所以能返回；新建一个空的列表，作为桶交出去。
    //Linklist支持add,remove,iterator方法，并且能保证桶的插入顺序，而冲突测试正是依赖这一点；需要满足造出来的桶和Hashmap插入顺序完全一致

    /**
     * Returns the index in buckets where key belongs.
     * floorMod keeps the result non-negative even for negative hash codes
     * (e.g. Integer.MIN_VALUE), which a plain % would not.
     *///返回key在buckets应该存放的下标，floorMod能保证结果非负，即使哈希码是负数，而普通的%做不到这一点
    private int bucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);//Math.floorMod的作用是取模且保证结果非负，需要对哈希值和桶数进行取模得到插入下标；
    }
//private Collection<Node>bucketFor(K key){
    //int i=bucketIndex(key);
    //if(buckets[i]==null)
    //return  createBucket();
    //return buckets[i];
//}
    //private Node findNode(K key){
    //for(Node node:bucketFor[key]){
    //if(key.equals(node.key)
    //return node;}
    //return null;
    //}
    /** Returns the bucket for key, creating an empty bucket on first use. *///返回key对应的桶，如果还没有桶，就第一次用到时创建一个桶
    private Collection<Node> bucketFor(K key) {
        int i = bucketIndex(key);
        if (buckets[i] == null) {
            buckets[i] = createBucket();
        }
        return buckets[i];
    }
//private Node findNode(K key){//Node:一个节点，存着：key(K类型），value(V类型);调用它的地方要用到节点本身，而不是只要key或者value;
//node:变量名，随便写；Node不能省略；
    //for(Node node:bucketsFor(key)):for遍历：拿出key所在的节点,节点名字叫node;
    /** Returns the node in key's bucket whose key equals key, or null if absent. *///返回key所在桶中和key相等的那一个节点；如果不存在，就返回null;
    private Node findNode(K key) {
        for (Node node : bucketFor(key)) {
            if (key.equals(node.key)) {//..s1.equals(s2):返回值有true,false两种类型，if(括号里要求是boolean);
                return node;
            }
        }
        return null;
    }
//重写方法：
    //@Override
    //public void put(K key,V value){
    //Node node=findNode(K key);
    //if(node!=null){node.value=value;return;}
    //bucketFor(key).add(new Node (key,value));//把右边创建的东西加到左边；
    //size+=1;
    //if(size>loadFactor*bucket.length)
    //resize(backet.length,GROW_FACTOR);执行增长因子；
    @Override
    public void put(K key, V value) {
        Node node = findNode(key);
        if (node != null) {
            // Key already present: replace the value, don't add a second pair.//key已经存在了，只替换value,不要再添加一对新的键值对
            //put 在遇到已存在的节点时，size不加，只是替换原有的值；
            node.value = value;
            return;
        }
        bucketFor(key).add(new Node(key, value));//往桶里添加新元素；
        size += 1;
        // The load factor must always stay <= loadFactor, so grow as soon as
        // this insertion pushes #items / #buckets over the limit.//负载因子必须始终<=loadFactor,所以这次插入一旦让元素数/桶数超过上限就立即扩容；
        if (size > loadFactor * buckets.length) {
            resize(buckets.length * GROWTH_FACTOR);//java能先调用后具体写方法，和c不同；resize方法后面具体写；
        }
    }
//public V get(key){
    //Node node=findNode(key);
    //if(node==null)return null;
    //else return node.value;
    @Override
    public V get(K key) {
        Node node = findNode(key);
        return node == null ? null : node.value;
    }
//public boolean containsKey(K key){
    //if(findNode(key)!=null)return true;
    //else return false;

    @Override
    public boolean containsKey(K key) {
        // Must not be written as get(key) != null: a key can map to null.//不能写成get(key)!=null因为一个key是可以映射到null的；
        return findNode(key) != null;//错误写法：比如：key存在，但是value=null，返回null：正确应该返回true;
    }
//@Override
    //public int size(){
    //return size;
//}
    @Override
    public int size() {
        return size;
    }
//@Override
    //public void clear(){
    //buckets=(Collection<Node>)new Collection[buckets.length];
    //size=0;
    //}
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        // Keep the current capacity, just drop all the pairs.//保留当前容量，只把所有键值对丢弃
        buckets = (Collection<Node>[]) new Collection[buckets.length];
        size = 0;
    }
//public void insert(Node node){
    //int i=Math.floorMod(node.key.hashCode(),bucket.lengths);
    //if(buckets[i]==null{buckets[i]=createBucket();
    //buckets[i].add(node.key,node.value):错误，add只收一个参数
//buckets[i].add(node);直接将整个节点传进去；
    /** Adds node to the bucket its key hashes to, without touching size.
     *  Used by resize(), which moves every existing pair. *///把节点放到他的key哈希到的那个桶里，但不改变size;这个方法由resize()使用，用来搬运每一个键值对；
    //不能直接用put搬：put含有size++;搬运会改变桶的大小
    private void insert(Node node) {//bucketFor:拿到桶，insert:插入元素；bucketFor返回的是一个集合，如果没有桶就创建新桶，
        int i = Math.floorMod(node.key.hashCode(), buckets.length);
        if (buckets[i] == null) {
            buckets[i] = createBucket();
        }
        buckets[i].add(node);
    }
//重新算容量的方法；
    //public void resize(int newCapacity){
    //Collections<Node>[] oldBuckets=buckets;
    //buckets=(Collection<Node>[]):new Collection[newCapacity];//创建新桶，
    //for(Collection<Node> bucket:oldBuckets){//依次取出所有oldBuckets里的东西，改名为bucket;临时有两个名字；
    //if(bucket==null)continue;//跳出当前循环；
    //插入新组：
    //for(Node node:bucket)
    //insert(node);}}}
    /** Grows the backing array to newCapacity and rehashes every pair.
     *  Every index changes, so no pair can be copied over in place. *///把底层数组扩大到newCapacity:并重新哈希每一对键值对；每个元素的下标都会改变，所以没有任何一对能拷贝过去；
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {//扩容方法，扩容前先把旧的桶存起来备用；
        Collection<Node>[] oldBuckets = buckets;
        buckets = (Collection<Node>[]) new Collection[newCapacity];
        for (Collection<Node> bucket : oldBuckets) {//for-each遍历必然引入一个新的循环变量
            if (bucket == null) {
                continue;
            }
            for (Node node : bucket) {
                insert(node);
            }
        }
    }
//@Override
    //public V remove(K key){
    //int i=bucketIndex(key);
    //if(buckets[i]==null)return null;
    //Iterator<Node>
    @Override
    public V remove(K key) {
        int i = bucketIndex(key);//算出key该去那个桶；
        Collection<Node> bucket = buckets[i];//
        if (bucket == null) {//
            return null;//三行等价于：if(buckets[i]==null)return null;
        }
        Iterator<Node> it = bucket.iterator();//拿到桶的迭代器，类型为：Iterator<Node>
        while (it.hasNext()) {
            Node node = it.next();
            if (key.equals(node.key)) {
                // Removing through the iterator is safe for every Collection
                // type used as a bucket (iterator().remove() is supported).//通过迭代器删除，对每种用作桶的Collection类型都是安全的，因为他们都支持iterator().remove();
                //不能用bucket.remove(node):在桶遍历的时候进行改动会让迭代器抛出异常；安全删除法
                it.remove();
                size -= 1;
                return node.value;
            }
        }
        return null;
    }
//必须写：
    //@Override
    //public  Set<K> keySet(){
    //Set<K> keys=new HshSet<>();
    //for(K key:this)keys.add(key);return keys;
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();//import java.util.HashSet;建一个不重复的集合；效果相当于建一个桶，creatBuckets用来建桶，HashSet用来建一个集合装keys;
        for (K key : this) {//集合名：keys；for(类型：新的变量名;正在遍历的对象）；
            keys.add(key);//for(K key:this表示当前正在执行代码的那个对象；//this:当前对象MyHashMap;this.字段：当前对象的字段；
        }//this(.....)调用本类另一个构造器；
        return keys;
    }
//
    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    /** Iterates over every key in the map, bucket by bucket. *///遍历map中的每一个key,一个同届一个通的遍历；
    //类名：KeyIterator;接口名：implements Iterator<K>;
    private class KeyIterator implements Iterator<K> {//接口的方法返回一个类；
        /** Index of the next bucket to look at. *///第一个要查看的桶的下标
        private int nextBucket=0;//记录查到底几个桶了
        /** Iterator over the current bucket, or null when it's exhausted. *///当前桶的迭代器：当这个桶已经遍历完，他就是null;
        private Iterator<Node> nodes;//记录走到桶的第几个格子了
//@override 重写方法，不针对重写后的方法，而针对重写前的接口；
        //@Override
        //public boolean hasNext(){
        //while((nodes!=null||nodes.hasnext()==false)&&nextBuckets<buckets.length)1还没开始或刚才的桶为null2.node.hasNext:同理的所有结点都被取过了
        @Override//
        public boolean hasNext() {//源文件未要求；
            while ((nodes == null || !nodes.hasNext()) && nextBucket < buckets.length) {
                Collection<Node> bucket = buckets[nextBucket++];
                nodes = bucket == null ? null : bucket.iterator();
            }
            return nodes != null && nodes.hasNext();
        }
//
        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();//throw new IllegalArgumentException
            }
            return nodes.next().key;
        }
    }
}
