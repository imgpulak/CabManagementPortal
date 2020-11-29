package com.imgpulak.cmp.repositories;

import com.imgpulak.cmp.dto.CabDto;
import com.imgpulak.cmp.interfaces.CabRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public final class InMemoryCabRepository implements CabRepository {
  private final static Logger LOG = LoggerFactory.getLogger(InMemoryCabRepository.class);
  private Map<String, CabDto> cabStore = new ConcurrentHashMap<String, CabDto>();

  @Override
  public void save(String cabId, CabDto cabDto) {
    cabStore.put(cabId, cabDto);
    LOG.debug("Cab with id={} is successfully saved", cabId);
  }

  @Override
  public Optional<CabDto> findCab(String cabId) {
    return Optional.ofNullable(cabStore.get(cabId));
  }
}
