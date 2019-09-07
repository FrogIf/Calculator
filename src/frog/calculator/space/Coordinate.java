package frog.calculator.space;

import frog.calculator.util.collection.Itraveller;
import frog.calculator.util.collection.LinkedList;

public class Coordinate extends AbstractCoordinate {

    private LinkedList<Integer> list = new LinkedList<>();

    private boolean isOrigin = true;

    private int endZero = -1;

    private boolean canModify = true;

    public Coordinate(int... coordinates) {
        for(int i : coordinates){
            if(i < 0){
                throw new IllegalArgumentException("coordinate can't be negative.");
            }
            this.isOrigin = this.isOrigin && (i == 0);
            endZero = (i == 0) ? (endZero == -1 ? list.size() : endZero) : -1;
            list.add(i);
        }
    }

    @Override
    public void add(int axialValue) {
        if(axialValue < 0){
            throw new IllegalArgumentException("coordinate can't be negative.");
        }
        if(!canModify){
            throw new IllegalStateException("coordinate can't be changed.");
        }
        this.isOrigin = this.isOrigin && (axialValue == 0);
        endZero = (axialValue == 0) ? (endZero == -1 ? list.size() : endZero) : -1;
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

    @Override
    public void trimRight() {
        canModify = false;
        if(endZero >= 0){
            for(int i = 0, r = list.size() - endZero; i < r; i++){
                list.postRemove();
            }
            endZero = -1;
        }
    }

}
