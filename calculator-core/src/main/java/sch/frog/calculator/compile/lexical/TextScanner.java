package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.ReadOutOfBoundsException;

/**
 * 字符文本扫描器
 */
public class TextScanner implements IScanner {

    private final String content;

    private int index = 0;

    private int mark = index;

    public TextScanner(String content){
        this.content = content;
    }

    @Override
    public char peek() {
        if(index < content.length()){
            return content.charAt(index);
        }else{
            throw new ReadOutOfBoundsException();
        }
    }

    @Override
    public boolean isNotEnd() {
        return index < content.length();
    }

    @Override
    public char take() {
        char ch = content.charAt(index);
        index++;
        if(index > content.length()){
            throw new ReadOutOfBoundsException();
        }
        return ch;
    }

    @Override
    public int position() {
        return this.index;
    }

    @Override
    public PointerSnapshot snapshot() {
        return new PointerSnapshot(this.index);
    }

    @Override
    public void applySnapshot(PointerSnapshot snapshot) {
        this.index = snapshot.getPointer();
    }
    
}
