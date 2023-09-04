package io.github.frogif.calculator.compile.semantic.result;

import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.util.collection.UnmodifiableList;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

/**
 * 函数
 */
public class FunctionValue implements IValue {
    
    private final IList<String> arguments;

    private final ISyntaxNode functionBody;

    public FunctionValue(IList<String> arguments, ISyntaxNode functionBody){
        if(arguments != null){
            this.arguments = new UnmodifiableList<>(arguments);
        }else{
            this.arguments = null;
        }
        this.functionBody = functionBody;
    }

    public IList<String> getFormalArguments() {
        return this.arguments;
    }

    public ISyntaxNode getFunctionBody() {
        return this.functionBody;
    }

}
