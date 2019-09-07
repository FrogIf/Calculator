package frog.calculator.space;

import frog.calculator.util.collection.IList;

public class SpaceBuilder {

    private int[] info;

    private IList<IPoint> elements;

    public void setDimension(int dimension) {
        if(info != null){
            throw new IllegalStateException("the dimension has been assign.");
        }
        info = new int[dimension];
    }

    public void setWidth(int dimension, int width) {
        if(dimension > info.length || dimension < 0){
            throw new IllegalArgumentException("dimension is error. dimension : " + dimension);
        }
        info[dimension] = width;
    }

    public void initElements(IList<IPoint> elements){
        this.elements = elements;
    }

    public ISpace build() {
        if(this.elements == null){
//            return new InterleavedSpace();
            return new FixedAlignSpace(info);
        }else{
//            return new InterleavedSpace();
            return new FixedAlignSpace(info, this.elements);
        }
    }
}
