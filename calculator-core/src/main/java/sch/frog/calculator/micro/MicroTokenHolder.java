package sch.frog.calculator.micro;

import sch.frog.calculator.compile.lexical.CommonToken;
import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.syntax.DeducibleNode;
import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import sch.frog.calculator.micro.exec.impl.base.AddExecutor;
import sch.frog.calculator.micro.exec.impl.base.AssignExecutor;
import sch.frog.calculator.micro.exec.impl.base.BracketExecutor;
import sch.frog.calculator.micro.exec.impl.base.CommaExecutor;
import sch.frog.calculator.micro.exec.impl.base.ComplexMarkExecutor;
import sch.frog.calculator.micro.exec.impl.base.DeclareExecutor;
import sch.frog.calculator.micro.exec.impl.base.DivExecutor;
import sch.frog.calculator.micro.exec.impl.base.MultExecutor;
import sch.frog.calculator.micro.exec.impl.base.PowerExecutor;
import sch.frog.calculator.micro.exec.impl.base.SubExecutor;
import sch.frog.calculator.micro.exec.impl.ext.FactorialExecutor;
import sch.frog.calculator.micro.exec.impl.ext.PercentExecutor;
import sch.frog.calculator.micro.exec.impl.fun.AbsExecutor;
import sch.frog.calculator.micro.exec.impl.fun.AverageExecutor;
import sch.frog.calculator.micro.exec.impl.fun.SumExecutor;

class MicroTokenHolder {

    private MicroTokenHolder(){
        // do nothing
    }

    private static final ISyntaxNodeGenerator[] builders = new ISyntaxNodeGenerator[] {
        new DeducibleNode("=", 5, new AssignExecutor()),
        new DeducibleNode("*", 20, new MultExecutor()),
        new DeducibleNode("/", 20, new DivExecutor()),
        new DeducibleNode("^", 30, new PowerExecutor()),
        new DeducibleNode("!", 40, DeducibleNode.AssociateType.LEFT, new FactorialExecutor()),
        new DeducibleNode("%", 40, DeducibleNode.AssociateType.LEFT, new PercentExecutor()),
        new DeducibleNode("i", 40, DeducibleNode.AssociateType.LEFT, new ComplexMarkExecutor()),
        new DeducibleNode("sum", 50, DeducibleNode.AssociateType.RIGHT, new SumExecutor()),
        new DeducibleNode("avg", 50, DeducibleNode.AssociateType.RIGHT, new AverageExecutor()),
        new DeducibleNode("@", 60, DeducibleNode.AssociateType.RIGHT, new DeclareExecutor()),
        new DeducibleNode(",", 0, new CommaExecutor()),
        new DeducibleNode("(", -1, DeducibleNode.AssociateType.RIGHT, new BracketExecutor()).setEnd(")"),
        new DeducibleNode(")", 0, DeducibleNode.AssociateType.LEFT, new BracketExecutor()),
        new DeducibleNode("abs", 50, DeducibleNode.AssociateType.RIGHT, new AbsExecutor())
    };

    public static final IToken ADD_TOKEN =  new CommonToken("+", new DeducibleNode("+", 10, new AddExecutor()));

    public static final IToken SUB_TOKEN =  new CommonToken("-", new DeducibleNode("-", 10, new SubExecutor()));

    public static IToken[] getTokens() {
        IToken[] tokens = new IToken[builders.length];
        for(int i = 0, len = builders.length; i < len; i++){
            ISyntaxNodeGenerator b = builders[i];
            tokens[i] = new CommonToken(b.word(), b);
        }
        return tokens;
    }
    
}
