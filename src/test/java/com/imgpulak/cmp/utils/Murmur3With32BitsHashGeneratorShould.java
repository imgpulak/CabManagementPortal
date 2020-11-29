package com.imgpulak.cmp.utils;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Murmur3With32BitsHashGeneratorShould {

  private GoogleHashGenerator murmur3With32BitsHashGenerator;

  @Before
  public void setUp() {
    this.murmur3With32BitsHashGenerator = new GoogleHashGenerator();
  }

  @Test public void
  generate_339d3b53_for_osoco_url() {
    assertThat(murmur3With32BitsHashGenerator.generateId("http://www.osoco.es"), is("339d3b53"));
  }
}
