package frog.calculator.space;

import frog.calculator.util.collection.IList;

public interface ISpaceBuilder {

    void setDimension(int dimension);

    void setWidth(int dimension, int width);

    ISpace build();

    void initElements(IList<ILiteral> elements);

}
