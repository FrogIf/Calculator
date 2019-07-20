package frog.calculator.util;

public class LinkedList<T>{

    private LinkedNode<T> root = null;

    private LinkedNode<T> tail = null;

    private int size = 0;

    public void add(T t){
        if(root == null){
            root = new LinkedNode<>();
            tail = root;
        }
        tail.next = new LinkedNode<>();
        tail.next.data = t;
        tail = tail.next;
        size++;
    }

    public int size(){
        return size;
    }

    public Iterator getIterator(){
        return new Iterator(root);
    }

    public class Iterator{
        private LinkedNode<T> viewNode;

        private LinkedNode<T> preNode;

        public Iterator(LinkedNode<T> viewNode) {
            this.viewNode = viewNode;
        }

        public boolean hasNext(){
            return viewNode != null && viewNode.next != null;
        }

        public T next(){
            this.preNode = viewNode;
            this.viewNode = viewNode.next;
            return this.viewNode.data;
        }

        /**
         * 当前遍历节点前插入
         * @param t
         */
//        public void preInsert(T t){
//            LinkedNode<T> insertNode = new LinkedNode<>();
//            if(this.preNode == null){
//                LinkedNode<T> oldRoot = LinkedList.this.root;
//                LinkedList.this.root = insertNode;
//                insertNode.next = oldRoot;
//            }else{
//                LinkedNode<T> oldNext = this.preNode.next;
//                this.preNode.next = insertNode;
//                insertNode.next = oldNext;
//            }
//        }

        /**
         * 当前遍历节点后插入
         * @param t
         */
//        public void postInsert(T t){
//            LinkedNode<T> insertNode = new LinkedNode<>();
//            if(this.viewNode == null){
//                LinkedNode<T> oldRoot = LinkedList.this.root;
//                LinkedList.this.root = insertNode;
//                insertNode.next = oldRoot;
//            }else{
//                LinkedNode<T> oldNext = this.viewNode.next;
//                this.viewNode.next = insertNode;
//                insertNode.next = oldNext;
//            }
//        }

    }

    private static class LinkedNode<T>{
        private LinkedNode<T> next;
        private T data;
    }

}
