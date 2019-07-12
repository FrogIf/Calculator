package frog.calculator.util;

public class AVLTree<T extends Comparable<T>> {

    private AVLNode<T> root;

    public void add(T t){
        if(root == null){
            root = new AVLNode<>();
            root.data = t;
        }else{
            AVLNode<T> nextTree = new AVLNode<>();
            nextTree.data = t;
            root = root.create(nextTree);
        }
    }

    public T find(T t){
        if(root == null){
            return null;
        }
        return root.retrieve(t);
    }

    public void remove(T t){
        if(root != null){
            root = root.delete(t);
        }
    }

    public boolean isEmpty(){
        return root == null;
    }

    private static class AVLNode<T extends Comparable<T>> {

        private AVLNode<T> left;

        private AVLNode<T> right;

        private int height = 1;   // 默认树高度为1

        private T data;

        // 从树中查找某一数据
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

        // 向树中插入数据
        private AVLNode<T> create(AVLNode<T> node){
            T t = node.data;
            int mark = data.compareTo(t);

            if(mark == 0){
                this.data = t;
            }else if(mark > 0){ // 向左侧插入数据
                if(this.left == null){
                    this.left = node;
                }else{
                    this.left = this.left.create(node);
                }
            }else{  // 向右侧插入数据
                if(this.right == null){
                    this.right = node;
                }else{
                    this.right = this.right.create(node);
                }
            }

            judgeTreeHeight(this);

            return this.balanceTree();
        }

        private AVLNode<T> delete(T t){
            int mark = data.compareTo(t);

            AVLNode<T> root = this;
            if(mark == 0){  // 删除中部数据
                if(this.left == null && this.right == null){
                    root = null;
                }else if(this.left == null){
                    root = this.right;
                }else if(this.right == null){
                    root = this.left;
                }else{  // 左右节点均不为null
                    AVLNode<T> lMax = findMax(this.left);
                    T oldData = this.data;
                    this.data = lMax.data;
                    lMax.data = oldData;
                    this.left = this.left.delete(t);
                }
            }else if(mark > 0){ // 删除左侧数据
                if(this.left != null){
                    this.left = this.left.delete(t);
                }
            }else{  // 删除右侧数据
                if(this.right != null){
                    this.right = this.right.delete(t);
                }
            }

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
