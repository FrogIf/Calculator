package frog.calculator.compile.lexical;

/**
 * 常规词法解析器
 */
public class GeneralLexer implements ILexer {

    private final ITokenRepository repository;

    public GeneralLexer(ITokenRepository repository){
        this.repository = repository;
    }

    @Override
    public IToken parse(IScanner scanner) {
        char ch = scanner.current();
        if(ch >= '0' && ch <= '9'){
            return parseNumber(scanner);
        }else if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')){
            return parseWord(scanner);
        }else{
            // 其它符号的解析, 只能是内置的
            return this.repository.retrieve(scanner);
        }
    }

    /**
     * 解析单词
     * 以字母开头, 符合如下正则: [a-zA-Z]+[a-zA-Z0-9_]*
     */
    private IToken parseWord(IScanner scanner){
        StringBuilder word = new StringBuilder(scanner.current());
        char ch;
        while(scanner.hasNext() 
            && (
                ((ch = scanner.next()) >= 'A' && ch <= 'Z') 
                || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') 
                || ch == '_'
                )
            ){
                word.append(ch);
        }
        String text = word.toString();
        IToken t = this.repository.find(text);
        if(t == null){
            t = new NamingToken(text);
        }
        return t;
    }

    /**
     * 解析数字
     * 数字开头, 符合如下正则: [0-9]+(\.{1}[0-9]*_{1}[0-9]+)?
     */
    private IToken parseNumber(IScanner scanner){
        StringBuilder numberBuilder = new StringBuilder(scanner.current());

        boolean hasDot = false; // 记录是否已经找到小数点
        while(scanner.hasNext()){
            char ch = scanner.next();
            if(ch >= '0' && ch <= '9'){
                numberBuilder.append(ch);
            }else if(ch == '.' && !hasDot){
                hasDot = true;
                numberBuilder.append(ch);
            }else if(ch == '_' && hasDot){
                numberBuilder.append(ch);
            }else{
                break;
            }
        }

        return numberBuilder.length() > 0 ? new NumberToken(numberBuilder.toString())  : null;
    }
    
}
