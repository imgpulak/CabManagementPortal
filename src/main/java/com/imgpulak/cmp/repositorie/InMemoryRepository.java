package com.imgpulak.cmp.repositorie;

import com.imgpulak.cmp.model.Cab;

import com.imgpulak.cmp.model.City;
import com.imgpulak.cmp.model.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@org.springframework.stereotype.Repository
public final class InMemoryRepository implements Repository {
  private final static Logger LOG = LoggerFactory.getLogger(InMemoryRepository.class);
  private Map<String, City> cityStore = new ConcurrentHashMap<String, City>();
  private Map<String, Cab> cabStore = new ConcurrentHashMap<String, Cab>();
  private Map<String, Trip> tripStore = new ConcurrentHashMap<String, Trip>();
  private Map<String, BlockingQueue> cabPool = new ConcurrentHashMap<String, BlockingQueue>();

  @Override
  public BlockingQueue getCabPool(String cityId) {
    return cabPool.get(cityId);
  }

  @Override
  public void add(City city) {
    String cityId = city.getCityId();
    if (cityStore.containsKey(cityId)) {
      LOG.debug("City with id={} is present in the store already.", cityId);
    } else {
      cityStore.put(cityId, city);
      BlockingQueue cabPoolPerCity = new LinkedBlockingQueue<Cab>();
      cabPool.put(cityId, cabPoolPerCity);
      LOG.debug("City with id={} is successfully added.", cityId);
    }
  }

  @Override
  public Optional<City> findCity(String cityId) {
    return Optional.ofNullable(cityStore.get(cityId));
  }

  @Override
  public void add(Cab cab) {
    String cabId = cab.getCabId();
    if( cabStore.containsKey(cabId) ) {
      LOG.debug("Can with id={} is already added.", cabId);
    } else {
      cabStore.put(cab.getCabId(), cab);
      cabPool.get(cab.getCityId()).add(cab);
      LOG.debug("Cab with id={} is successfully added.", cab.getCabId());
    }
  }

  @Override
  public Optional<Cab> findCab(String cabId) {
    return Optional.ofNullable(cabStore.get(cabId));
  }

  @Override
  public void add(Trip trip) {
    String tripId = trip.getTripId();
    if( tripStore.containsKey(tripId) ) {
      LOG.debug("Trip with id={} is already added.", tripId);
    } else {
      tripStore.put(tripId, trip);
      LOG.debug("Trip with id={} is successfully added.", tripId);
    }
  }

  @Override
  public Optional<Trip> findTrip(String tripId) {
    return Optional.ofNullable(tripStore.get(tripId));
  }
}
