package com.imgpulak.cmp.interfaces;

import com.imgpulak.cmp.dto.CabDto;

import java.util.Optional;

public interface CabRepository {
  void save(String cabId, CabDto cabDto);
  Optional<CabDto> findCab(String cabId);
}
