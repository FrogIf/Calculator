package frog.calculator.space;

import frog.calculator.util.collection.Itraveller;
import frog.calculator.util.collection.LinkedList;

public class Coordinate extends AbstractCoordinate {

    private LinkedList<Integer> list = new LinkedList<>();

    public Coordinate(int... coordinates) {
        for(int i : coordinates){
            list.add(i);
        }
    }

    @Override
    public void add(int axialValue) {
        list.add(axialValue);
    }

    @Override
    public int dimension() {
        return list.size();
    }

    @Override
    public Itraveller<Integer> traveller() {
        return this.list.iterator();
    }

}
