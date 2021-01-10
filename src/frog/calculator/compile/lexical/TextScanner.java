package frog.calculator.compile.lexical;

public class TextScanner implements IScanner {

    private final char[] content;

    private int index = 0;

    public TextScanner(String content){
        this.content = content.toCharArray();
    }

    @Override
    public char current() {
        return content[index];
    }

    @Override
    public boolean hasNext() {
        return index < content.length - 1;
    }

    @Override
    public char next() {
        return content[++index];
    }
    
}
