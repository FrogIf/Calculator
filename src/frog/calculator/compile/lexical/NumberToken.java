package frog.calculator.compile.lexical;

import frog.calculator.compile.semantic.NumberExecutor;
import frog.calculator.compile.syntax.CloseSyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNodeBuilder;
import frog.calculator.compile.syntax.ISyntaxTreeContext;

public class NumberToken implements IToken {

    private final String numberStr;

    public NumberToken(String number) {
        this.numberStr = number;
    }

    @Override
    public ISyntaxNodeBuilder getSyntaxBuilder() {
        return new NumberNodeBuilder();
    }

    @Override
    public String word() {
        return this.numberStr;
    }

    private class NumberNodeBuilder implements ISyntaxNodeBuilder {

        @Override
        public ISyntaxNode build(int order, ISyntaxTreeContext context) {
            return new CloseSyntaxNode(NumberExecutor.getInstance(), NumberToken.this.numberStr);
        }

    }  
    
}
