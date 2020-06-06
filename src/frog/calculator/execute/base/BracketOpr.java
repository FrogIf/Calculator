package frog.calculator.execute.base;

import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.space.Coordinate;
import frog.calculator.execute.space.FixedAlignSpaceBuilder;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class BracketOpr extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IList<IExpression> elements = exp.children();
        FixedAlignSpaceBuilder<BaseNumber> builder = new FixedAlignSpaceBuilder<>();
        builder.setDimension(1);
        builder.setWidth(0, elements.size());
        ISpace<BaseNumber> space = builder.build();
        Iterator<IExpression> iterator = elements.iterator();
        int i = 0;
        while(iterator.hasNext()){
            ISpace<BaseNumber> cSpace = iterator.next().interpret();
            BaseNumber value = cSpace.get(new Coordinate(i));
            space.add(value, new Coordinate(i));
            i++;
        }
        return space;
    }
}
