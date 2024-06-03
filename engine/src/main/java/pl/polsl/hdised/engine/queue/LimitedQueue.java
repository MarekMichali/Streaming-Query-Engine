package pl.polsl.hdised.engine.queue;

import java.util.*;
import pl.polsl.hdised.engine.measurement.model.MeasurementDto;

public class LimitedQueue implements Queue<MeasurementDto> {

  private static final Short MAX_CAPACITY = 20;
  Queue<MeasurementDto> measurements = new ArrayDeque<>();
  private Float maximumTemperature = Float.MIN_VALUE;
  private Float minimumTemperature = Float.MAX_VALUE;
  private Float sum = 0.0f;

  public Float getMaximumTemperature() {
    return maximumTemperature;
  }

  public Float getMinimumTemperature() {
    return minimumTemperature;
  }

  public Float getAverageTemperature() {
    return this.sum / this.measurements.size();
  }

  @Override
  public int size() {
    return this.measurements.size();
  }

  @Override
  public boolean isEmpty() {
    return this.measurements.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return this.measurements.contains(o);
  }

  @Override
  public Iterator<MeasurementDto> iterator() {
    return this.measurements.iterator();
  }

  @Override
  public Object[] toArray() {
    return this.measurements.toArray();
  }

  @Override
  public <MeasurementDto> MeasurementDto[] toArray(MeasurementDto[] a) {
    return this.measurements.toArray(a);
  }

  @Override
  public boolean add(MeasurementDto measurementDto) {
    Objects.requireNonNull(measurementDto, "Measurement Cannot be null");

    if (measurementDto.getTemperature() > this.maximumTemperature) {
      this.maximumTemperature = measurementDto.getTemperature();
    }
    if (measurementDto.getTemperature() < this.minimumTemperature) {
      this.minimumTemperature = measurementDto.getTemperature();
    }

    if (this.measurements.size() == this.MAX_CAPACITY) {
      this.sum -= this.measurements.remove().getTemperature();
    }
    this.sum += measurementDto.getTemperature();
    return this.measurements.add(measurementDto);
  }

  @Override
  public boolean remove(Object o) {
    return this.measurements.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return this.measurements.contains(c);
  }

  @Override
  public boolean addAll(Collection<? extends MeasurementDto> c) {
    return this.measurements.addAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return this.measurements.remove(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return this.measurements.retainAll(c);
  }

  @Override
  public void clear() {
    this.measurements.clear();
  }

  @Override
  public boolean offer(MeasurementDto measurementDto) {
    return this.measurements.offer(measurementDto);
  }

  @Override
  public MeasurementDto remove() {
    return this.measurements.remove();
  }

  @Override
  public MeasurementDto poll() {
    return this.measurements.poll();
  }

  @Override
  public MeasurementDto element() {
    return this.measurements.element();
  }

  @Override
  public MeasurementDto peek() {
    return this.measurements.peek();
  }
}
