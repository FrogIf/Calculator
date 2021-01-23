package frog.calculator.compile.lexical;

import frog.calculator.compile.semantic.NumberExecutor;
import frog.calculator.compile.syntax.TerminalNode;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;

/**
 * 数字持有者token
 */
public class NumberToken implements IToken {

    private final String numberStr;

    private final ISyntaxNodeGenerator nodeBuilder;

    public NumberToken(String number) {
        this.numberStr = number;
        this.nodeBuilder = new NumberNodeGenerator();
    }

    @Override
    public ISyntaxNodeGenerator getSyntaxNodeGenerator() {
        return this.nodeBuilder;
    }

    @Override
    public String word() {
        return this.numberStr;
    }

    private class NumberNodeGenerator implements ISyntaxNodeGenerator {

        @Override
        public ISyntaxNode generate(int position) {
            return new TerminalNode(NumberExecutor.getInstance(), NumberToken.this.numberStr, position);
        }

        @Override
        public String word() {
            return NumberToken.this.numberStr;
        }

    }  
    
}
