package frog.calculator.space;

import frog.calculator.util.collection.Itraveller;

public abstract class AbstractPoint<T> implements IPoint<T> {

    protected ICoordinate coordinate;

    @Override
    public IPoint clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (IPoint) clone;
    }

    @Override
    public int compareTo(IPoint<T> o){
        Itraveller<Integer> t1 = this.getCoordinate().traveller();
        Itraveller<Integer> t2 = o.getCoordinate().traveller();

        while(t1.hasNext() && t2.hasNext()){
            int n1 = t1.next();
            if(n1 == 0) {continue;}
            int n2 = t2.next();
            if(n1 > n2){
                return 1;
            }else if(n1 < n2){
                return -1;
            }
        }

        while(t1.hasNext()){
            if(t1.next() != 0) return 1;
        }

        while(t2.hasNext()){
            if(t2.next() != 0) return -1;
        }

        return 0;
    }
}
