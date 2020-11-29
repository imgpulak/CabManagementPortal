package com.imgpulak.cmp.repositorie;

import com.imgpulak.cmp.model.Cab;
import com.imgpulak.cmp.model.City;
import com.imgpulak.cmp.model.Trip;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public interface Repository {

  void add(Cab cab);
  Optional<Cab> findCab(String cabId);

  void add(City city);
  Optional<City> findCity(String cabId);

  void add(Trip trip);
  Optional<Trip> findTrip(String tripId);

  BlockingQueue getCabPool(String cityId);
}
