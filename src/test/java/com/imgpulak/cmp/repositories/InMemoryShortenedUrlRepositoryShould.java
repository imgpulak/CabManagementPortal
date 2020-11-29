package com.imgpulak.cmp.repositories;

import com.imgpulak.cmp.dto.CabDto;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;

import static java.lang.Boolean.FALSE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InMemoryShortenedUrlRepositoryShould {
  private static final URI OSOCO_URI = URI.create("http://www.osoco.es");
  private static final String OSOCO_URL_MURMUR3_HASH = "339d3b53";
  private InMemoryCabRepository inMemoryShortenedUrlRepository;

  @Before
  public void setUp() {
    this.inMemoryShortenedUrlRepository = new InMemoryCabRepository();
  }

  @Test public void
  save_a_new_shortened_url() throws MalformedURLException {
    CabDto shortenedUrlDto = new CabDto();
    shortenedUrlDto.setUrl(OSOCO_URI.toURL());
    inMemoryShortenedUrlRepository.save(OSOCO_URL_MURMUR3_HASH, shortenedUrlDto);
    assertThat(inMemoryShortenedUrlRepository.findURL(OSOCO_URL_MURMUR3_HASH).get().getUrl(), is(OSOCO_URI.toURL()));
  }

  @Test public void
  return_absent_optional_for_a_non_existing_shortened_url() throws MalformedURLException {
    assertThat(inMemoryShortenedUrlRepository.findURL(OSOCO_URL_MURMUR3_HASH).isPresent(), is(FALSE));
  }

}
