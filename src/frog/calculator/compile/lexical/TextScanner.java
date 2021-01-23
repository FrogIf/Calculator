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
    public char peek() {
        return content[index];
    }

    @Override
    public boolean hasNext() {
        return index < content.length;
    }

    @Override
    public char read() {
        return content[index++];
    }

    @Override
    public int position() {
        return this.index;
    }
    
}
