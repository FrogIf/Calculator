package sch.frog.calculator.micro;

import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.lexical.ITokenRepository;
import sch.frog.calculator.compile.lexical.TokenRepository;
import sch.frog.calculator.compile.lexical.exception.DuplicateTokenException;
import sch.frog.calculator.compile.lexical.fetcher.ITokenFetcher;
import sch.frog.calculator.compile.lexical.fetcher.IdentifierTokenFetcher;
import sch.frog.calculator.compile.lexical.fetcher.InnerTokenFetcher;
import sch.frog.calculator.compile.lexical.fetcher.NumberTokenFetcher;
import sch.frog.calculator.compile.lexical.fetcher.PlusMinusTokenFetcher;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import sch.frog.calculator.compile.syntax.UndeducibleNode;
import sch.frog.calculator.exception.CalculatorError;
import sch.frog.calculator.micro.exec.impl.base.NumberExecutor;
import sch.frog.calculator.micro.exec.impl.base.VariableExecutor;
import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.compile.lexical.ITokenFactory;

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
                    return new UndeducibleNode(NamingToken.this.word, position, new VariableExecutor());
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
                return new UndeducibleNode(NumberToken.this.numberStr, position, new NumberExecutor());
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