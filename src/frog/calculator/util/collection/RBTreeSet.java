package frog.calculator.util.collection;

import frog.calculator.util.IComparator;

/**
 * 使用红黑树构造的集合<br/>
 * 特定 : 综合性能好, 增删速度比AVL树快, 查找速度比AVL稍慢
 *
 * @param <T> 实际存储的元素的类型
 */
public class RBTreeSet<T> implements ISet<T>{

    /*
     * 红黑树的性质:
     *     1. 根节点为黑节点
     *     2. 一个节点不是红节点就是黑节点
     *     3. 叶节点一定为黑节点(根据算法导论, 所有叶子结点都是null节点, 也就是说, 这里的末端节点并不是真实的叶节点)
     *     4. 红节点的子节点一定是黑节点
     *     5. 从一节点(不包含该节点本身)到其后代所有叶节点, 所经过的路径包含相同数目的黑节点(因此, 红黑树是黑平衡的!)
     */

    /**
     * 树的根节点
     */
    private RBNode<T> root;

    /**
     * 集合中元素个数
     */
    private int modCount = 0;

    /**
     * 比较器
     */
    private final IComparator<T> comparator;

    public RBTreeSet(IComparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null.");
        }
        this.comparator = comparator;
    }

    @Override
    public boolean add(T t) {
        if(this.root == null){
            RBNode<T> node = new RBNode<>();
            node.data = t;
            node.color = RBNode.BLACK;
            this.root = node;
            modCount = 1;
            return true;
        } else {
            // 查找可以插入元素的位置
            RBNode<T> cursor = this.root;
            RBNode<T> p = cursor;
            int mark = 0;
            while(cursor != null){
                p = cursor;
                mark = comparator.compare(t, cursor.data);
                if(mark == 0){
                    return false;
                }else if(mark > 0){
                    cursor = cursor.right;
                }else{
                    cursor = cursor.left;
                }
            }

            RBNode<T> node = new RBNode<>();
            node.data = t;
            node.parent = p;
            if(mark > 0){
                p.right = node;
            }else{
                p.left = node;
            }

            // fixup
            /*
             * 插入该节点可能破坏的性质有两个:
             * 1. 根节点是黑节点
             * 2. 红节点的子节点必定是黑节点
             * (并且这两个性质, 同一时间只会有一个被破坏(互斥))
             *
             * 下面变换实际关注的重点是: 如何变换, 保证一条路径的黑节点数目不变
             */
            while(node.parent != null && node.parent.color == RBNode.RED){
                if(node.parent.parent.left == node.parent){
                    RBNode<T> u = node.parent.parent.right;
                    if(u != null && u.color == RBNode.RED){  // 叔节点是红节点
                        node.parent.parent.color = RBNode.RED;
                        node.parent.color = RBNode.BLACK;
                        u.color = RBNode.BLACK;
                        node = node.parent.parent;
                    }else{
                        if(node == node.parent.right){
                            node = node.parent;
                            leftRotate(node);
                        }
                        node.parent.color = RBNode.BLACK;
                        node.parent.parent.color = RBNode.RED;
                        rightRotate(node.parent.parent);
                        // 至此, 这个循环就终结了
                    }
                }else{
                    RBNode<T> u = node.parent.parent.left;
                    if(u != null && u.color == RBNode.RED){  // 叔节点是红节点
                        node.parent.parent.color = RBNode.RED;
                        node.parent.color = RBNode.BLACK;
                        u.color = RBNode.BLACK;
                        node = node.parent.parent;
                    }else{  // 叔节点是黑节点
                        if(node == node.parent.left){
                            node = node.parent;
                            rightRotate(node);
                        }
                        node.parent.parent.color = RBNode.RED;
                        node.parent.color = RBNode.BLACK;
                        leftRotate(node.parent.parent);
                        // 至此, 这个循环就终结了
                    }
                }
            }

            this.root.color = RBNode.BLACK;

            modCount++;
            return true;
        }
    }

    @Override
    public boolean remove(T t) {
        if(this.root == null){
            return false;
        }

        RBNode<T> cursor = this.root;
        int mark;
        while (cursor != null){
            mark = comparator.compare(t, cursor.data);
            if(mark == 0){
                break;
            }else if(mark > 0){
                cursor = cursor.right;
            }else{
                cursor = cursor.left;
            }
        }

        if(cursor == null){ return false; }

        remove(cursor);

        return false;
    }

    private void remove(RBNode<T> node){
        /*
         * 记录被删除元素的颜色, 如果被删除节点有两个子节点, 则会使用该节点的后继节点补位
         * 并使用被删除节点的颜色, 对后继节点重新着色, 所以树中消失的节点的颜色是后继节点的颜色
         */
        int color = node.color;
        RBNode<T> fix;  // 需要修复节点起始位置, 从该节点开始向双亲的方向修复
        if(node.left == null){
            fix = node.right;
            if(fix == null){ fix = node.parent; }
            transplant(node, node.right);
        }else if(node.right == null){
            fix = node.left;
            transplant(node, node.left);
        }else{
            RBNode<T> fill = node.right.minimum();    // 填充空缺的节点
            color = fill.color;

            if(fill != node.right){
                transplant(fill, fill.right);
            }

            if(fill.right != null){
                fix = fill.right;
            }else{
                fix = fill;
            }

            transplant(node, fill);
            fill.color = node.color;
            if(node.right != fill){
                fill.right = node.right;
                fill.right.parent = fill;
            }
            fill.left = node.left;
            fill.left.parent = fill;
        }

        if(color == RBNode.BLACK){  // 只有消失的节点颜色是黑色时, 才会破坏树的红黑性质
            /*
             * 调用这个方法时, 可以将fix想象为双色节点, 既包含本身的颜色fix.color, 又包含赋予的black, 具有双重颜色
             * 所以, fixup实际上就是找一个地方把多出来的一重黑色放进去
             */
            deleteFixup(fix);
        }
        this.modCount--;
    }

    private void deleteFixup(RBNode<T> fix) {
        while(fix != this.root && fix.color == RBNode.BLACK){
            if(fix == fix.parent.left){
                RBNode<T> w = fix.parent.right; // fix的兄弟节点
                // case1
                if(w.color == RBNode.RED){
                    w.color = RBNode.BLACK;
                    fix.parent.color = RBNode.RED;
                    leftRotate(fix.parent);
                    w = fix.parent.right;
                }
                // case 2
                if(w.left.color == RBNode.BLACK && w.right.color == RBNode.BLACK){  // 根据性质5, b的左右子一定不会是null
                    w.color = RBNode.RED;
                    fix = fix.parent;
                }else{
                    // case3
                    if(w.right.color == RBNode.BLACK){  // 由于排除了w.left.color == w.right.color == BLACK, 所以如果这里w.left.color一定是red
                        w.color = RBNode.RED;
                        w.left.color = RBNode.BLACK;
                        rightRotate(w);
                        w = w.parent;
                    }

                    // case4
                    w.color = fix.parent.color;
                    w.right.color = fix.parent.color = RBNode.BLACK;
                    leftRotate(fix.parent);
                    fix = this.root;
                }
            }else{
                RBNode<T> w = fix.parent.left;
                if(w.color == RBNode.RED){
                    fix.parent.color = RBNode.RED;
                    w.color = RBNode.BLACK;
                    rightRotate(fix.parent);
                    w = fix.parent.left;
                }
                if(w.left.color == RBNode.BLACK && w.right.color == RBNode.BLACK){
                    w.color = RBNode.RED;
                    fix = fix.parent;
                }else{
                    if(w.left.color == RBNode.BLACK){
                        w.color = RBNode.RED;
                        w.right.color = RBNode.BLACK;
                        leftRotate(w);
                        w = w.parent;
                    }

                    w.color = fix.parent.color;
                    w.left.color = fix.parent.color = RBNode.RED;
                    rightRotate(fix.parent);
                    fix = this.root;
                }
            }
        }
        fix.color = RBNode.BLACK;
    }

    /**
     * 节点的移植<br/>
     * 不会连带移植传入两个节点的子节点
     * @param o 原节点, 它的位置需要被新节点替换
     * @param n 新节点
     */
    private void transplant(RBNode<T> o, RBNode<T> n){
        if(o.parent == null){
            this.root = n;
        }else if(o.parent.left == o){
            o.parent.left = n;
        }else{
            o.parent.right = n;
        }
        if(n != null){
            n.parent = o.parent;
        }
    }

    /**
     * 右旋
     * @param node 需旋转的子树的根
     */
    private void rightRotate(RBNode<T> node){
        RBNode<T> nr = node.left;
        node.left = nr.right;
        if(nr.right != null){
            nr.right.parent = node;
        }
        nr.right = node;
        nr.parent = node.parent;
        if(node.parent != null){
            if(node.parent.right == node){
                node.parent.right = nr;
            }else{
                node.parent.left = nr;
            }
        }else{
            this.root = nr;
        }
        node.parent = nr;
    }

    /**
     * 左旋
     * @param node 需旋转的子树的根
     */
    private void leftRotate(RBNode<T> node){
        RBNode<T> nr = node.right;
        node.right = nr.left;
        if(nr.left != null){
            nr.left.parent = node;
        }
        nr.left = node;
        nr.parent = node.parent;
        if(node.parent != null){
            if(node.parent.left == node){
                node.parent.left = nr;
            }else{
                node.parent.right = nr;
            }
        }else{
            this.root = nr;
        }
        node.parent = nr;
    }

    @Override
    public void clear() {
        this.modCount = 0;
        this.root = null;
    }

    @Override
    public boolean isEmpty() {
        return this.modCount == 0;
    }

    @Override
    public int size() {
        return this.modCount;
    }

    @Override
    public Iterator<T> iterator() {
        return new RBIterator();
    }

    @Override
    public T find(T t) {
        if(this.root == null){
            return null;
        }else{
            RBNode<T> cursor = this.root;
            T data = null;
            int mark;
            while(cursor != null){
                mark = comparator.compare(t, cursor.data);
                if(mark == 0){
                    data = cursor.data;
                    break;
                }else if(mark > 0){
                    cursor = cursor.right;
                }else{
                    cursor = cursor.left;
                }
            }
            return data;
        }
    }

    @Override
    public boolean contains(T t) {
        return find(t) != null;
    }

    /**
     * avl tree 集合的迭代器
     */
    private class RBIterator implements Iterator<T> {

        /**
         * 记录在创建该迭代器时, 集合中元素的个数<br/>
         * 用于监测并发修改异常
         */
        private int expectedModCount = modCount;

        private RBNode<T> cursor;

        private RBNode<T> viewNode;

        private RBIterator() {
            if(RBTreeSet.this.root != null){
                cursor = RBTreeSet.this.root.minimum();
            }
        }

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public T next() {
            T data = cursor.data;
            viewNode = cursor;
            cursor = cursor.successor();
            return data;
        }

        @Override
        public void remove() {
            if (this.expectedModCount != RBTreeSet.this.modCount) {
                throw new IllegalStateException("concurrent modify exception.");
            }

            if(viewNode == null){
                throw new IllegalStateException("no element can be remove.");
            }

            RBTreeSet.this.remove(viewNode);
            viewNode = null;
            this.expectedModCount--;
        }
    }

    private static class RBNode<T> {

        private static final int BLACK = 1;

        private static final int RED = 0;

        /**
         * 节点存储的实际数据
         */
        private T data;

        /**
         * 标记红黑树的节点颜色</br>
         * 0 - 红色; 1 - 黑色
         */
        private int color = 0;  // 默认是红节点

        /**
         * 左节点/右子树
         */
        private RBNode<T> left;

        /**
         * 右节点/右子树
         */
        private RBNode<T> right;

        /**
         * 双亲节点
         */
        private RBNode<T> parent;

        /**
         * 找到以该节点作为根的子树的最大值节点
         * @return 返回最大值的节点
         */
        private RBNode<T> maximum() {
            RBNode<T> cursor = this;
            while (cursor.right != null) {
                cursor = cursor.right;
            }
            return cursor;
        }

        /**
         * 找到以该节点作为根的子树的最小值节点
         * @return 返回最小值的节点
         */
        private RBNode<T> minimum() {
            RBNode<T> cursor = this;
            while (cursor.left != null) {
                cursor = cursor.left;
            }
            return cursor;
        }

        /**
         * 获取该节点的后继节点
         * @return 紧邻该节点的后继节点
         */
        private RBNode<T> successor(){
            if(this.right != null){
                return this.right.minimum();
            }else{
                RBNode<T> child = this;
                RBNode<T> cursor = this.parent;
                while(cursor != null && child == cursor.right){
                    child = cursor;
                    cursor = child.parent;
                }
                return cursor;
            }
        }

        /**
         * 获取一个节点的前驱节点
         * @return 紧邻该节点的前驱节点
         */
        private RBNode<T> predecessor(){
            if(this.left != null){
                return this.left.maximum();
            }else{
                RBNode<T> child = this;
                RBNode<T> cursor = this.parent;
                while(cursor != null && child == cursor.left){
                    child = cursor;
                    cursor = child.parent;
                }
                return cursor;
            }
        }

        @Override
        public String toString() {
            return data.toString();
        }

    }
}
