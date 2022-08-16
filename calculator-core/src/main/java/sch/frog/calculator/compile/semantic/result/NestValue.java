package sch.frog.calculator.compile.semantic.result;

import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.UnmodifiableList;

/**
 * VALUE的嵌套
 */
public class NestValue implements IValue {

    public enum Direction{
        HORIZONTAL,
        VERTICAL
    }

    private final IList<IValue> innerValues;

    private final Direction direction;

    public NestValue(IList<IValue> innerValue, Direction direction){
        this.innerValues = new UnmodifiableList<>(innerValue);
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public IList<IValue> getValues(){
        return this.innerValues;
    }

}
