package frog.test;

import java.util.Scanner;

import frog.calculator.compile.SyntaxTreeBuilder;
import frog.calculator.compile.exception.CompileException;
import frog.calculator.compile.lexical.GeneralLexer;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.lexical.ITokenRepository;
import frog.calculator.compile.lexical.TextScanner;
import frog.calculator.compile.lexical.TokenRepository;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.config.MathTokenHolder;
import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class SyntaxTreeTest {

    public static void main(String[] args) throws CompileException {
        Scanner sc = new Scanner(System.in);

        MathTokenHolder holder = new MathTokenHolder();
        ITokenRepository tokenRespository = new TokenRepository();
        for(IToken t : holder.getTokens()){
            tokenRespository.insert(t);
        }
        ILexer lexer = new GeneralLexer(tokenRespository);
        SyntaxTreeBuilder builder = new SyntaxTreeBuilder(lexer);

        while(sc.hasNext()){
            String expression = sc.nextLine();
            if("exit".equals(expression)) {
                System.out.println("bye !");
                break;
            }

            TextScanner ts = new TextScanner(expression);
            try{
                ISyntaxNode tree = builder.build(ts);
                System.out.println(printTree(tree));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        sc.close();
    }


    public static String printTree(ISyntaxNode tree){
        StringBuilder sb = new StringBuilder();
        sb.append(tree.word()).append('\n');
        printTree(tree, 1, sb);
        return sb.toString();
    }

    public static void printTree(ISyntaxNode tree, int level, StringBuilder sb){
        IList<ISyntaxNode> children = tree.children();
        // 深度优先
        if(children != null && children.size() > 0){
            Iterator<ISyntaxNode> itr = children.iterator();
            while(itr.hasNext()){
                ISyntaxNode next = itr.next();
                sb.append(StringUtils.leftFill(next.word(), ' ', level * 4)).append('\n');
                printTree(next, level + 1, sb);
            }
        }
    }
    
}
