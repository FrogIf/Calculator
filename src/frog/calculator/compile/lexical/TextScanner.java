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
    
}
