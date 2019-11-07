package frog.calculator.space;

import frog.calculator.util.collection.*;

public class FixedAlignSpace<T> implements ISpace<T> {

    private final int dimension;

    private final int[] widthInfo;

    private final Object[] values;

    private IRange range = null;

    public FixedAlignSpace(IRange range){
        this(range.maxWidths());
    }

    FixedAlignSpace(int[] widths) {
        this.dimension = widths.length;
        this.widthInfo = widths;

        int totalWidth = 1;
        for (int width : widths) {
            totalWidth *= width;
        }

        values = new Object[totalWidth];
    }

    FixedAlignSpace(int[] widths, IList<T> values){
        this.dimension = widths.length;
        this.widthInfo = widths;
        int totalWidth = 1;
        for (int width : widths) {
            totalWidth *= width;
        }
        if(values.size() <= totalWidth){
            this.values = new Object[totalWidth];
            Iterator<T> iterator = values.iterator();
            int i = 0;
            while(iterator.hasNext()){
                this.values[i] = iterator.next();
                i++;
            }
        }else{
            throw new IllegalArgumentException("input values is too large.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }
        int offset = locate(coordinate.traveller());
        return offset >= 0 ? (T) values[offset] : null;
    }

    @Override
    public void add(T point, ICoordinate coordinate) {
        if(coordinate == null){
            throw new IllegalArgumentException("coordinate is null.");
        }

        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        int pos = locate(coordinate.traveller());
        if(pos == -1){
            throw new IllegalArgumentException("can't locate the coordinate.");
        }

        if(values[pos] == null) { values[pos] = point; }
        else{
            throw new IllegalStateException("the point has exists.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public IList<T> getElements() {
        return new UnmodifiableList<>((IList<T>) new ArrayList<Object>(this.values));
    }

    private int locate(ITraveller<Integer> traveller){
        int di = 0;
        int len = values.length;
        int w;
        int p;
        int offset = 0;

        while(traveller.hasNext()){
            w = widthInfo[di];
            p = traveller.next();

            if(p >= w){
                return -1;
            }

            len = len / w;
            offset += len * p;

            di++;
        }

        return offset;
    }

    @Override
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        int dim = coordinate.dimension();
        if(dim > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        ITraveller<Integer> traveller = coordinate.traveller();
        ICoordinate subPos = new Coordinate();

        int offset = 0;
        int di = 0;
        int len = values.length;
        int w;
        int p;

        while(traveller.hasNext()){
            p = traveller.next();
            subPos.add(p);

            w = widthInfo[di];

            if(p >= w){
                return null;
            }

            len = len / w;
            offset += len * p;

            di++;
        }

        if(offset == -1){ return null; }

        int l = 1;
        for(int m = di, i = 0; m < this.dimension; m++, i++){
            l *= widthInfo[m];
        }


        Subspace subspace = new Subspace();
        subspace.start = offset;
        subspace.end = offset + l - 1;
        subspace.pos = subPos;

        return subspace;
    }

    @Override
    public IRange getRange() {
        if(this.range == null){
            SpaceRange range = new SpaceRange();
            range.setMaxWidths(widthInfo);
            this.range = new UnmodifiedSpaceRange(range);
        }
        return this.range;
    }

    private class Subspace implements ISpace<T>{

        private ICoordinate pos;

        private int start;

        private int end;

        private IList<T> points = null;

        @Override
        public void add(T val, ICoordinate coordinate) {
            FixedAlignSpace.this.add(val, realCoordinate(coordinate));
        }

        @Override
        @SuppressWarnings("unchecked")
        public IList<T> getElements() {
            if(points == null){
                Object[] values = FixedAlignSpace.this.values;
                points = new ArrayList<>(end - start + 1);
                for(int i = start; i <= end; i++){
                    points.add((T) values[i]);
                }
                points = new UnmodifiableList<>(points);
            }
            return points;
        }

        @Override
        public T get(ICoordinate coordinate) {
            return FixedAlignSpace.this.get(realCoordinate(coordinate));
        }

        @Override
        public ISpace<T> getSubspace(ICoordinate coordinate) {
            return FixedAlignSpace.this.getSubspace(realCoordinate(coordinate));
        }

        @Override
        public IRange getRange() {
            return null;
        }

        private ICoordinate realCoordinate(ICoordinate coordinate){
            if(coordinate == null){ return pos; }

            ICoordinate realPos = new Coordinate();
            ITraveller<Integer> traveller = pos.traveller();
            while(traveller.hasNext()){
                realPos.add(traveller.next());
            }
            ITraveller<Integer> inputPos = coordinate.traveller();
            while(inputPos.hasNext()){
                realPos.add(inputPos.next());
            }
            return realPos;
        }
    }

}
