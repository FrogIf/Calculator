package frog.calculator.space;

import frog.calculator.util.collection.IList;

public class CommonSpaceBuilder implements ISpaceBuilder {

    private int[] info;

    private IList<ILiteral> elements;

    @Override
    public void setDimension(int dimension) {
        if(info != null){
            throw new IllegalStateException("the dimension has been assign.");
        }
        info = new int[dimension];
    }

    @Override
    public void setWidth(int dimension, int width) {
        if(dimension > info.length || dimension < 0){
            throw new IllegalArgumentException("dimension is error. dimension : " + dimension);
        }
        info[dimension] = width;
    }

    @Override
    public void initElements(IList<ILiteral> elements){
        this.elements = elements;
    }

    @Override
    public ISpace build() {
        if(this.elements == null){
            return new CommonSpace(info);
        }else{
            return new CommonSpace(info, this.elements);
        }
    }
}
