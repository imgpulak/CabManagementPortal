package com.imgpulak.cmp.service;


import com.imgpulak.cmp.dto.CabDto;

import com.imgpulak.cmp.interfaces.CabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

@Service
public final class CabManagementService {
  private final CabRepository cabRepository;

  @Autowired
  public CabManagementService(final CabRepository cabRepository) {
    this.cabRepository = cabRepository;
  }

  public void registerCab(CabDto cabDto) throws MalformedURLException {
    String cabId = cabDto.getCabId();
    cabRepository.save(cabId, cabDto);
  }
}
