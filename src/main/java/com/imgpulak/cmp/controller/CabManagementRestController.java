package com.imgpulak.cmp.controller;

import com.imgpulak.cmp.dto.CabDto;
import com.imgpulak.cmp.fsm.CabState;
import com.imgpulak.cmp.request.model.RegisterCabRequest;
import com.imgpulak.cmp.service.CabManagementService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;


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

  @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> createShortUrl(@RequestBody RegisterCabRequest payload, HttpServletRequest request) {
    LOG.debug("Register Cab API has been called.");
    try {
      CabDto cabDto = new CabDto();
      cabDto.setCabId(payload.getCabId());
      cabDto.setCabState(CabState.valueOf(payload.getCabState()));
      cabManagementService.registerCab(cabDto);
      String responseMsg = new String("{\"status\": \"CREATED\"}");
      return new ResponseEntity<String>(responseMsg, HttpStatus.CREATED);
    } catch (IllegalArgumentException | MalformedURLException ex) {
      LOG.error("Error when trying to register a cab {}", ex.getMessage());
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      LOG.error("Something unexpected went wrong: {}", ex.getMessage());
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
