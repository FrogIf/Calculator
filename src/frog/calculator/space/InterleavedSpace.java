package frog.calculator.space;

import frog.calculator.util.ComparableComparator;
import frog.calculator.util.collection.*;

public class InterleavedSpace<T> implements ISpace<T> {

    private ISet<XSpace<T>> subspaces = new TreeSet<>(ComparableComparator.<XSpace<T>>getInstance());

    private ISet<IPoint<T>> points = new TreeSet<>(PointComparator.getInstance());

    private int dimension;

    @Override
    public IPoint<T> getPoint(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        int pos = 0;
        int di = this.dimension;
        while(traveller.hasNext()){
            di--;
            if((pos = traveller.next()) != 0){
                break;
            }
        }

        XSpace<T> finder = new XSpace<>(pos);

        if(di > 0 || traveller.hasNext()){
            XSpace<T> xSpace = this.subspaces.find(finder);
            if(xSpace == null){ return null; }
            ISpace<T> space = xSpace.space;
            while(traveller.hasNext()){
                di--;
                if((pos = traveller.next()) != 0){
                    if(!(space instanceof InterleavedSpace)){ break; }
                    else if(traveller.hasNext()){
                        finder.index = pos;
                        xSpace = ((InterleavedSpace<T>) space).subspaces.find(finder);
                        if(xSpace == null){ return null; }
                        else{ space = xSpace.space; }
                    }
                }
            }

            return space.getPoint(new Coordinate(di == 0 ? pos : 0));
        }else{
            XPoint<T> xPoint = new XPoint<>(pos);
            return points.find(xPoint);
        }
    }

    @Override
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        ISpace<T> space = this;

        int pos = 0;
        int di = 0;
        while(traveller.hasNext() && (pos = traveller.next()) == 0){
            di++;
        }

        if(pos != 0 && traveller.hasNext()){
            XSpace<T> finder = new XSpace<>(pos);
            XSpace<T> xSpace;
            space = this;

            while(traveller.hasNext()){
                if(pos != 0){
                    if(!(space instanceof InterleavedSpace)){
                        space = space.getSubspace(new ContinueCoordinate(traveller, this.dimension - di));
                    }else{
                        finder.index = pos;
                        xSpace = ((InterleavedSpace<T>) space).subspaces.find(finder);
                        if(xSpace == null){ return null; }
                        else{ space = xSpace.space; }
                    }
                }

                di++;
                pos = traveller.next();
            }
        }

        return space;
    }

    @Override
    public int dimension() {
        return this.dimension;
    }

    @Override
    public int width(ICoordinate coordinate) {
        ISpace<T> space = this.getSubspace(coordinate);
        if(space == null){
            return 0;
        }else{
            if(space instanceof InterleavedSpace){
                if(coordinate.dimension() == this.dimension){
                    return ((InterleavedSpace)space).points.size();
                }else{
                    return ((InterleavedSpace)space).subspaces.size() + 1;
                }
            }else{
                return space.width(Coordinate.ORIGIN);
            }
        }
    }

    @Override
    public void addPoint(IPoint<T> point, ICoordinate coordinate) {
        if(point == null || coordinate == null){
            throw new IllegalArgumentException("point info is null.");
        }

        if(coordinate.dimension() > this.dimension){
            this.dimension = coordinate.dimension();
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        int pos = 0;
        int di = 0;
        while(traveller.hasNext()){
            di++;
            if((pos = traveller.next()) != 0){
                break;
            }
        }

        if(!traveller.hasNext()){
            point.setAxialValue(pos);
            if(!this.points.add(point)){
                throw new IllegalStateException("the point has exists.");
            }
        }else{
            XSpace<T> space = this.subspaces.find(new XSpace<>(pos));
            if(space != null){
                space.space.addPoint(point, new ContinueCoordinate(traveller, coordinate.dimension() - di));
            }else{
                ISpace<T> subSpace = new InterleavedSpace<>();
                subSpace.addPoint(point, new ContinueCoordinate(traveller, coordinate.dimension() - di));
                this.subspaces.add(new XSpace<>(pos, subSpace));
            }
        }

    }

    @Override
    public IList<IPoint<T>> getPoints() {
        IList<IPoint<T>> result = new ArrayList<>();
        Iterator<IPoint<T>> iterator = this.points.iterator();
        while(iterator.hasNext()){
            result.add(iterator.next());
        }

        Iterator<XSpace<T>> spaceIterator = this.subspaces.iterator();
        while(spaceIterator.hasNext()){
            XSpace<T> space = spaceIterator.next();
            ISpace<T> s = space.space;
            IList<IPoint<T>> points = s.getPoints();
            Iterator<IPoint<T>> pi = points.iterator();
            while(pi.hasNext()){
                result.add(pi.next());
            }
        }

        return new UnmodifiableList<>(result);
    }

    private static class ContinueCoordinate extends AbstractCoordinate{

        private Itraveller<Integer> traveller;

        private int dimension;

        private ContinueCoordinate(Itraveller<Integer> traveller, int dimension) {
            this.traveller = traveller;
            this.dimension = dimension;
        }

        @Override
        public void add(int axialValue) {
            throw new IllegalStateException("this object can't support this method.");
        }

        @Override
        public int dimension() {
            return this.dimension;
        }

        @Override
        public Itraveller<Integer> traveller() {
            return traveller;
        }

        @Override
        public boolean isOrigin() {
            // 判断是否是原点
            return false;
        }
    }

    private static class XSpace<T> implements Comparable<XSpace<T>> {
        private int index;
        private ISpace<T> space;
        private XSpace(int index, ISpace<T> space) {
            this.index = index;
            this.space = space;
        }

        private XSpace(int index){
            this.index = index;
        }

        @Override
        public int compareTo(XSpace<T> o) {
            return this.index - o.index;
        }
    }

    private static class XPoint<T> extends AbstractPoint<T> {

        private final int axialValue;

        public XPoint(int axialValue) {
            this.axialValue = axialValue;
        }

        @Override
        public T intrinsic() {
            return null;
        }

        @Override
        public int getAxialValue() {
            return this.axialValue;
        }

        @Override
        public void setAxialValue(int val) {
            // do nothing.
        }

    }
}
