//void put(K key, V value);!//一个文件只能有一个public 类，且要和文件名相同，其他类可以写在同一文件里。
//V get(K key)!;
//boolean containsKey(K key)!;
//int size()!;
//void clear()!;
//Set<K> keySet()!;
//V remove(K key)!;
//类=模具，对象=用模具做出来的东西，接口=一份必须会做什么的能力清单；方法=物件会做的事，
//类：class,里面可以装：字段，方法，构造器，静态成员。
//public class Node{
//K key;类里面存数据的变量，字段，K key;V value;Node left,Node right;方法显然；构造器：创建对象时调用的特殊方法，名字必须和类名一样，没有返回值
//V value;构造器举例：Node(K,k,v,v);静态成员举例:private static final int;DEFAULT 16;属于类本身但不是对象，用类名.xxx访问
//Node left right;类的成员默认有访问修饰符：public,private,protected,private只有这个类内部，protected这个类+子类+同包；
//}private Node root意思是只能在类内部使用；
//对象/实例：new出来的东西；对象：object;实例：instance;new出来的东西；
//Node n=new Node(k,v);Node是类，n是变量，指向一个对象，new Node:调用构造器；实例和对象是近义词。
//一个类可以new出无限个对象，每个对象有自己的一个字段。
//Node a=new Node(x,1):
//Node b=new Node(y,2);a.key和b.key是两个不相干的内存位置。
//private static int count;所有Node对象共享这一个count;
//private int size;每个BSTMap对象个有一份size;
//方法：对象会做的事，
//private V get(K key){//方法签名：返回类型：V;方法名：get;参数列表：K key;方法体在大扩号里面
//return get(root,key);}
//void put(K key,V value)k,value是形参；当方法名相同，参数不同时，是两个不同的方法；重载只看参数不看类型，int size()和void size(int n)能共存，但int size()和void size()是不能共存的。
//public interface Map61B<K V>extends Iterable<K>{interface:接口名————能力清单。接口：不能new:new Map61B<>()就算实现也不行。new的只能是类。
//void put(K key,V value);//一个类可以实现多个接口，但只能继承一个类，class X extends A implements B,C;
//V get(K,key);//implements和extends的区别：extends:类继承类，“我是她的子型，继承他的东西。”implements:类实现接口，承诺提供类里面的全部方法。Map61B extends Iterable<K>是接口继承接口，
//boolean containsKey(K key);//意思是：实现Map61B的人也得顺便提供iterator();泛型参数：Map61B<k V>里的K,V是接口自己的类型参数，BSTMap实现时implements Map61B<K V>就是用BSTMap里的K V 填进去；
//}
import java.util.Iterator;//接口；接口：一份方法签名清单，规定要具备哪些能力，没有实现；类：真正有实现的，能new出来；方法：类里面的函数;静态成员(static)
import java.util.Set;//接口
import java.util.TreeSet;

public class BSTMap <K extends Comparable<K>,V> implements Map61B<K,V> {//Comparable<K>要求K有compareTo(k)方法，能和自己同类型的对象比大小。方法名必须是compareTo,返回值是int 类型，参数是一个k.*/

    public class Node {
        K key;//这是字段声明，值由构造器传入，this.key=key才赋值。等号是赋值语句用的
        V value;
        Node left;//left right是Node的字段，Node可以指向自己的字段，称为递归
        Node right;//指向左子树根，可能为null;通常，大写Node表示一个类；

        Node(K key, V value) {//构造器，用来给新结点传入key和value;构造器是new Node(K V)创建对象时自动调用的方法，给字段赋初值；名字与类名相同，没有返回类型。
            this.key = key;//把参数key的值存进对象的字段key;字段属于对象，访问时：this.key;this表示访问，key是字段名。
            this.value = value;//蓝色Node:内部类；
        }
    }

    //类是字段的定义。new BSTMap<>()创建出来的对象才有自己的root.new BSTMap能创建对象。private保证安全性。
    //记录元素个数。

    public void put(K key, V value) {//参数key的类型为K,参数value的类型为V;V是任意类型，不一定有Comparable功能。
        root = put(root, key, value);//注意：和put(K key V value)不是同一个方法！重载关系。
    }

    private Node put(Node x, K key, V value) {//用于递归的方法，以x为根节点的子树里插入，返回该子树的新根。
        if (x == null) {//引用，x==null表示不指向任何东西。不能进行指针运算。
            size++;//空节点就是新的key要插入的位置。元素个数+1；
            return new Node(key, value);//调用的x是父节点，返回值是子节点。new Node 创建并返回一个对象，
        }

        int cmp = key.compareTo(x.key);//比较新节点和当前节点key的大小。key是要插入的节点，x.key是树上已有的节点
        if (cmp < 0) {//左小右大，去左节点，
            x.left = put(x.left, key, value);
        } else if (cmp > 0)
            x.right = put(x.right, key, value);
        else x.value = value;//右边的value是新输入的value;插入时要覆盖原来的x.value
        return x;//把当前的子树的根交给上一层
    }

    public V get(K key) {
        return get(root, key);
    }


    private int size = 0;
    private Node root;

    private V get(Node x, K key) {//get 返回的是value,
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else return x.value;//私有的get方法是拿到x.value,所以return x.value;
    }

    public boolean containsKey(K key) {//containsKey方法：返回true或者false,判断key在不在
        return containsKey(root, key);
    }

    private boolean containsKey(Node x, K key) {//boolean说明返回true或者false，并且进返回这两种类型。
        if (x == null) {
            return false;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return containsKey(x.left, key);
        } else if (cmp > 0) {
            return containsKey(x.right, key);
        } else return true;
    }

    public int size() {
        return size;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public Set<K> keySet() {
        Set<K> keys = new TreeSet<>();
        inOrder(root, keys);
        return keys;
    }

    private void inOrder(Node x, Set<K> keys) {
        if (x == null) {
            return;
        }
        inOrder(x.left, keys);
        keys.add(x.key);
        inOrder(x.right, keys);
        keys.add(x.key);
    }

    public V remove(K key) {
        V old = get(key);
        if (old == null) {
            return null;
        }
        if (!containsKey(key)) return null;//!表示取反，等价于写：if(containsKeys(key)==false)
        root = remove(root, key);
        size--;
        return old;
    }

    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return remove(x.left, key);
        } else if (cmp > 0) {
            return remove(x.right, key);
        }
        if (x.left == null && x.right == null) {
            //还要判断有没有孩子的情况；x.left指左孩子，x.right指右孩子。孩子指节点
            return null;//没有左孩子且没有右孩子
        } else if (x.left == null) {
            return x.right;
        }
        if (x.right == null) {
            return x.left;
        }//在BST里面，永远只比较key,永远不比较value;key=学号，value=姓名，顺着学号找姓名。
        else {//只需要返回右子树里面最小的值，根据左小右大，右子树里面最小的永远大于所有左子树的key，小于右子树的key;
            Node min = x.right;//进入右子树但是要一直往左走；
            while (min.left != null) {//这样可以找到最靠近x的key
                min = min.left;
            }
            x.key = min.key;
            x.value = min.value;//min的节点还在树上，只是把值复制过来了，需要调用函数把他删掉。
            x.right = remove(x.right, min.key);
        }
        return x;

    }

    public Iterator<K> iterator() {//该方法返回一个能逐个吐出出所有key的迭代器/
        return keySet().iterator();
    }
}


