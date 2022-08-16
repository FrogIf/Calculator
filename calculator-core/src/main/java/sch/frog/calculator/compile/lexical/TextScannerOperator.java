package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.ReadOutOfBoundsException;

/**
 * 字符文本扫描器
 */
public class TextScannerOperator implements IScannerOperator {

    private final char[] content;

    private int index = 0;

    private int mark = index;

    public TextScannerOperator(String content){
        this.content = content.toCharArray();
    }

    @Override
    public char peek() {
        if(index < content.length){
            return content[index];
        }else{
            throw new ReadOutOfBoundsException();
        }
    }

    @Override
    public boolean isNotEnd() {
        return index < content.length;
    }

    @Override
    public void take() {
        index++;
        if(index > content.length){
            throw new ReadOutOfBoundsException();
        }
    }

    @Override
    public int position() {
        return this.index;
    }

    @Override
    public void moveToMark() {
        index = mark;
    }

    @Override
    public void markTo(int offset) {
        mark = mark + offset;
    }
    
}
