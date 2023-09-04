package io.github.frogif.calculator.compile.lexical.matcher;

import io.github.frogif.calculator.compile.lexical.IScanner;

/**
 * 正负号匹配器
 * 对于连续的正负号, 进行压缩
 * 例如: ++-- -> +
 */
public class PlusMinusMatcher implements IMatcher{

    @Override
    public String match(IScanner scanner) {
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


        return mark % 2 == 0 ? "+" : "-";
    }
}
