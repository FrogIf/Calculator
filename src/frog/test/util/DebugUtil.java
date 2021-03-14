package frog.test.util;

import java.util.Random;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

/**
 * 调试工具类
 */
public class DebugUtil {

    private DebugUtil(){
        // do nothing
    }

    /**
     * 根据语法树的根节点, 获取该语法树的字符串表现形式
     */
    public static String getSyntaxTree(ISyntaxNode tree){
        StringBuilder sb = new StringBuilder();
        sb.append(tree.word()).append('\n');
        buildTree(tree, 1, sb);
        return sb.toString();
    }

    private static void buildTree(ISyntaxNode tree, int level, StringBuilder sb){
        IList<ISyntaxNode> children = tree.children();
        // 深度优先
        if(children != null && children.size() > 0){
            Iterator<ISyntaxNode> itr = children.iterator();
            while(itr.hasNext()){
                ISyntaxNode next = itr.next();
                if(next != null){
                    sb.append(StringUtils.leftFill(next.word(), ' ', level * 4)).append('\n');
                    buildTree(next, level + 1, sb);
                }
            }
        }
    }

    public static String randomString(){
        Random r = new Random();
        int len = r.nextInt(20) + 1;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < len; i++){
            int pos = r.nextInt(52);
            int a = pos / 27 * ('a' - 'A') + 'A' + pos % 26;
            sb.append((char)a);
        }
        return sb.toString();
    }
    
}
