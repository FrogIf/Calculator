package frog.calculator.compile.lexical;

import frog.calculator.compile.IBuildContext;
import frog.calculator.compile.semantic.NumberExecutor;
import frog.calculator.compile.syntax.CloseSyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNodeBuilder;

public class NumberToken implements IToken {

    private final String numberStr;

    private final ISyntaxNodeBuilder nodeBuilder;

    public NumberToken(String number) {
        this.numberStr = number;
        this.nodeBuilder = new NumberNodeBuilder();
    }

    @Override
    public ISyntaxNodeBuilder getSyntaxBuilder() {
        return this.nodeBuilder;
    }

    @Override
    public String word() {
        return this.numberStr;
    }

    private class NumberNodeBuilder implements ISyntaxNodeBuilder {

        @Override
        public ISyntaxNode build(int order, IBuildContext context) {
            return new CloseSyntaxNode(NumberExecutor.getInstance(), NumberToken.this.numberStr);
        }

        @Override
        public String word() {
            return NumberToken.this.numberStr;
        }

    }  
    
}
