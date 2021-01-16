package frog.calculator.compile;

import frog.calculator.compile.exception.CompileException;
import frog.calculator.compile.exception.UnrecognizedTokenException;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.collection.IList;
import frog.calculator.value.IValue;

public class SyntaxTreeBuilder {

    private final ILexer lexer;

    private final StartSyntaxNode startSyntaxNode = new StartSyntaxNode();

    public SyntaxTreeBuilder(ILexer lexer) {
        this.lexer = lexer;
    }

    public ISyntaxNode build(IScanner scanner) throws CompileException {
        ISyntaxNode root = startSyntaxNode;
        IBuildContext context = new GeneralBuildContext();
        int order = 0;
        while(scanner.hasNext()){
            IToken token = lexer.parse(scanner);
            if(token == null){
                throw new UnrecognizedTokenException(scanner.peek(), scanner.position());
            }
            ISyntaxNode node = token.getSyntaxBuilder().build(order, context);
            root = root.associate(node);
            order++;
        }
        return root;
    }

    private static class StartSyntaxNode implements ISyntaxNode {

        @Override
        public String word() {
            return null;
        }

        @Override
        public int priority() {
            return 0;
        }

        @Override
        public int position() {
            return 0;
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public ISyntaxNode associate(ISyntaxNode input) {
            return input;
        }

        @Override
        public boolean branchOff(ISyntaxNode child) {
            return false;
        }

        @Override
        public IList<ISyntaxNode> children() {
            return null;
        }

        @Override
        public IValue execute(IExecuteContext context) {
            return null;
        }

    }
    
}
