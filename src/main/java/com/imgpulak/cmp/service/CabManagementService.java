package com.imgpulak.cmp.service;


import com.imgpulak.cmp.controller.CabManagementRestController;
import com.imgpulak.cmp.exception.CityNotFoundException;
import com.imgpulak.cmp.exception.NoCabAvailableException;
import com.imgpulak.cmp.exception.TripNotFoundException;
import com.imgpulak.cmp.model.Cab;
import com.imgpulak.cmp.model.City;
import com.imgpulak.cmp.model.Trip;
import com.imgpulak.cmp.repositorie.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Block;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

@Service
public final class CabManagementService {
  private final static Logger LOG = LoggerFactory.getLogger(CabManagementService.class);
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

  public String initiateTrip(Trip trip)
          throws CityNotFoundException, NoCabAvailableException, InterruptedException {
    // Assign a free cab
    String sourceCityId = trip.getSourceCityId();
    LOG.debug("Source cityId={}", sourceCityId);

    if ( !repository.findCity(sourceCityId).isPresent()) {
      throw new CityNotFoundException();
    }

    BlockingQueue cabPool = repository.getCabPool(sourceCityId);
    if(cabPool.isEmpty()) {
      throw new NoCabAvailableException();
    }
    Cab cab = (Cab)cabPool.take();
    trip.setCabId(cab.getCabId());
    cab.getCabState().nextState(); // Changing cab state once it is assigned
    cab.setCityId("indeterminate"); // On trip

    // Generating a random tripID
    String tripId = UUID.randomUUID().toString();
    trip.setTripId(tripId);

    // Setting trip start time
    trip.setStartDate(new Date(Instant.now().toEpochMilli()));

    repository.add(trip); // Saved in trip repo

    return tripId;
  }

  public void endTrip(String tripId) throws TripNotFoundException {
    Optional<Trip> optionalTrip = repository.findTrip(tripId);
    if( optionalTrip.isPresent() ) {
      Trip trip = optionalTrip.get();
      Cab cab = repository.findCab(trip.getCabId()).get();
      cab.getCabState().nextState(); // Changing cab state
      // Setting trip end time and ending the trip
      trip.setEndDate(new Date(Instant.now().toEpochMilli()));

      // Now cab will be added in destination city pool
      cab.setCityId(trip.getDestinationCityId());
      BlockingQueue cabPool = repository.getCabPool(trip.getDestinationCityId());
      cabPool.add(cab);
    } else {
      throw  new TripNotFoundException();
    }
  }
}
