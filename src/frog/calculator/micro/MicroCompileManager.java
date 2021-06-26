package frog.calculator.micro;

import frog.calculator.compile.lexical.ITokenFactory;
import frog.calculator.compile.lexical.ITokenRepository;
import frog.calculator.compile.lexical.TokenRepository;
import frog.calculator.compile.lexical.exception.DuplicateTokenException;
import frog.calculator.compile.lexical.fetcher.ITokenFetcher;
import frog.calculator.compile.lexical.fetcher.IdentifierTokenFetcher;
import frog.calculator.compile.lexical.fetcher.InnerTokenFetcher;
import frog.calculator.compile.lexical.fetcher.NumberTokenFetcher;
import frog.calculator.compile.lexical.fetcher.PlusMinusTokenFetcher;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import frog.calculator.compile.syntax.TerminalNode;
import frog.calculator.exception.CalculatorError;
import frog.calculator.micro.exec.impl.base.NumberExecutor;
import frog.calculator.micro.exec.impl.base.VariableExecutor;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public class MicroCompileManager {

    private static class IdentifierTokenFactory implements ITokenFactory {

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

    public static class NumberTokenFactory implements ITokenFactory {

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

    public IList<ITokenFetcher> getTokenFetchers() {
        IList<ITokenFetcher> fetchers = new ArrayList<>();

        // 数字fetcher
        fetchers.add(new NumberTokenFetcher(new NumberTokenFactory()));
        // 标识符fetcher
        fetchers.add(new IdentifierTokenFetcher(new IdentifierTokenFactory()));

        ITokenRepository tokenRepository = new TokenRepository();
        IToken[] tokens = MicroTokenHolder.getTokens();
        try {
            for(IToken t : tokens){
                tokenRepository.insert(t);
            }
        } catch (DuplicateTokenException e) {
            throw new CalculatorError(e.getMessage());
        }
        // 内置符号fetcher
        fetchers.add(new InnerTokenFetcher(tokenRepository));

        // 正负号fetcher
        fetchers.add(new PlusMinusTokenFetcher(new ITokenFactory(){

            @Override
            public IToken create(String word) {
                if("+".equals(word)){
                    return MicroTokenHolder.ADD_TOKEN;
                }else if("-".equals(word)){
                    return MicroTokenHolder.SUB_TOKEN;
                }else{
                    throw new IllegalArgumentException("can't recognized word : " + word);
                }
            }
            
        }));

        return fetchers;
    }

}