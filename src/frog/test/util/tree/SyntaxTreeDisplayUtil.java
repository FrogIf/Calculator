package frog.test.util.tree;

import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Stack;

public class SyntaxTreeDisplayUtil {

    /**
     * 根据树结构, 拼装可视化的树
     * @param <T> 树节点类型
     * @param treeRoot 树的根节点
     * @param reader 树节点读取器, 提供读取该树根节点以及文本
     * @return 可视化树文本
     */
    public static <T> String drawTree(T treeRoot, ITreeNodeReader<T> reader){
        AnchorNode root = new AnchorNode(fetchChildCount(treeRoot, reader)).setLabel(reader.label(treeRoot)).setSource(treeRoot);
        root.col = root.children.length >> 1;

        Stack<Integer> viewCountStack = new Stack<>();  // 记录遍历的子节点个数
        ArrayList<int[]> maxColIndexEachRow = new ArrayList<>(); // 记录每层的最大索引值, 之所以使用数组泛型, 是为了方便修改
        int currentRowIndex = 0;

        // 深度优先, 确定节点的相对位置
        AnchorNode cursor = root;
        viewCountStack.push(0);
        maxColIndexEachRow.add(new int[]{ root.col });
        while(cursor != null){
            if(cursor.children.length > viewCountStack.top()){
                int childCount = viewCountStack.pop();
                viewCountStack.push(childCount + 1);
                if(childCount == 0){ // 说明children还是空的, 初始化子节点
                    int currentRowMaxCol = 0;
                    int nextRowIndex = currentRowIndex + 1;
                    if(nextRowIndex < maxColIndexEachRow.size()){
                        currentRowMaxCol = maxColIndexEachRow.get(nextRowIndex)[0];
                    }
                    @SuppressWarnings("unchecked")
                    IList<T> sourceChildren = reader.children((T)cursor.source);
                    int col = cursor.col - (cursor.children.length >> 1);
                    if(col < 0){ col = 0; }
                    if(currentRowMaxCol >= col){
                        col = currentRowMaxCol + 1;
                    }
                    int skipColIndex = col + (cursor.children.length == 2 ? 1 : -1);
                    Iterator<T> itr = sourceChildren.iterator();
                    int i = 0;
                    while(itr.hasNext()){
                        if(skipColIndex == col){
                            col++;
                        }
                        T node = itr.next();
                        if(node == null){
                            cursor.setChild(i, new AnchorNode(0).setLabel("").setParent(cursor).setCol(col));
                        }else{
                            cursor.setChild(i, new AnchorNode(fetchChildCount(node, reader))
                                    .setLabel(reader.label(node)).setParent(cursor).setSource(node).setCol(col)
                                );
                        }
                        i++;
                        col++;
                    }
                    if(maxColIndexEachRow.size() == nextRowIndex){
                        maxColIndexEachRow.add(new int[]{ col - 1 });
                    }else{
                        maxColIndexEachRow.get(nextRowIndex)[0] = col - 1;
                    }
                }
                cursor = cursor.children[childCount];    // 向下一层移动
                currentRowIndex++;
                viewCountStack.push(0);
            }else{
                if(cursor.children.length > 0){ // 所有子节点都已经遍历完成, 重新计算父节点的位置
                    int[] maxColInfo = maxColIndexEachRow.get(currentRowIndex);
                    int col = (cursor.children[0].col + cursor.children[cursor.children.length - 1].col) >> 1;
                    if(col > cursor.col){
                        int offset = col - cursor.col;
                        cursor.col = col;
                        if(cursor.parent != null){
                            AnchorNode[] brothers = cursor.parent.children;
                            int len = brothers.length;
                            boolean startMove = false;
                            for(int i = 0; i < len;i++){
                                AnchorNode bro = brothers[i];
                                if(startMove){
                                    bro.col = bro.col + offset;
                                }else{
                                    startMove = !startMove && bro == cursor;
                                }
                            }
                            maxColInfo[0] = brothers[len - 1].col;
                        }else{
                            maxColInfo[0] = col;
                        }
                    }
                }
                cursor = cursor.parent; // 向上一层移动
                currentRowIndex--;
                viewCountStack.pop();
            }
        }

        int sumMaxColIndex = 0;
        Iterator<int[]> itr = maxColIndexEachRow.iterator();
        while(itr.hasNext()){
            int maxColIndex = itr.next()[0];
            if(sumMaxColIndex < maxColIndex){
                sumMaxColIndex = maxColIndex;
            }
        }
        // 深度优先, 确定每列的宽度
        int[] colWidth = new int[sumMaxColIndex + 1];
        viewCountStack.clear();
        cursor = root;
        viewCountStack.push(0);
        while(cursor != null){
            if(cursor.children.length > viewCountStack.top()){
                int index = viewCountStack.pop();
                viewCountStack.push(index + 1);
                cursor = cursor.children[index];
                viewCountStack.push(0);
            }else{
                int w = colWidth[cursor.col];
                if(w < cursor.label.length()){
                    colWidth[cursor.col] = cursor.label.length() + 1;   // 宽度扩充1, 防止同层相邻节点混在一起
                }
                cursor = cursor.parent;
                viewCountStack.pop();
            }
        }

        for(int i = 1; i < colWidth.length; i++){
            colWidth[i] = colWidth[i] + colWidth[i - 1];
        }

        // 广度优先, 绘制树
        StringBuilder canvas = new StringBuilder();
        AnchorNode[] layer = new AnchorNode[]{ root };
        AnchorNode[] nextLayer;
        while(layer.length > 0){
            // // 绘制当前层与上层之间的连线, 初始化下一层的容器
            int nextLayerLen = 0;
            AnchorNode n;
            for(int i = 0; i < layer.length; i++){
                n = layer[i];
                if(n.parent != null){
                    int parentCol = n.parent.col;   // 父节点所在列
                    int pos = judgeNodePosInChildren(n, n.parent);
                    int labelWidth = colWidth[n.col] - (n.col == 0 ? 0 : colWidth[n.col - 1]);
                    int labelLeftFill = (labelWidth - 1) >> 1;
                    int labelRightFill = labelWidth - 1 - labelLeftFill;
                    int unfill = colWidth[n.col] - (i == 0 ? 0 : colWidth[layer[i - 1].col]);
                    int blank = unfill - labelWidth;
                    if(n.col < parentCol){   // 该节点在父节点的左边
                        if(pos == -1){  // 最左
                            canvas.append(StringUtils.bothFill(' ', labelLeftFill + blank, "┌", '─', labelRightFill));
                        }else if(pos == 1){ // 最右, 这种情况不会出现, 除非是bug
                            canvas.append(StringUtils.repeat("/", blank + labelWidth));
                        }else{  // 中间
                            canvas.append(StringUtils.bothFill('─', blank + labelLeftFill, "┬", '─', labelRightFill));
                        }
                    }else if(n.col > parentCol){ // 该节点在父节点的右边
                        AnchorNode preBrother;
                        if(i == 0){
                            preBrother = null;
                        }else{
                            preBrother = layer[i - 1];
                            if(preBrother.parent != n.parent){
                                preBrother = null;
                            }
                        }
                        String preStr = "";
                        int hasFillLen = 0;
                        if(preBrother != null && preBrother.col < parentCol){
                            int fixFill = colWidth[parentCol - 1] - colWidth[preBrother.col];
                            int fixWidth = colWidth[parentCol] - colWidth[parentCol - 1];
                            int fixLeftFillNum = (fixWidth - 1) >> 1;
                            int fixRightFillNum = fixWidth - 1 - fixLeftFillNum;
                            preStr = StringUtils.bothFill('─', fixFill + fixLeftFillNum, "┴", '─', fixRightFillNum);
                            hasFillLen = preStr.length();
                            canvas.append(preStr);
                        }
                        if(pos == -1){  // 最左
                            canvas.append(StringUtils.repeat("\\", blank + labelWidth - hasFillLen));
                        }else if(pos == 1){ // 最右
                            canvas.append(StringUtils.bothFill('─', blank + labelLeftFill - hasFillLen, "┐", ' ', labelRightFill));
                        }else{  // 中间
                            canvas.append(StringUtils.bothFill('─', blank + labelLeftFill - hasFillLen, "┬", '─', labelRightFill));
                        }
                    }else{ // 该节点在父节点的正下方
                        if(n.parent.children.length > 1){
                            if(pos == -1){  // 最左孩子
                                canvas.append(StringUtils.bothFill(' ', blank + labelLeftFill, "├", '─', labelRightFill));
                            }else if(pos == 1){ // 最右孩子
                                canvas.append(StringUtils.bothFill('─', blank + labelLeftFill, "┤", ' ', labelRightFill));
                            }else{
                                canvas.append(StringUtils.bothFill('─', blank + labelLeftFill, "┼", '─', labelRightFill));
                            }
                        }else{  // 父节点只有这一个子节点
                            canvas.append(StringUtils.bothFill(' ', blank + labelLeftFill, "│", ' ', labelRightFill));
                        }
                    }
                }
                nextLayerLen += n.children.length;
            }
            canvas.append('\n');
            nextLayer = new AnchorNode[nextLayerLen];
            
            int nextLayerIndex = 0;

            for(int i = 0; i < layer.length; i++){
                n = layer[i];
                for(int j = 0; j < n.children.length; j++){
                    nextLayer[nextLayerIndex++] = n.children[j];
                }

                int labelWidth = colWidth[n.col] - (n.col == 0 ? 0 : colWidth[n.col - 1]);
                int unfill = colWidth[n.col] - (i == 0 ? 0 : colWidth[layer[i - 1].col]);
                String blank = StringUtils.leftFill("", ' ', unfill - labelWidth);
                canvas.append(blank).append(fixLabel(n.label, labelWidth));
            }

            layer = nextLayer;
            canvas.append('\n');
        }


        return canvas.toString();
    }

    /**
     * 判断一个节点在所有兄弟节点中的位置
     * @param selfNode
     * @param parent
     * @return 0 - 中间节点, -1 - 最左节点; 1 - 最右节点; 如果parent只有这一个节点, 则返回0
     */
    private static int judgeNodePosInChildren(AnchorNode selfNode, AnchorNode parent){
        if(parent.children.length > 1){
            if(parent.children[0] == selfNode){
                return -1;
            }else if(parent.children[parent.children.length - 1] == selfNode){
                return 1;
            }
        }
        return 0;
    }

    private static String fixLabel(String label, int width){
        if(label.length() >= width){
            return label;
        }else{
            int blank = width - label.length();
            int left = blank >> 1;
            int right = blank - left;
            return StringUtils.rightFill(StringUtils.leftFill(label, ' ', left), ' ', right);
        }
    }

    private static <T> int fetchChildCount(T parent, ITreeNodeReader<T> reader){
        IList<T> children = reader.children(parent);
        return children == null ? 0 : children.size();
    }

    private static class AnchorNode {

        private static final AnchorNode[] EMPTY_CHILDREN = new AnchorNode[0];

        private AnchorNode parent;

        private Object source;

        // 列索引
        private int col = -1;
    
        // 节点名
        private String label;
    
        // 子节点
        private AnchorNode[] children = EMPTY_CHILDREN;

        private AnchorNode(int childrenSize){
            if(childrenSize > 0){
                children = new AnchorNode[childrenSize];
            }
        }

        public AnchorNode setParent(AnchorNode parent){
            this.parent = parent;
            return this;
        }

        public AnchorNode setSource(Object source){
            this.source = source;
            return this;
        }

        public AnchorNode setLabel(String label){
            this.label = label;
            return this;
        }

        public AnchorNode setCol(int col){
            this.col = col;
            return this;
        }

        public AnchorNode setChild(int index, AnchorNode node){
            children[index] = node;
            return this;
        }

        @Override
        public String toString(){
            return this.label;
        }
    
    }

    public interface ITreeNodeReader<T>{

        String label(T node);

        IList<T> children(T parent);
    }
    
}
