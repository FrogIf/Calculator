package frog.calculator.compile.lexical;

import frog.calculator.compile.IBuildContext;
import frog.calculator.compile.syntax.CloseSyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNodeBuilder;

public class NamingToken implements IToken {

    private final String word;

    private final ISyntaxNodeBuilder builder;

    public NamingToken(String word) {
        this.word = word;
        this.builder = new NamingTokenBuilder();
    }

    @Override
    public ISyntaxNodeBuilder getSyntaxBuilder() {
        return this.builder;
    }

    @Override
    public String word() {
        return this.word;
    }

    private class NamingTokenBuilder implements ISyntaxNodeBuilder {

        @Override
        public String word() {
            return NamingToken.this.word;
        }

        @Override
        public ISyntaxNode build(int order, IBuildContext context) {
            //TODO naming token exectuor
            return new CloseSyntaxNode(null, NamingToken.this.word);
        }

    }
    
}
