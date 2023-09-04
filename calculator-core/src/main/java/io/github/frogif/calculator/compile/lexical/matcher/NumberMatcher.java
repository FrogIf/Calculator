package io.github.frogif.calculator.compile.lexical.matcher;

import io.github.frogif.calculator.compile.lexical.IScanner;

/**
 * 数字匹配器
 * 支持循环节: 1.234_5 = 1.2345555555555555555555555555...
 */
public class NumberMatcher implements IMatcher{

    @Override
    public String match(IScanner scanner) {
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

        return numberBuilder.toString();
    }

}
