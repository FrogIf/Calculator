package frog.calculator.space;

import frog.calculator.util.collection.Itraveller;
import frog.calculator.util.collection.LinkedList;

public class Coordinate extends AbstractCoordinate {

    private LinkedList<Integer> list = new LinkedList<>();

    private boolean isOrigin = true;

    public Coordinate(int... coordinates) {
        for(int i : coordinates){
            if(i < 0){
                throw new IllegalArgumentException("coordinate can't be negative.");
            }
            this.isOrigin = this.isOrigin && (i == 0);
            list.add(i);
        }
    }

    @Override
    public void add(int axialValue) {
        if(axialValue < 0){
            throw new IllegalArgumentException("coordinate can't be negative.");
        }
        this.isOrigin = this.isOrigin && (axialValue == 0);
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
