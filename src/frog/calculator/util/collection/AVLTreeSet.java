package frog.calculator.util.collection;

import frog.calculator.util.IComparator;

/**
 * 使用AVL树构造的一个集合<br/>
 * 特点, 查找速度快; 增删速度虽然为O(lgn), 但是相较于红黑树略慢
 *
 * @param <T> 实际存储的元素的类型
 */
public class AVLTreeSet<T> extends AbstractCollection<T> implements ISet<T> {

    /**
     * 树的根节点
     */
    private AVLNode<T> root;

    /**
     * 集合中元素个数
     */
    private int modCount = 0;

    /**
     * 比较器
     */
    private final IComparator<T> comparator;

    /**
     * 构造一棵由AVL树实现的集合
     *
     * @param comparator 比较器, 用于比较两个元素的大小
     */
    public AVLTreeSet(IComparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null.");
        }
        this.comparator = comparator;
    }

    /**
     * 向集合中添加元素
     *
     * @param t 元素
     * @return 是否添加成功, 如果添加失败, 说明该树中已经存在等值的元素
     */
    @Override
    public boolean add(T t) {
        if (root == null) {
            root = new AVLNode<>();
            root.data = t;
            modCount = 1;
        } else {
            AVLNode<T> cursor = this.root;
            int mark = 0;
            AVLNode<T> p = cursor;
            while (cursor != null) {
                p = cursor;
                mark = this.comparator.compare(t, cursor.data);
                if (mark == 0) {
                    return false;
                } else if (mark < 0) {
                    cursor = cursor.left;
                } else {
                    cursor = cursor.right;
                }
            }

            // 插入新节点
            AVLNode<T> nextNode = new AVLNode<>();
            nextNode.data = t;
            nextNode.parent = p;
            p.height++;
            if (mark < 0) {
                p.left = nextNode;
            } else {
                p.right = nextNode;
            }

            // 修复树的平衡, 并重新计算高度
            AVLNode<T> c = nextNode;
            while (p.parent != null) {
                int lh = height(p.parent.left);
                int rh = height(p.parent.right);
                int f = lh - rh;
                if (f > 1) {  // 左子树过高
                    if (c == p.right) {
                        leftRotate(p);
                        p = p.parent;
                    }
                    rightRotate(p.parent);  // p的位置向根的方向, 上移一层
                    c = p;
                } else if (f < -1) {   // 右子树过高
                    if (c == c.left) {
                        rightRotate(p);
                        p = p.parent;
                    }
                    leftRotate(p.parent);
                    c = p;
                } else {
                    p.parent.height = (lh > rh ? lh : rh) + 1;
                    c = p;
                    p = p.parent;
                }
            }

            modCount++;
            root = p;
        }

        return true;
    }

    /**
     * 右旋
     *
     * @param oldRoot 旋转前, 子树的根
     */
    private void rightRotate(AVLNode<T> oldRoot) {
        AVLNode<T> newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;

        // 修改父节点
        newRoot.parent = oldRoot.parent;
        if (oldRoot.left != null) {
            oldRoot.left.parent = oldRoot;
        }
        oldRoot.parent = newRoot;

        int lh = height(oldRoot.left);
        int rh = height(oldRoot.right);
        oldRoot.height = (lh > rh ? lh : rh) + 1;

        lh = height(newRoot.left);
        newRoot.height = (oldRoot.height > lh ? oldRoot.height : lh) + 1;

        // 修改旋转的子树的总parent的子节点指向
        fixParent(newRoot, oldRoot);
    }

    /**
     * 左旋
     *
     * @param oldRoot 旋转前, 子树的根
     */
    private void leftRotate(AVLNode<T> oldRoot) {
        AVLNode<T> newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;

        // 修改父节点
        newRoot.parent = oldRoot.parent;
        if (oldRoot.right != null) {
            oldRoot.right.parent = oldRoot;
        }
        oldRoot.parent = newRoot;

        int lh = height(oldRoot.left);
        int rh = height(oldRoot.right);
        oldRoot.height = (lh > rh ? lh : rh) + 1;

        rh = height(newRoot.right);
        newRoot.height = (oldRoot.height > rh ? oldRoot.height : rh) + 1;

        // 修改旋转的子树的总parent的子节点指向
        fixParent(newRoot, oldRoot);
    }

    /**
     * 用于旋转之后对新的根父节点的指向以及高度的修复
     *
     * @param newRoot 旋转之后新的子根
     * @param oldRoot 旋转之前的子根
     */
    private void fixParent(AVLNode<T> newRoot, AVLNode<T> oldRoot) {
        AVLNode<T> sp = newRoot.parent;
        if (sp != null) {
            int h;
            if (sp.left == oldRoot) {
                sp.left = newRoot;
                h = height(sp.right);
            } else {
                sp.right = newRoot;
                h = height(sp.left);
            }
            sp.height = (newRoot.height > h ? newRoot.height : h) + 1;
        }
    }

    /**
     * 获取一棵子树的高度
     *
     * @param root 子树的根
     * @return 子树的高度
     */
    private int height(AVLNode<T> root) {
        return root == null ? 0 : root.height;
    }

    /**
     * 从集合中删除一个元素
     * @param t 待删除的元素
     * @return 返回是否删除成功
     */
    @Override
    public boolean remove(T t) {
        if(this.root == null){ return false; }

        // 查找到该节点
        AVLNode<T> cursor = this.root;
        int mark;
        while (cursor != null) {
            mark = comparator.compare(t, cursor.data);
            if (mark == 0) {
                break;
            } else if (mark > 0) {
                cursor = cursor.right;
            } else {
                cursor = cursor.left;
            }
        }

        if (cursor == null) { return false; }

        remove(cursor);

        return true;
    }

    /**
     * 根据指定的节点, 将其从树中移除, 并调整树的平衡
     * @param cursor 需要移除的节点
     */
    private void remove(AVLNode<T> cursor){
        AVLNode<T> p = cursor.parent;
        AVLNode<T> nr;   // 替换被删除节点的节点
        AVLNode<T> b = p;   // 需要调整的最底层节点
        if(cursor.left != null && cursor.right != null){
            if (cursor.left.height > cursor.right.height){
                nr = cursor.left.maximum();  // 显然nr不存在right, 可能存在left, 并且nr一定是nr.parent的right
                if(nr.parent != cursor){
                    b = nr.parent;
                    if(nr.left != null){
                        b.right = nr.left;
                        nr.left.parent = b;
                    }
                    b.right = null;
                    nr.left = cursor.left;
                }
                nr.right = cursor.right;
            }else{
                nr = cursor.right.minimum();
                if(nr.parent != cursor){
                    b = nr.parent;
                    if(nr.right != null){
                        b.left = nr.right;
                        nr.right.parent = b;
                    }
                    b.left = null;
                    nr.right = cursor.right;
                }
                nr.left = cursor.left;
            }
            cursor.left.parent = cursor.right.parent = nr;
            nr.parent = p;
        }else{
            nr = cursor.left == null ? cursor.right : cursor.left;
            if(nr != null){ nr.parent = p; }
        }

        if(p == null){
            this.root = nr;
        }else if(p.left == cursor){ // 被删节点是父节点的左节点
            p.left = nr;
        }else { // 被删节点是父节点的右节点
            p.right = nr;
        }

        // b的子树一定是平衡的
        AVLNode<T> r = null;    //调整过程中, 整棵树的根可能会变化, 用这个记录
        while(b != null){
            int lh = height(b.left);
            int rh = height(b.right);
            int f = lh - rh;
            if(f > 1){  // 左子树高
                assert b.left != null;
                if(height(b.left.right) > height(b.left.left)){
                    leftRotate(b.left);
                }
                rightRotate(b);
                b = b.parent;
            }else if(f < -1){   // 右子树高
                assert b.right != null;
                if(height(b.right.left) > height(b.right.right)){
                    rightRotate(b.right);
                }
                leftRotate(b);
                b = b.parent;
            }else{  // 平衡
                b.height = (lh > rh ? lh : rh) + 1;
            }
            r = b;
            b = b.parent;
        }

        if(r != null){ this.root = r; }

        this.modCount--;
    }

    /**
     * 清空集合
     */
    @Override
    public void clear() {
        this.modCount = 0;
        this.root = null;
    }

    /**
     * 根据指定的key查找集合中与其等值的元素
     *
     * @param t 待查找的key
     * @return 查找的结果, 如果没有找到, 返回null
     */
    @Override
    public T find(T t) {
        if (root == null) {
            return null;
        }

        AVLNode<T> cursor = this.root;
        T data = null;

        int mark;
        while (cursor != null) {
            mark = this.comparator.compare(t, cursor.data);
            if (mark > 0) {
                cursor = cursor.right;
            } else if (mark < 0) {
                cursor = cursor.left;
            } else {
                data = cursor.data;
                break;
            }
        }
        return data;
    }

    /**
     * 获取集合中元素个数
     * @return 元素个数
     */
    @Override
    public int size() {
        return this.modCount;
    }

    /**
     * 判断一个集合是否是空集合
     *
     * @return true - 空集合, false - 非空集合
     */
    @Override
    public boolean isEmpty() {
        return this.modCount == 0;
    }

    /**
     * 判断集合中是否存在与指定key相等的元素
     *
     * @param t 待匹配的key
     * @return true - 存在, false - 不存在
     */
    @Override
    public boolean contains(T t) {
        return this.find(t) != null;
    }

    /**
     * 获取集合的迭代器
     *
     * @return 迭代器
     */
    @Override
    public Iterator<T> iterator() {
        return new AVLIterator();
    }

    /**
     * avl tree 集合的迭代器
     */
    private class AVLIterator implements Iterator<T> {

        /**
         * 记录在创建该迭代器时, 集合中元素的个数<br/>
         * 用于监测并发修改异常
         */
        private int expectedModCount = modCount;

        private AVLNode<T> cursor;

        private AVLNode<T> viewNode;

        private AVLIterator() {
            if(AVLTreeSet.this.root != null){
                cursor = AVLTreeSet.this.root.minimum();
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
            if (this.expectedModCount != AVLTreeSet.this.modCount) {
                throw new IllegalStateException("concurrent modify exception.");
            }

            if(viewNode == null){
                throw new IllegalStateException("no element can be remove.");
            }

            AVLTreeSet.this.remove(viewNode);
            viewNode = null;
            this.expectedModCount--;
        }
    }

    /**
     * AVL树的节点类
     */
    private static class AVLNode<T> {

        /**
         * 左节点/左子树
         */
        private AVLNode<T> left;

        /**
         * 右节点/右子树
         */
        private AVLNode<T> right;

        /**
         * 双亲节点
         */
        private AVLNode<T> parent;

        /**
         * 该节点及它的子树的总高度
         */
        private int height = 1;

        /**
         * 节点存储的实际对象
         */
        private T data;

        /**
         * 找到以该节点作为根的子树的最大值节点
         * @return 返回最大值的节点
         */
        private AVLNode<T> maximum() {
            AVLNode<T> cursor = this;
            while (cursor.right != null) {
                cursor = cursor.right;
            }
            return cursor;
        }

        /**
         * 找到以该节点作为根的子树的最小值节点
         * @return 返回最小值的节点
         */
        private AVLNode<T> minimum() {
            AVLNode<T> cursor = this;
            while (cursor.left != null) {
                cursor = cursor.left;
            }
            return cursor;
        }

        /**
         * 获取该节点的后继节点
         * @return 紧邻该节点的后继节点
         */
        private AVLNode<T> successor(){
            if(this.right != null){
                return this.right.minimum();
            }else{
                AVLNode<T> child = this;
                AVLNode<T> cursor = this.parent;
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
        private AVLNode<T> predecessor(){
            if(this.left != null){
                return this.left.maximum();
            }else{
                AVLNode<T> child = this;
                AVLNode<T> cursor = this.parent;
                while(cursor != null && child == cursor.left){
                    child = cursor;
                    cursor = child.parent;
                }
                return cursor;
            }
        }

        @Override
        public String toString() {
            return data.toString() + ":" + height;
        }
    }

}
