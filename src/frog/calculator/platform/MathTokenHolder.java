package frog.calculator.platform;

import frog.calculator.compile.lexical.CommonToken;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.ContainerNode;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import frog.calculator.compile.syntax.NonterminalNode;
import frog.calculator.compile.syntax.TerminalNode;
import frog.calculator.compile.syntax.NonterminalNode.AssociateType;
import frog.calculator.microexec.impl.base.AddExecutor;
import frog.calculator.microexec.impl.base.BracketExecutor;
import frog.calculator.microexec.impl.base.DivExecutor;
import frog.calculator.microexec.impl.base.MultExecutor;
import frog.calculator.microexec.impl.base.PowerExecutor;
import frog.calculator.microexec.impl.base.SubExecutor;
import frog.calculator.microexec.impl.ext.FactorialExecutor;
import frog.calculator.microexec.impl.ext.PercentExecutor;
import frog.calculator.microexec.impl.fun.SumExecutor;

public class MathTokenHolder implements ITokenHolder {

    private static final ISyntaxNodeGenerator[] builders = new ISyntaxNodeGenerator[] { 
        new NonterminalNode("+", 10, new AddExecutor()),
        new NonterminalNode("-", 10, new SubExecutor()), 
        new NonterminalNode("++", 10, new AddExecutor()),
        new NonterminalNode("+-", 10, new SubExecutor()),
        new NonterminalNode("-+", 10, new SubExecutor()),
        new NonterminalNode("--", 10, new AddExecutor()),
        new NonterminalNode("*", 20, new MultExecutor()), 
        new NonterminalNode("/", 20, new DivExecutor()),
        new NonterminalNode("^", 30, new PowerExecutor()), 
        new NonterminalNode("!", 40, AssociateType.LEFT, new FactorialExecutor()),
        new NonterminalNode("%", 40, AssociateType.LEFT, new PercentExecutor()),
        new NonterminalNode("sum", 50, AssociateType.RIGHT, new SumExecutor()),
        new ContainerNode("(", ")", new BracketExecutor()),
        new TerminalNode(")", null)
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
