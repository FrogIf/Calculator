package sch.frog.calculator.compile.lexical.fetcher;

import sch.frog.calculator.compile.lexical.IScanner;
import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.lexical.ITokenRepository;

public class InnerTokenFetcher implements ITokenFetcher {

    private final ITokenRepository tokenRepository;

    public InnerTokenFetcher(ITokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }

    @Override
    public IToken fetch(IScanner scanner) {
        return this.tokenRepository.retrieve(scanner);
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean overridable() {
        return true;
    }
    
}
