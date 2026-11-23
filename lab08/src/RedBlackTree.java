//红黑树的五条要求：1.每个节点非红即黑；2.根节点必须是黑色3.每个叶子节点都是黑色4.红节点的两个孩子必须是黑色（不能红红相连）
//从任意节点到每个叶子的路径上，黑节点数量相同
//左旋与右旋：
//旋转能保持BST性质不变，并且改变树形的操作。
public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. *///树的根节点
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK////创建一个红黑树节点，内容为item,颜色根据isBlack的值决定；
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.//创建一个红黑树节点，数据为ITEM,颜色根据ISBLACK的值决定，左孩子为LEFT,有孩子维RIGHT;
         * @param isBlack//严格按照@param定义变量名；
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.//创建一棵空的红黑树
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children//翻转node以及他两个孩子的颜色，假定NODE同时有左孩子和有孩子
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if(node.isBlack==true)
            node.isBlack=false;
        else if(node.isBlack==false)
            node.isBlack=true;
        if(node.left.isBlack==true)
            node.left.isBlack=false;
        else if(node.left.isBlack==false)
            node.left.isBlack=true;
        if(node.right.isBlack==true)
            node.right.isBlack=false;
        else if(node.right.isBlack==false)
            node.right.isBlack=true;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node//对给定节点做右旋，返回这棵子树新的根节点。本次实现中，务必把新根和旧根的颜色互换：
     * @return
     *///右红左黑：左旋；左红左左：右旋；左右都红：换色；全黑/单左红：不动；顺序：左左右换
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> newRoot=node.left;
        node.left=newRoot.right;
        newRoot.right=node;
//node.left=newRoot.right;
        //newRoot.right=node;
        //换色：node和newRoot;
        boolean temp=node.isBlack;
        node.isBlack=newRoot.isBlack;
        newRoot.isBlack=temp;
        return newRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node//对给定节点做左旋。返回这棵子树新的根节点，本次实现中，务必把新根和旧根的颜色互换。
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> newRoot=node.right;
        node.right=newRoot.left;
        newRoot.left=node;
        boolean temp=node.isBlack;
        node.isBlack=newRoot.isBlack;
        newRoot.isBlack=temp;
        return newRoot;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.//一个辅助方法，用来判断给定节点是不是红色，null节点（子节点或叶子）自动视为黑色
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }//判断一个节点是不是红色，并且把null当成黑色；
    //node!=null:先判断空，为空则不继续；若第一个条件为false则第二个条件不聚会执行；
//如果node=null，return false;//isBlack===false表示红色！此时返回表示“不是”红色；
    //
    //如果node!=null&&node.isBlack==false,return true;返回“真”；表示是红色；
    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item//把ieam插入红黑树，把树的根结点染成黑色
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!//用于把item插入这棵树的辅助方法，代码中已提供注释，帮助拆解问题；对每一种情况，请思考执行那些操作时所需的场景
     * @param node//也务必回顾本类中的其它方法！
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.//建新节点：递归到底，创建红色叶子；
//对应递归到达空位时创建新节点；
        if(node==null){//插入用红色：如果插入的节点染成黑色，必然破坏黑高相等；
            return new RBTreeNode<>(false,item,null,null);
        }
        // TODO: Handle normal binary search tree insertion.//正常BST插入：和lab6的put一样；
        //不能在方法里面套方法；
        int cmp=item.compareTo(node.item);
        if(cmp>0){
            node.right=insertHelper(node.right,item);
        }
        else if(cmp<0){
            node.left=insertHelper(node.left,item);
        }
        else{
            //元素已经存在，什么也不做，与BST不同，BST要存key和value,红黑树是集合，只有元素，而BST是key value映射，存着键值对。

        }
        // TODO: Rotate left operation//左旋：红色跑到了右边//左旋：右红且左不红；右旋：左红且左左红；换色：左红且右红；
        //K key,V value;BST;//final item:红黑树；集合里面不能出现重复的元素
        /*if(node.left.isBlack==true&&node.right.isBlack==false){
            node=rotateLeft(node);
        }
        if(node.left.isBlack==false&&node.left.left.isBlack==false){
            node=rotateRight(node);
        }
        if(node.left.isBlack==false&&node.right.isBlack==false){
            flipColors(node);
        }*/
        if(isRed(node.left)==false&&isRed(node.right)==true){
            node=rotateLeft(node);//node=才能走完三部，直接return会导致只要条件1成立，就将结果交给上一层
        }
        if(isRed(node.left)==true&&isRed(node.left.left)==true){
            node=rotateRight(node);
        }
        if(isRed(node.left)==true&&isRed(node.right)==true){
            flipColors(node);
        }
        //写法错误，遇到空指针会抛出异常导致崩溃，而且插入时必然遇到空指针，要引用isRed方法；

        // TODO: Rotate right operation//右旋：左边有两个连续的红色

        // TODO: Color flip//换色：两个孩子都是红色

        return node; //fix this return statement
    }

}
