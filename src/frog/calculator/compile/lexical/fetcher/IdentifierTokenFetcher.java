package frog.calculator.compile.lexical.fetcher;

import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.lexical.ITokenFactory;

public class IdentifierTokenFetcher implements ITokenFetcher {

    private final ITokenFactory identifierTokenFactory;

    public IdentifierTokenFetcher(ITokenFactory tokenFactory){
        this.identifierTokenFactory = tokenFactory;
    }

    @Override
    public IToken fetch(IScanner scanner) {
        StringBuilder wordBuilder = new StringBuilder();
        char ch = scanner.peek();
        if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == '_'){
            wordBuilder.append(ch);
            scanner.take();
        }else{
            return null;
        }
        while(scanner.isNotEnd() && isNormalChar(ch = scanner.peek())){
            wordBuilder.append(ch);
            scanner.take();
        }
        return wordBuilder.length() == 0 ? null : this.identifierTokenFactory.create(wordBuilder.toString());
    }

    private final boolean isNormalChar(char ch){
        return (ch >= 'A' && ch <= 'Z') 
        || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') 
        || ch == '_';
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public boolean overridable() {
        return true;
    }
    
}
