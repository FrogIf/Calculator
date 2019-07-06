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
        return root.retrieve(t);
    }

    private static class AVLNode<T extends Comparable<T>> {

        private AVLNode<T> left;

        private AVLNode<T> right;

        private int height = 1;   // 默认树高度为1

        private T data;

        private AVLNode<T> create(AVLNode<T> node){
            T t = node.data;
            int mark = data.compareTo(t);

            AVLNode<T> root = this;

            if(mark == 0){
                this.data = t;
            }else{
                if(mark > 0){   // 说明当前节点大, 向当前节点左侧插入
                    if(this.left == null){
                        this.left = node;
                    }else{
                        this.left = this.left.create(node);
                        root = balanceLeft(t);
                    }
                }else{ // 说明当前节点小, 向当前节点右侧插入
                    if(this.right == null){
                        this.right = node;
                    }else{
                        this.right = this.right.create(node);
                        root = balanceRight(t);
                    }
                }
            }

            judgeTreeheight(root);

            return root;
        }

        private AVLNode<T> delete(T t){
            int mark = t.compareTo(this.data);
            AVLNode<T> root = this;

            if(mark == 0){
                if(this.left == null && this.right == null){
                    root = null;
                }else{
                    if(this.left == null){
                        root = this.right;
                    }else if(this.right == null){
                        root = this.left;
                    }else{
                        if(this.left.height > this.right.height){
                            root = this.left;
                            if(this.left.right != null){
                                this.right = this.right.create(this.left.right);
                            }
                            this.left.right = this.right;
                        }else{
                            root = this.right;
                            AVLNode<T> rl = this.right.left;
                            if(this.right.left != null){
                                this.left = this.left.create(this.left.right);
                            }
                            this.right.left = this.left;
                            balanceLeft(rl.data);
                        }
                    }
                }
            }else if(mark < 0 && this.left != null){
                this.left = this.left.delete(t);
                root = balanceRight(t);
            }else if(mark > 0 && this.right != null){
                this.right = this.right.delete(t);
                root = balanceLeft(t);
            }

            return root;
        }

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


        private AVLNode<T> rrRotate(AVLNode<T> root){
            AVLNode<T> newRoot = root.left;
            root.left = newRoot.right;
            newRoot.right = root;

            judgeTreeheight(root);
            judgeTreeheight(newRoot);

            return newRoot;
        }

        private AVLNode<T> llRotate(AVLNode<T> root){
            AVLNode<T> newRoot = root.right;
            root.right = newRoot.left;
            newRoot.left = root;

            judgeTreeheight(root);
            judgeTreeheight(newRoot);

            return newRoot;
        }

        private AVLNode<T> lrRotate(AVLNode<T> root){
            root.right = llRotate(root.right);
            return rrRotate(root);
        }

        private AVLNode<T> rlRotate(AVLNode<T> root){
            root.left = rrRotate(root.left);
            return llRotate(root);
        }

        // 获取当前树左右子树高度中更高的那一个的高度
        private int maxHigh(AVLNode<T> left, AVLNode<T> right){
            int leftH = left == null ? 0 : left.height;
            int rightH = right == null ? 0 : right.height;
            return leftH > rightH ? leftH : rightH;
        }

        private int balanceFactor(AVLNode<T> left, AVLNode<T> right){
            int leftH = left == null ? 0 : left.height;
            int rightH = right == null ? 0 : right.height;
            return leftH - rightH;
        }

        private AVLNode<T> balanceLeft(T t){
            AVLNode<T> root = this;
            if(balanceFactor(this.left, this.right) == 2){
                if(t.compareTo(this.left.data) < 0){
                    root = rrRotate(this);
                }else{
                    root = lrRotate(this);
                }
            }
            return root;
        }

        private AVLNode<T> balanceRight(T t){
            AVLNode<T> root = this;
            if(balanceFactor(this.left, this.right) == -2){
                if(t.compareTo(this.right.data) > 0){
                    root = llRotate(this);
                }else{
                    root = rlRotate(this);
                }
            }
            return root;
        }

        // 判断树高度
        private void judgeTreeheight(AVLNode<T> root){
            root.height = maxHigh(root.left, root.right) + 1;
        }

        @Override
        public String toString(){
            return data.toString();
        }
    }

    public static void main(String[] args){
        int[] arr = {1,2 , 3, 4, 5, 6, 7, 8, 9, 10};

        AVLTree<Integer> tree = new AVLTree<>();
        for(int a : arr){
            tree.add(a);
        }

        System.out.println(tree);

        System.out.println(tree.find(3));
        System.out.println(tree.find(15));
    }


}
