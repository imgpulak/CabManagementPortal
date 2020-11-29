package com.imgpulak.cmp.controller;

import com.imgpulak.cmp.exception.CityNotFoundException;
import com.imgpulak.cmp.exception.NoCabAvailableException;
import com.imgpulak.cmp.exception.TripNotFoundException;
import com.imgpulak.cmp.model.Cab;
import com.imgpulak.cmp.fsm.CabState;
import com.imgpulak.cmp.model.City;
import com.imgpulak.cmp.model.Trip;
import com.imgpulak.cmp.model.request.InitiateTripRequest;
import com.imgpulak.cmp.model.request.RegisterCabRequest;
import com.imgpulak.cmp.model.request.RegisterCityRequest;
import com.imgpulak.cmp.service.CabManagementService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CabManagementRestController {
  private final static Logger LOG = LoggerFactory.getLogger(CabManagementRestController.class);
  private final CabManagementService cabManagementService;

  @Autowired
  public CabManagementRestController(CabManagementService urlShortenerService) {
    this.cabManagementService = urlShortenerService;
  }

  @RequestMapping("/")
  @ResponseBody
  String home() {
    return "Pong!";
  }

  @RequestMapping(value = "/registerCab", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> registerCab(@RequestBody RegisterCabRequest payload, HttpServletRequest request) {
    LOG.debug("Register Cab API has been called.");
    try {
      Cab cab = new Cab();
      cab.setCabId(payload.getCabId());
      cab.setCabState(CabState.valueOf(payload.getCabState()));
      cab.setCityId(payload.getCityId());
      cabManagementService.registerCab(cab);
      String responseMsg = new String("{\"status\": \"CREATED\"}");
      return new ResponseEntity<String>(responseMsg, HttpStatus.CREATED);
    } catch (IllegalArgumentException ex) {
      LOG.error("Error when trying to register a cab {}", ex.getMessage());
      ex.printStackTrace();
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      LOG.error("Something unexpected went wrong: {}", ex.getMessage());
      ex.printStackTrace();
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/registerCity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> registerCity(@RequestBody RegisterCityRequest payload, HttpServletRequest request) {
    LOG.debug("Register City API has been called.");
    try {
      City city = new City();
      BeanUtils.copyProperties(payload, city);
      cabManagementService.registerCity(city);
      String responseMsg = new String("{\"status\": \"CREATED\"}");
      return new ResponseEntity<String>(responseMsg, HttpStatus.CREATED);
    } catch (IllegalArgumentException ex) {
      LOG.error("Error when trying to register a city {}", ex.getMessage());
      ex.printStackTrace();
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      LOG.error("Something unexpected went wrong: {}", ex.getMessage());
      ex.printStackTrace();
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/initiateTrip", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> initiateTrip(@RequestBody InitiateTripRequest payload, HttpServletRequest request) {
    LOG.debug("Initiate trip API has been called.");
    try {
      Trip trip = new Trip();
      BeanUtils.copyProperties(payload, trip);
      String tripId = cabManagementService.initiateTrip(trip);
      String responseMsg = new String("{\"status\": \"TRIP_STARTED\", \"tripId\":" + tripId + "}");
      return new ResponseEntity<String>(responseMsg, HttpStatus.CREATED);
    } catch (IllegalArgumentException | NoCabAvailableException | CityNotFoundException ex) {
      LOG.error("Error when trying to initiate a trip {}", ex.getMessage());
      ex.printStackTrace();
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      LOG.error("Something unexpected went wrong: {}", ex.getMessage());
      ex.printStackTrace();
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/endTrip/{tripId}", method = RequestMethod.GET)
  ResponseEntity<String> endTrip(@PathVariable String tripId, HttpServletRequest request) {
    LOG.debug("End trip API has been called for tripId={}", tripId);
    try {
      cabManagementService.endTrip(tripId);
      String responseMsg = new String("{\"status\": \"TRIP_ENDED\", \"tripId\":" + tripId + "}");
      return new ResponseEntity<String>(responseMsg, HttpStatus.CREATED);
    } catch (IllegalArgumentException | TripNotFoundException ex) {
      LOG.error("Error when trying to initiate a trip {}", ex.getMessage());
      ex.printStackTrace();
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      LOG.error("Something unexpected went wrong: {}", ex.getMessage());
      ex.printStackTrace();
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
