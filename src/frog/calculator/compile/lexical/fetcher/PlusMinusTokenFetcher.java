package frog.calculator.compile.lexical.fetcher;

import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.lexical.ITokenFactory;

public class PlusMinusTokenFetcher implements ITokenFetcher {

    private final ITokenFactory plusMinusTokenFactory;

    public PlusMinusTokenFetcher(ITokenFactory plusMinusTokenFactory){
        this.plusMinusTokenFactory = plusMinusTokenFactory;
    }

    @Override
    public IToken fetch(IScanner scanner) {
        char ch = scanner.peek();
        int mark = 0;
        if(ch == '+' || ch == '-'){
            mark += (ch == '-' ? 1 : 0);
            scanner.take();
        }else{
            return null;
        }

        while(scanner.isNotEnd() && ((ch = scanner.peek()) == '+' || ch == '-' || ch == ' ')){
            mark += (ch == '-' ? 1 : 0);
            scanner.take();
        }

        
        return plusMinusTokenFactory.create(mark % 2 == 0 ? "+" : "-");
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean overridable() {
        return false;
    }
    
}
