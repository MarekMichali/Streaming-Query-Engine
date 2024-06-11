package pl.polsl.hdised.engine.query.historical;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.Tuple;
import org.springframework.stereotype.Service;
import pl.polsl.hdised.engine.device.model.DeviceDto;
import pl.polsl.hdised.engine.entity.DeviceEntity;
import pl.polsl.hdised.engine.entity.LocationEntity;
import pl.polsl.hdised.engine.exception.ParametersNotFoundException;
import pl.polsl.hdised.engine.location.model.LocationDto;
import pl.polsl.hdised.engine.measurement.model.TemperatureResponseDto;
import pl.polsl.hdised.engine.repository.DeviceRepository;
import pl.polsl.hdised.engine.repository.LocationRepository;
import pl.polsl.hdised.engine.repository.MeasurementRepository;

@Service
public class HistoricalQueryService {

  private final MeasurementRepository measurementRepository;
  private final DeviceRepository deviceRepository;
  private final LocationRepository locationRepository;

  public HistoricalQueryService(
      MeasurementRepository measurementRepository,
      DeviceRepository deviceRepository,
      LocationRepository locationRepository) {
    this.measurementRepository = measurementRepository;
    this.deviceRepository = deviceRepository;
    this.locationRepository = locationRepository;
  }

  public Float getHistoricalAverage(
      String deviceId, String location, String stringStartDate, String stringFinishDate)
      throws ParseException, ParametersNotFoundException {
    if (!parametersExists(deviceId, location)) {
      throw new ParametersNotFoundException();
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
    Date startDate = format.parse(stringStartDate + ":00.000");
    Date finishDate = format.parse(stringFinishDate + ":00.000");
    return this.measurementRepository.getAverage(
        Objects.requireNonNull(deviceId, "device id cannot be null"),
        Objects.requireNonNull(location, "location cannot be null"),
        Objects.requireNonNull(startDate, "date cannot be null"),
        Objects.requireNonNull(finishDate, "date cannot be null"));
  }

  public List<DeviceDto> getDevices() {
    List<DeviceDto> deviceDtos = new ArrayList<>();
    List<DeviceEntity> deviceEntities = this.deviceRepository.findAll();
    deviceEntities.forEach(de -> deviceDtos.add(new DeviceDto(de.getDeviceId())));

    return deviceDtos;
  }

  public List<LocationDto> getLocations() {
    List<LocationDto> locationDtos = new ArrayList<>();
    List<LocationEntity> locationEntities = this.locationRepository.findAll();
    locationEntities.forEach(le -> locationDtos.add(new LocationDto(le.getCity())));

    return locationDtos;
  }

  private Boolean parametersExists(String deviceId, String location) {
    return !Objects.isNull(this.deviceRepository.findDeviceById(deviceId))
        && !Objects.isNull(this.locationRepository.findLocationByCity(location));
  }

  public Float getMinimumHistoricalTemperature(
      String deviceId, String location, String stringStartDate, String stringFinishDate)
      throws ParseException, ParametersNotFoundException {
    if (!parametersExists(deviceId, location)) {
      throw new ParametersNotFoundException();
    }
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
    Date startDate = format.parse(stringStartDate + ":00.000");
    Date finishDate = format.parse(stringFinishDate + ":00.000");
    return this.measurementRepository.getMinimumTemperature(
        Objects.requireNonNull(deviceId),
        Objects.requireNonNull(location),
        Objects.requireNonNull(startDate),
        Objects.requireNonNull(finishDate));
  }

  public Float getMaximumHistoricalTemperature(
      String deviceId, String location, String stringStartDate, String stringFinishDate)
      throws ParseException, ParametersNotFoundException {
    Objects.requireNonNull(deviceId);
    Objects.requireNonNull(location);
    Objects.requireNonNull(stringStartDate);
    Objects.requireNonNull(stringFinishDate);
    if (!parametersExists(deviceId, location)) {
      throw new ParametersNotFoundException();
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
    Date startDate = format.parse(stringStartDate + ":00.000");
    Date finishDate = format.parse(stringFinishDate + ":00.000");

    return this.measurementRepository.getMaximumTemperature(
        deviceId, location, startDate, finishDate);
  }

  public List<TemperatureResponseDto> getAllHistoricalTemperatures(
      String deviceId, String location, String stringStartDate, String stringFinishDate)
      throws ParseException {
    List<TemperatureResponseDto> temperatureResponseDtos = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
    Date startDate = format.parse(stringStartDate + ":00.000");
    Date finishDate = format.parse(stringFinishDate + ":00.000");

    for (Tuple record :
        this.measurementRepository.getAllTemperatures(deviceId, location, startDate, finishDate)) {
      var timestamp = record.get("date", Timestamp.class);
      LocalDateTime dateTime = timestamp.toLocalDateTime();
      TemperatureResponseDto temperatureResponseDto =
          new TemperatureResponseDto(dateTime, record.get("temperature", Float.class));

      temperatureResponseDtos.add(temperatureResponseDto);
    }

    return temperatureResponseDtos;
  }
}
