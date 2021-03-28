package frog.calculator.micro;

import frog.calculator.compile.ICompileManager;
import frog.calculator.compile.lexical.INamedTokenFactory;
import frog.calculator.compile.lexical.INumberTokenFactory;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import frog.calculator.compile.syntax.TerminalNode;
import frog.calculator.micro.exec.impl.base.NumberExecutor;
import frog.calculator.micro.exec.impl.base.VariableExecutor;

public class MicroCompileManager implements ICompileManager {

    @Override
    public INamedTokenFactory getNamedTokenFactory() {
        return new NamedTokenFactory();
    }

    @Override
    public INumberTokenFactory getNumberTokenFactory() {
        return new NumberTokenFactory();
    }


    private static class NamedTokenFactory implements INamedTokenFactory {

        @Override
        public IToken create(String word) {
            return new NamingToken(word);
        }
    
        private static class NamingToken implements IToken {
    
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
                    return new TerminalNode(NamingToken.this.word, position, new VariableExecutor());
                }
        
            }
        }
        
    }

    public static class NumberTokenFactory implements INumberTokenFactory {

        @Override
        public IToken create(String word) {
            return new NumberToken(word);
        }
        
    }
    
    private static class NumberToken implements IToken {
    
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
                return new TerminalNode(NumberToken.this.numberStr, position, new NumberExecutor());
            }
    
            @Override
            public String word() {
                return NumberToken.this.numberStr;
            }
        } 
    }

}