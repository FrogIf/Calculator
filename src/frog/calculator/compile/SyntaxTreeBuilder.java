package frog.calculator.compile;

import frog.calculator.compile.exception.BuildException;
import frog.calculator.compile.exception.UnrecognizedTokenException;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.collection.Stack;

public class SyntaxTreeBuilder implements ISyntaxTreeBuilder{

    private final ILexer lexer;

    public SyntaxTreeBuilder(ILexer lexer) {
        this.lexer = lexer;
    }

    public ISyntaxNode build(IScanner scanner) throws BuildException {
        IBuildContext context = new GeneralBuildContext(this);
        IToken token = lexer.parse(scanner);
        int order = 0;
        ISyntaxNode root = token.getSyntaxBuilder().build(order++, context);
        Stack<ISyntaxNode> activeStack = new Stack<>();
        while(scanner.hasNext()){
            token = lexer.parse(scanner);
            // if(token == null){
                // throw new UnrecognizedTokenException(scanner.peek(), scanner.position());
            // }

            ISyntaxNode node = token.getSyntaxBuilder().build(order, context);

            ISyntaxNode activeNode = null;
            boolean hasInsert = false;
            while(activeStack.size() > 0 && !hasInsert){
                activeNode = activeStack.pop();
                hasInsert = judgeParent(activeNode, node) == activeNode && activeNode.branchOff(node, context);
            }
            if(hasInsert){
                if(activeNode.isOpen()){
                    activeStack.push(activeNode);
                    if(node.isOpen()){
                        activeStack.push(node);
                    }
                }
            }else{
                root = this.associate(root, node, context);
                if(node.isOpen() && root != node){
                    activeStack.push(node);
                }
            }
            order++;
        }
        // 获取栈底
        return root;
    }

    @Override
    public ISyntaxNode associate(ISyntaxNode nodeA, ISyntaxNode nodeB, IBuildContext context) {
        ISyntaxNode parent = judgeParent(nodeA, nodeB);
        ISyntaxNode child = parent == nodeA ? nodeB : nodeA;
        return parent != null && parent.branchOff(child, context)? parent : null;
    }

    private static ISyntaxNode judgeParent(ISyntaxNode nodeA, ISyntaxNode nodeB){
        boolean open = nodeA.isOpen();
        ISyntaxNode parent = nodeA;
        if(nodeB.isOpen() != open){    // 两个open状态不相同
            if(!open){  // this不是open
                parent = nodeB;
            }
        }else if(open){    // 两个node都处于open状态
            int p = nodeA.priority() - nodeB.priority();
            /**
             * 1. 优先级相等, input位置靠后
             * 2. input优先级小
             */
            if((p == 0 && nodeA.position() < nodeB.position()) || p > 0){
                parent = nodeB;
            }
        }else{  // 两个node都是close
            // 使失败
            parent = null;
        }

        return parent;
    }
    
}
