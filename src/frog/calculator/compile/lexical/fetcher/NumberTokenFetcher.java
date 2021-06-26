package frog.calculator.compile.lexical.fetcher;

import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.lexical.ITokenFactory;

public class NumberTokenFetcher implements ITokenFetcher {

    private final ITokenFactory tokenFactory;

    public NumberTokenFetcher(ITokenFactory tokenFactory){
        if(tokenFactory == null){
            throw new IllegalArgumentException("token factory is null for number token fetcher.");
        }
        this.tokenFactory = tokenFactory;
    }

    @Override
    public IToken fetch(IScanner scanner) {
        boolean hasDot = false; // 记录是否已经找到小数点
        boolean hasLoopMark = false; // 是否有无限循环标记
        StringBuilder numberBuilder = new StringBuilder();

        char ch;
        do {
            ch = scanner.peek();
            if(ch >= '0' && ch <= '9'){
                numberBuilder.append(ch);
            }else if(ch == '.' && !hasDot){
                hasDot = true;
                numberBuilder.append(ch);
            }else if(ch == '_' && hasDot && !hasLoopMark){
                hasLoopMark = true;
                numberBuilder.append(ch);
            }else{
                break;
            }
            scanner.take();
        }while(scanner.isNotEnd());

        return numberBuilder.length() > 0 ? this.tokenFactory.create(numberBuilder.toString())  : null;
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
