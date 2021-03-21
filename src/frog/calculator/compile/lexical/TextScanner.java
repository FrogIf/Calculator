package frog.calculator.compile.lexical;

/**
 * 字符文本扫描器
 */
public class TextScanner implements IScanner {

    private final char[] content;

    private int index = 0;

    public TextScanner(String content){
        this.content = content.toCharArray();
    }

    @Override
    public char read() {
        return content[index];
    }

    @Override
    public boolean isNotEnd() {
        return index < content.length;
    }

    @Override
    public boolean moveToNext() {
        index++;
        return index < content.length;
    }

    @Override
    public int position() {
        return this.index;
    }
    
}
