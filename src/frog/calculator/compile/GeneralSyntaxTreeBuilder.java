package frog.calculator.compile;

import frog.calculator.compile.exception.CompileException;
import frog.calculator.compile.exception.SyntaxException;
import frog.calculator.compile.exception.UnrecognizedTokenException;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.collection.Stack;

public class GeneralSyntaxTreeBuilder implements ISyntaxTreeBuilder, IAssembler{

    private final ILexer lexer;

    public GeneralSyntaxTreeBuilder(ILexer lexer) {
        this.lexer = lexer;
    }

    public ISyntaxNode build(IScanner scanner) throws CompileException {
        IToken token = lexer.parse(scanner);
        int order = 0;
        ISyntaxNode root = token.getSyntaxNodeGenerator().generate(order++);
        Stack<ISyntaxNode> activeStack = new Stack<>();
        while(scanner.hasNext()){
            token = lexer.parse(scanner);
            if(token == null){
                throw new UnrecognizedTokenException(scanner.peek(), scanner.position());
            }

            ISyntaxNode node = token.getSyntaxNodeGenerator().generate(order);
            
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
            order++;
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
