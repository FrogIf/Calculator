package frog.calculator.compile.lexical;

import frog.calculator.compile.syntax.TerminalNode;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;

public class NamingToken implements IToken {

    private final String word;

    private final ISyntaxNodeGenerator builder;

    public NamingToken(String word) {
        this.word = word;
        this.builder = new NamingNodeGenerator();
    }

    @Override
    public ISyntaxNodeGenerator getSyntaxNodeGenerator() {
        return this.builder;
    }

    @Override
    public String word() {
        return this.word;
    }

    private class NamingNodeGenerator implements ISyntaxNodeGenerator {

        @Override
        public String word() {
            return NamingToken.this.word;
        }

        @Override
        public ISyntaxNode generate(int position) {
            // TODO naming token exectuor
            return new TerminalNode(null, NamingToken.this.word, position);
        }

    }
    
}
