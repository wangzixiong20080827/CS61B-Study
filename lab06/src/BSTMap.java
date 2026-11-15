import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable <K>,V> implements Map61B<K,V>{
    public class Node{

        K key;
        V value;
        Node  left,right;
        Node(K key,V value){
            this.key=key;
            this.value=value;
        }
    }
    private Node root;
    private int size=0;
    @Override
            public void put (K key ,V value){
        root=put(root,key,value);
    }
    private Node put(Node x,K key,V value){
        if(x==null){
            size++;
            return new Node(key,value);
        }
        int cmp=key.compareTo(x.key);
        if(cmp<0){
            x.left=put(x.left,key,value);
        }
        else if(cmp>0){
            x.right=put(x.right,key,value);
        }
        else {
            x.value=value;
        }
        return x;
    }
        @Override
                public V get(K key){
        return get(root,key);
    }
    private V get(Node x,K key){
        if(x==null)return null;
        int cmp=key.compareTo(x.key);
        if(cmp<0)return get(x.left,key);
        else if(cmp>0)return get(x.right,key);
        else return x.value;

    }
            @Override
                    public boolean containsKey(K key){
       return containsKey(root,key);
    }
    private boolean containsKey(Node x,K key){
       if(x==null){
           return false;
       }
       int cmp=key.compareTo(x.key);
       if(cmp<0){
           return containsKey(x.left,key);
       }
       else if(cmp>0){
           return containsKey(x.right,key);
       }
       else return true;
    }
                @Override
                        public int size(){
        return size;
                }
                @Override
                        public void clear(){
  root=null;
  size=0;
    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    public void printInOrder(){

    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}

