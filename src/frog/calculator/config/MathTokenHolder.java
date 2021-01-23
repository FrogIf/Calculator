package frog.calculator.config;

import frog.calculator.compile.lexical.CommonToken;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.ContainerNode;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import frog.calculator.compile.syntax.NonterminalNode;
import frog.calculator.compile.syntax.TerminalNode;
import frog.calculator.compile.syntax.NonterminalNode.AssociateType;

public class MathTokenHolder implements ITokenHolder {

    private static final ISyntaxNodeGenerator[] builders = new ISyntaxNodeGenerator[] { 
        new NonterminalNode(null, "+", 10),
        new NonterminalNode(null, "-", 10), 
        new NonterminalNode(null, "*", 20), 
        new NonterminalNode(null, "/", 20),
        new NonterminalNode(null, "^", 30), 
        new NonterminalNode(null, "!", 40, AssociateType.LEFT),
        new NonterminalNode(null, "%", 40, AssociateType.LEFT),
        new NonterminalNode(null, "sum", 50, AssociateType.RIGHT),
        new ContainerNode(null, "(", ")"),
        new TerminalNode(null, ")")
    };

    @Override
    public IToken[] getTokens() {
        IToken[] tokens = new IToken[builders.length];
        for(int i = 0, len = builders.length; i < len; i++){
            ISyntaxNodeGenerator b = builders[i];
            tokens[i] = new CommonToken(b.word(), b);
        }
        return tokens;
    }
    
}
