package io.github.frogif.calculator.compile.lexical.matcher;

import io.github.frogif.calculator.compile.lexical.IScanner;

public class WordMatcher implements IMatcher{

    @Override
    public String match(IScanner scanner) {
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
        return wordBuilder.toString();
    }

    private boolean isNormalChar(char ch){
        return (ch >= 'A' && ch <= 'Z')
                || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')
                || ch == '_';
    }
}
