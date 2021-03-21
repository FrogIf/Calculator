package frog.calculator.compile.lexical;

import frog.calculator.compile.ICompileManager;
import frog.calculator.compile.lexical.exception.UnrecognizedTokenException;

/**
 * 常规词法解析器
 */
public class GeneralLexer implements ILexer {

    private final ITokenRepository repository;

    private final INamedTokenFactory namedTokenFactory;

    private final INumberTokenFactory numberTokenFactory;

    public GeneralLexer(ITokenRepository repository, ICompileManager manager){
        this.repository = repository;
        this.namedTokenFactory = manager.getNamedTokenFactory();
        this.numberTokenFactory = manager.getNumberTokenFactory();
    }

    @Override
    public IToken parse(IScanner scanner) throws UnrecognizedTokenException{
        char ch = skipBlank(scanner);

        IToken result = null;
        if(ch >= '0' && ch <= '9'){
            result = parseNumber(scanner);
        }else if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')){
            result = parseWord(scanner);
        }else{
            // 其它符号的解析, 只能是内置的
            result = this.repository.retrieve(scanner);
        }
        if(result == null){
            throw new UnrecognizedTokenException(ch, scanner.position());
        }

        // 跳过后空格
        if(scanner.isNotEnd()){
            skipBlank(scanner);
        }

        return result;
    }

    private char skipBlank(IScanner scanner){
        char ch = scanner.read();
        while(ch == ' ' && scanner.moveToNext()){
            ch = scanner.read();
        }
        return ch;
    }

    /**
     * 解析单词
     * 以字母开头, 符合如下正则: [a-zA-Z]+[a-zA-Z0-9_]*
     * 
     * 执行结束时, scanner会指向结尾 或者 未读字符的起始位置
     */
    private IToken parseWord(IScanner scanner){
        IToken t = this.repository.retrieve(scanner);

        // 贪婪策略, 继续读取后面的字符
        if(scanner.isNotEnd() && isNormalChar(scanner.read())) {
            StringBuilder word = new StringBuilder();
            if(t != null){
                word.append(t.word());
            }
            word.append(scanner.read());

            while(scanner.moveToNext()){
                char ch = scanner.read();
                if(!isNormalChar(ch)){
                    break;
                }
                word.append(ch);
            }
            this.namedTokenFactory.create(word.toString());
        }
        return t;
    }

    private final boolean isNormalChar(char ch){
        return (ch >= 'A' && ch <= 'Z') 
        || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') 
        || ch == '_';
    }

    /**
     * 解析数字
     * 数字开头, 符合如下正则: [0-9]+(\.{1}[0-9]*_{1}[0-9]+)?
     * <br/>
     * 执行结束时, scanner会指向结尾 或者 未读字符的起始位置
     */
    private IToken parseNumber(IScanner scanner){
        StringBuilder numberBuilder = new StringBuilder();
        numberBuilder.append(scanner.read());

        boolean hasDot = false; // 记录是否已经找到小数点
        while(scanner.moveToNext()){
            char ch = scanner.read();
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

        return numberBuilder.length() > 0 ? this.numberTokenFactory.create(numberBuilder.toString())  : null;
    }
    
}
