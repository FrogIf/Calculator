package frog.calculator.compile.syntax;

import frog.calculator.compile.exception.CompileException;
import frog.calculator.compile.syntax.exception.SyntaxException;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.IScanner;
import frog.calculator.util.collection.Stack;

/**
 * 通用的语法树构建器
 */
public class GeneralSyntaxTreeBuilder implements ISyntaxTreeBuilder, IAssembler {

    /**
     * 词法解析器
     */
    private final ILexer lexer;

    public GeneralSyntaxTreeBuilder(ILexer lexer) {
        this.lexer = lexer;
    }

    public ISyntaxNode build(IScanner scanner) throws CompileException {
        int position = 0;
        ISyntaxNode root = lexer.parse(scanner).getSyntaxNodeGenerator().generate(position++);

        // 栈结构, 用于存储构建过程中, 激活的语法节点, 这些节点只会作为父节点
        Stack<ISyntaxNode> activeStack = new Stack<>();

        while(scanner.isNotEnd()){
            ISyntaxNode node = lexer.parse(scanner).getSyntaxNodeGenerator().generate(position);
            
            ISyntaxNode activeNode = null;
            boolean hasInsert = false;
            while(activeStack.size() > 0 && !hasInsert){
                activeNode = activeStack.pop();
                hasInsert = judgeParent(activeNode, node) == activeNode && activeNode.branchOff(node, this);
            }

            if(hasInsert){
                if(activeNode.isRightOpen()){
                    activeStack.push(activeNode);
                    if(node.isRightOpen()){
                        activeStack.push(node);
                    }
                }
            }else{
                root = this.associate(root, node);
                if(root == null){
                    throw new SyntaxException(node.word(), scanner.position(), 0);
                }
                if(node.isRightOpen() && root != node){
                    activeStack.push(node);
                }
            }
            position++;
        }
        
        return root;
    }

    @Override
    public ISyntaxNode associate(ISyntaxNode nodeA, ISyntaxNode nodeB) {
        ISyntaxNode parent = judgeParent(nodeA, nodeB);
        ISyntaxNode child = parent == nodeA ? nodeB : nodeA;
        return parent != null && parent.branchOff(child, this) ? parent : null;
    }

    /**
     * 根据两个节点的open状态, 优先级, 位置信息等, 判断哪一个作为父节点
     * @return 返回父节点, 如果无法判断, 返回null
     */
    private static ISyntaxNode judgeParent(ISyntaxNode nodeA, ISyntaxNode nodeB){
        // 判断左右
        ISyntaxNode left = nodeA;
        ISyntaxNode right = nodeB;
        if(nodeA.position() > nodeB.position()){
            left = nodeB;
            right = nodeA;
        }

        boolean rOpen = right.isLeftOpen();
        ISyntaxNode parent = right; // 正常情况下, 后出现的, 后执行
        if(left.isRightOpen() != rOpen){    // 两个open状态不相同
            if(!rOpen){  // right不是open
                parent = left;
            }
        }else if(rOpen){    // 两个node都处于open状态
            if(right.priority() > left.priority()){
                parent = left;
            }
        }else{  // 两个node都是close
            // 使失败
            parent = null;
        }

        return parent;
    }
    
}
