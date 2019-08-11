package frog.calculator.util.collection;

public class TreeSet<T extends Comparable<T>> implements ISet<T> {

    private AVLNode<T> root;

    private int modCount = 0;

    @Override
    public boolean add(T t){
        if(root == null){
            root = new AVLNode<>();
            root.data = t;
            modCount = 1;
        }else{
            AVLNode<T> nextTree = new AVLNode<>();
            nextTree.data = t;
            root = root.create(nextTree, this);
        }
        return true;
    }

    @Override
    public boolean remove(T t){
        if(root != null){
            int size = this.modCount;
            root = root.delete(t, this);
            return size > this.modCount;
        }else{
            return false;
        }
    }

    @Override
    public void clear() {
        this.modCount = 0;
        this.root = null;
    }

    @Override
    public T find(T t){
        if(root == null){
            return null;
        }
        return root.retrieve(t);
    }

    @Override
    public int size() {
        return this.modCount;
    }

    @Override
    public boolean isEmpty(){
        return this.modCount == 0;
    }

    @Override
    public boolean contains(T t) {
        return this.find(t) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<T> {

        private int expectedModCount = modCount;

        private Stack<AVLNode<T>> line = new Stack<>();

        private T viewData = null;

        private TreeIterator() {
            AVLNode<T> cursor = TreeSet.this.root;
            while(cursor != null){
                line.push(cursor);
                cursor = cursor.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !line.isEmpty();
        }

        @Override
        public T next() {
            AVLNode<T> view = line.top();
            this.viewData = view.data;

            if(view.right != null){
                line.pop();
                AVLNode<T> cursor = view.right;
                while(cursor != null){
                    line.push(cursor);
                    cursor = cursor.left;
                }
            }else{
                line.pop();
            }

            return viewData;
        }

        @Override
        public void remove() {
            if(this.expectedModCount != TreeSet.this.modCount){
                throw new IllegalStateException("concurrent modify exception.");
            }

            AVLNode<T> cursor = TreeSet.this.root = TreeSet.this.root.delete(viewData, TreeSet.this);
            this.expectedModCount--;

            T vData = viewData;

            // rebuild the line
            line.clear();
            while(cursor != null){
                int mark = vData.compareTo(cursor.data);
                if(mark < 0){
                    if(cursor.right == null){
                        break;
                    }else{
                        cursor = cursor.right;
                    }
                } else {
                    line.push(cursor);
                    cursor = cursor.left;
                }
            }
        }
    }

    private static class AVLNode<T extends Comparable<T>> {

        private AVLNode<T> left;

        private AVLNode<T> right;

        private int height = 1;   // default tree height is 1. because there must be itself.

        private T data;

        /**
         * retrieve data by assign data <br/>
         * because of type parameter is Comparable, so we can by a simple object get the object's more information which is equal to the simple object.
         * @param t a needed to find object
         * @return find result. if not find, return null.
         */
        private T retrieve(T t){
            T result = null;

            int mark = t.compareTo(this.data);

            if(mark == 0){
                result = this.data;
            }else if(mark > 0 && this.right != null){
                result = this.right.retrieve(t);
            }else if(mark < 0 && this.left != null){
                result = this.left.retrieve(t);
            }

            return result;
        }

        /**
         * insert node into avl tree
         * @param node need to insert
         * @param belong the tree belong to which set, to update belong's modCount. this class is static, so param "belong" is necessary.
         * @return new tree root
         */
        private AVLNode<T> create(AVLNode<T> node, TreeSet<T> belong){
            T t = node.data;
            int mark = data.compareTo(t);

            if(mark == 0){
                // if insert node's is exists, update this.
                this.data = t;
            }else if(mark > 0){ // insert into left branch
                if(this.left == null){
                    this.left = node;
                    belong.modCount++;
                }else{
                    this.left = this.left.create(node, belong);
                }
            }else{  // insert into right branch
                if(this.right == null){
                    this.right = node;
                    belong.modCount++;
                }else{
                    this.right = this.right.create(node, belong);
                }
            }

            judgeTreeHeight(this);

            return this.balanceTree();
        }

        /**
         * delete assign data
         * @param t aim data
         * @param belong which tree that current node belong.
         * @return new tree root.
         */
        private AVLNode<T> delete(T t, TreeSet<T> belong){
            int mark = data.compareTo(t);

            AVLNode<T> root = this;
            if(mark == 0){  // find it.
                if(this.left == null && this.right == null){
                    root = null;
                    belong.modCount--;
                }else if(this.left == null){
                    root = this.right;
                    belong.modCount--;
                }else if(this.right == null){
                    root = this.left;
                    belong.modCount--;
                }else{  // need to delete mid node. but left and right both is not null.
                    AVLNode<T> lMax = findMax(this.left);
                    T oldData = this.data;
                    this.data = lMax.data;
                    lMax.data = oldData;
                    this.left = this.left.delete(t, belong);
                }
            }else if(mark > 0){ // delete from left.
                if(this.left != null){
                    this.left = this.left.delete(t, belong);
                }
            }else{  // delete from right.
                if(this.right != null){
                    this.right = this.right.delete(t, belong);
                }
            }

            judgeTreeHeight(this);

            if(root != null){
                root = root.balanceTree();
            }
            return root;
        }

        private AVLNode<T> rRotate(AVLNode<T> root){
            AVLNode<T> newRoot = root.left;
            root.left = newRoot.right;
            newRoot.right = root;

            judgeTreeHeight(root);
            judgeTreeHeight(newRoot);

            return newRoot;
        }

        private AVLNode<T> lRotate(AVLNode<T> root){
            AVLNode<T> newRoot = root.right;
            root.right = newRoot.left;
            newRoot.left = root;

            judgeTreeHeight(root);
            judgeTreeHeight(newRoot);
            return newRoot;
        }

        // 获取当前树左右子树高度中更高的那一个的高度
        private static int maxHigh(AVLNode nodeA, AVLNode nodeB){
            int aH = height(nodeA);
            int bH = height(nodeB);
            return aH > bH ? aH : bH;
        }

        private static int balanceFactor(AVLNode nodeA, AVLNode nodeB){
            return height(nodeA) - height(nodeB);
        }

        // 树自平衡调整
        // 每插入或删除一个节点就调用一次进行自平衡
        private AVLNode<T> balanceTree(){
            /*
             * 由于每一次插入或删除都会检查平衡性
             * 所以有:
             *      1. 每增加一个节点导致的不平衡, 其增加节点肯定没有兄弟节点(如果有兄弟节点在之前就应该导致不平衡)
             *      2. 每删除一个节点导致的不平衡, 在删除之前, 该节点可定没有兄弟节点(如果有, 删除该节点不会影响树的高度)
             * 此外:
             *      如果不平衡, 则左右子树其中一颗高度至少是2, (bf = left.height - right.height >= 2)
             *
             * 该平衡操作是自下而上的, 所以平衡只需要关注当前节点树即可, 下级树肯定是保证平衡的
             */
            AVLNode<T> root = this;

            int bf = balanceFactor(this.left, this.right);  // 实际上每个增删操作只会使树的高度变化为1, 所以bf的取值范围是 bf >= -2 && bf <= 2

            if(bf < -1){    // 右侧树过高, 需要左旋
                int rlHeight = height(this.right.left);
                int rrHeight = height(this.right.right);
                if(rlHeight > rrHeight){
                    this.right = rRotate(this.right);
                    root = lRotate(this);
                }else{
                    root = lRotate(this);
                }
            }else if(bf > 1){   // 左侧树过高, 需要右旋
                int llHeight = height(this.left.left);
                int lrHeight = height(this.left.right);
                if(llHeight > lrHeight){
                    root = rRotate(this);
                }else{
                    this.left = lRotate(this.left);
                    root = rRotate(this);
                }
            }

            return root;
        }

        // 判断树高度
        private static void judgeTreeHeight(AVLNode root){
            root.height = maxHigh(root.left, root.right) + 1;
        }

        private static int height(AVLNode root){
            return root == null ? 0 : root.height;
        }

        private static <K extends Comparable<K>> AVLNode<K> findMax(AVLNode<K> root){
            if(root.right != null){
                return findMax(root.right);
            }else{
                return root;
            }
        }

        @Override
        public String toString(){
            return data.toString();
        }
    }

}
