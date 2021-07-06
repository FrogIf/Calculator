package frog.test.util.tree;

import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Stack;

public class SyntaxTreeDisplayUtil {

    /*
     * 直接报错: 1+2+3+4*5+6*(7+8)
     * 结构错乱: 3+4*5+6*7
     */

    public static <T> String drawTree(T treeRoot, ITreeNodeReader<T> reader){
        AnchorNode root = new AnchorNode(fixChildCount(treeRoot, reader)).setLabel(reader.label(treeRoot)).setSource(treeRoot);

        Stack<Integer> viewCountStack = new Stack<>();  // 记录遍历的子节点个数
        ArrayList<int[]> maxColIndexEachRow = new ArrayList<>(); // 记录每层的最大索引位置, 之所以使用数组, 是为了方便修改
        int rowIndex = 0;

        // 深度优先, 确定节点的相对位置
        AnchorNode cursor = root;
        viewCountStack.push(0);
        maxColIndexEachRow.add(new int[]{-1});
        while(cursor != null){
            if(cursor.children.length > viewCountStack.top()){
                Integer index = viewCountStack.pop();
                viewCountStack.push(index + 1);
                if(index == 0){ // 说明children还是空的
                    IList<T> sourceChildren = reader.children((T)cursor.source);
                    int maxColIndex = maxColIndexEachRow.get(rowIndex)[0];
                    int cs = sourceChildren.size();
                    int midIndex = -1;
                    if((cs & 1) == 0){  // 如果是偶数个子节点, 增加一个空白中间节点
                        midIndex = cs >> 1;
                    }
                    Iterator<T> itr = sourceChildren.iterator();
                    int i = 0;
                    while(itr.hasNext()){
                        if(i == midIndex){
                            cursor.setChild(i, new AnchorNode(0).setParent(cursor).setCol(maxColIndex + i));    // 空的占位节点
                            i++;
                        }
                        T node = itr.next();
                        if(node == null){
                            cursor.setChild(i, new AnchorNode(0).setCol(maxColIndex + i).setLabel("").setParent(cursor).setSource(new Object()));
                        }else{
                            cursor.setChild(i, new AnchorNode(fixChildCount(node, reader)).setCol(maxColIndex + i).setLabel(reader.label(node)).setParent(cursor).setSource(node));
                        }
                        i++;
                    }
                }
                cursor = cursor.children[index];    // 向下一层移动
                rowIndex++;
                if(maxColIndexEachRow.size() == rowIndex){
                    maxColIndexEachRow.add(new int[]{cursor.parent.col});
                }
                viewCountStack.push(0);
            }else{
                // 如果是叶子节点, 或者所有子节点都已经遍历完成
                if(cursor.children.length > 0){
                    cursor.col = (cursor.children[0].col + cursor.children[cursor.children.length - 1].col) >> 1;
                    maxColIndexEachRow.get(rowIndex)[0] = cursor.col;
                }else{
                    int[] maxColInfo = maxColIndexEachRow.get(rowIndex);
                    maxColInfo[0] = maxColInfo[0] + 1;
                    cursor.setCol(maxColInfo[0]);
                }
                cursor = cursor.parent; // 向上一层移动
                rowIndex--;
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
                if(cursor.source != null){  // 说明不是占位节点
                    int w = colWidth[cursor.col];
                    if(w < cursor.label.length()){
                        colWidth[cursor.col] = cursor.label.length() + 1;   // 宽度扩充1, 防止同层相邻节点混在一起
                    }
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
                    int width = colWidth[n.col] - (n.col == 0 ? 0 : colWidth[n.col - 1]);
                    int leftFillNum = (width - 1) >> 1;
                    int rightFillNum = width - 1 - leftFillNum;
                    int distance = colWidth[n.col] - (i == 0 ? 0 : colWidth[layer[i - 1].col]);
                    int blank = distance - width;
                    if(n.source == null){   // 占位节点
                        if(n.col == parentCol){
                            canvas.append(StringUtils.bothFill('─', leftFillNum + blank, "┴", '─', rightFillNum));
                        }else{
                            canvas.append(StringUtils.repeat("─", leftFillNum + blank + rightFillNum));
                        }
                    }else if(n.col < parentCol){   // 该节点在父节点的左边
                        if(pos == -1){  // 最左
                            canvas.append(StringUtils.bothFill(' ', leftFillNum + blank, "┌", '─', rightFillNum));
                        }else if(pos == 1){ // 最右, 这种情况不会出现, 除非是bug
                            canvas.append(StringUtils.repeat("/", blank + width));
                        }else{  // 中间
                            canvas.append(StringUtils.bothFill('─', blank + leftFillNum, "┬", '─', rightFillNum));
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
                        }
                        if(pos == -1){  // 最左
                            canvas.append(StringUtils.repeat("\\", blank + width - hasFillLen));
                        }else if(pos == 1){ // 最右
                            canvas.append(StringUtils.bothFill('─', blank + leftFillNum - hasFillLen, "┐", ' ', rightFillNum));
                        }else{  // 中间
                            canvas.append(StringUtils.bothFill('─', blank + leftFillNum - hasFillLen, "┬", '─', rightFillNum));
                        }
                    }else{ // 该节点在父节点的正下方
                        if(n.parent.children.length > 1){
                            if(pos == -1){  // 最左孩子
                                canvas.append(StringUtils.bothFill(' ', blank + leftFillNum, "├", '─', rightFillNum));
                            }else if(pos == 1){ // 最右孩子
                                canvas.append(StringUtils.bothFill('─', blank + leftFillNum, "┤", ' ', rightFillNum));
                            }else{
                                canvas.append(StringUtils.bothFill('─', blank + leftFillNum, "┼", '─', rightFillNum));
                            }
                        }else{  // 父节点只有这一个子节点
                            canvas.append(StringUtils.bothFill(' ', blank + leftFillNum, "│", ' ', rightFillNum));
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

                int width = colWidth[n.col] - (n.col == 0 ? 0 : colWidth[n.col - 1]);
                int distance = colWidth[n.col] - (i == 0 ? 0 : colWidth[layer[i - 1].col]);
                String exceptCurrentBlank = StringUtils.leftFill("", ' ', distance - width);
                String fillBlank = StringUtils.leftFill(exceptCurrentBlank, ' ', width);
                if(n.source == null){
                    canvas.append(fillBlank);
                }else{
                    canvas.append(exceptCurrentBlank).append(fixLabel(n.label, width));
                }
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

    private static <T> int fixChildCount(T parent, ITreeNodeReader<T> reader){
        IList<T> children = reader.children(parent);
        int childrenCount = children == null ? 0 : children.size();
        if(childrenCount > 0 && childrenCount % 2 == 0){
            return childrenCount + 1;
        }else{
            return childrenCount;
        }
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
