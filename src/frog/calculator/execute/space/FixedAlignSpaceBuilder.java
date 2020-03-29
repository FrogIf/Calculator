package frog.calculator.execute.space;

import frog.calculator.util.collection.IList;

public class FixedAlignSpaceBuilder<T> {

    private int[] info;

    private IList<T> elements;

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

    public void initElements(IList<T> elements){
        this.elements = elements;
    }

    public FixedAlignSpace<T> build() {
        if(this.elements == null){
            return new FixedAlignSpace<>(info);
        }else{
            return new FixedAlignSpace<>(info, this.elements);
        }
    }
}
