package frog.calculator.util.collection;

public abstract class AbstractCollection<E> implements ICollection<E>{

    @Override
    public void addAll(ICollection<E> e) {
        Iterator<E> iterator = e.iterator();
        while (iterator.hasNext()){
            this.add(iterator.next());
        }
    }
}
