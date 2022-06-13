package pl.polsl.hdised.consumer.limitedqueue;

import java.util.*;

public class LimitedQueue<MeasurementDto> implements Queue<MeasurementDto> {

    Queue<MeasurementDto> measurementDtos = new ArrayDeque<>();
    private final Short MAX_CAPACITY = 20;

    @Override
    public int size() {
        return this.measurementDtos.size();
    }

    @Override
    public boolean isEmpty() {
        return this.measurementDtos.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.measurementDtos.contains(o);
    }

    @Override
    public Iterator<MeasurementDto> iterator() {
        return this.measurementDtos.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.measurementDtos.toArray();
    }

    @Override
    public <MeasurementDto> MeasurementDto[] toArray(MeasurementDto[] a) {
        return this.measurementDtos.toArray(a);
    }

    @Override
    public boolean add(MeasurementDto measurementDto) {
        if(this.measurementDtos.size() == this.MAX_CAPACITY){
            this.measurementDtos.remove();
        }
        return this.measurementDtos.add(measurementDto);
    }

    @Override
    public boolean remove(Object o) {
        return this.measurementDtos.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.measurementDtos.contains(c);
    }

    @Override
    public boolean addAll(Collection<? extends MeasurementDto> c) {
        return this.measurementDtos.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.measurementDtos.remove(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.measurementDtos.retainAll(c);
    }

    @Override
    public void clear() {
        this.measurementDtos.clear();
    }

    @Override
    public boolean offer(MeasurementDto measurementDto) {
        return this.measurementDtos.offer(measurementDto);
    }

    @Override
    public MeasurementDto remove() {
        return this.measurementDtos.remove();
    }

    @Override
    public MeasurementDto poll() {
        return this.measurementDtos.poll();
    }

    @Override
    public MeasurementDto element() {
        return this.measurementDtos.element();
    }

    @Override
    public MeasurementDto peek() {
        return this.measurementDtos.peek();
    }
}
