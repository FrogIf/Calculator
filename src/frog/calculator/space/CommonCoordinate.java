package frog.calculator.space;

import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;

public class CommonCoordinate implements ICoordinate {

    private IList<Integer> list = new LinkedList<>();

    public CommonCoordinate(int... coordinates) {
        for(int i : coordinates){
            list.add(i);
        }
    }

    @Override
    public void add(int axialValue) {
        list.add(axialValue);
    }

    @Override
    public ICoordinateViewer getViewer(){
        return new CommonCoordinateViewer(list.iterator());
    }

}
