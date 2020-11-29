package com.imgpulak.cmp.service;


import com.imgpulak.cmp.exception.NoCabAvailableException;
import com.imgpulak.cmp.exception.TripNotFoundException;
import com.imgpulak.cmp.model.Cab;
import com.imgpulak.cmp.model.City;
import com.imgpulak.cmp.model.Trip;
import com.imgpulak.cmp.repositorie.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

@Service
public final class CabManagementService {
  private final Repository repository;

  @Autowired
  public CabManagementService(final Repository repository) {
    this.repository = repository;
  }

  public void registerCab(Cab cab) throws MalformedURLException {
    repository.add(cab);
  }

  public void registerCity(City city) throws MalformedURLException {
    repository.add(city);
  }

  public String initiateTrip(Trip trip) throws NoCabAvailableException, InterruptedException {
    // Assign a free cab
    String sourceCityId = trip.getSourceCityId();
    BlockingQueue cabPool = repository.getCabPool(sourceCityId);
    if(cabPool.isEmpty()) {
      throw new NoCabAvailableException();
    }
    Cab cab = (Cab)cabPool.take();
    trip.setCabId(cab.getCabId());
    cab.getCabState().nextState(); // Changing cab state once it is assigned

    // Generating a random tripID
    String tripId = UUID.randomUUID().toString();
    trip.setTripId(tripId);

    // Setting trip start time
    trip.setStartDate(new Date(Instant.now().toEpochMilli()));

    return tripId;
  }

  public void endTrip(String tripId) throws TripNotFoundException {
    Optional<Trip> optionalTrip = repository.findTrip(tripId);
    if( optionalTrip.isPresent() ) {
      Trip trip = optionalTrip.get();
      Cab cab = repository.findCab(trip.getCabId()).get();
      cab.getCabState().nextState(); // Changing cab state
      // Setting trip end time
      trip.setEndDate(new Date(Instant.now().toEpochMilli()));
    } else {
      throw  new TripNotFoundException();
    }
  }
}
