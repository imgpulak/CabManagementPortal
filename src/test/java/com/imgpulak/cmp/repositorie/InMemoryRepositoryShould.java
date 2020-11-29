package com.imgpulak.cmp.repositorie;

import com.imgpulak.cmp.model.Cab;
import com.imgpulak.cmp.model.Trip;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;

import static java.lang.Boolean.FALSE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InMemoryRepositoryShould {
  private static final String ID_1 = "100";
  private static final String ID_2 = "101";
  private InMemoryRepository inMemoryRepository;

  @Before
  public void setUp() {
    this.inMemoryRepository = new InMemoryRepository();
  }

  @Test
  public void addTripSuccessTest() {
    Trip trip = new Trip();
    trip.setTripId(ID_1);
    inMemoryRepository.add(trip);
    assertThat(inMemoryRepository.findTrip(ID_1).get().getTripId(), is(ID_1));
  }

  @Test
  public void tripNotFoundTest(){
    assertThat(inMemoryRepository.findTrip(ID_2).isPresent(), is(FALSE));
  }
}
