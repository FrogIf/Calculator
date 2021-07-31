package sch.frog.calculator.compile.lexical.fetcher;

import sch.frog.calculator.compile.lexical.IScanner;
import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.lexical.ITokenFactory;

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

        while(scanner.isNotEnd() && ((ch = scanner.peek()) == '+' || ch == '-')){
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
