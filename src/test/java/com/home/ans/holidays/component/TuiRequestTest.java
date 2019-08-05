package com.home.ans.holidays.component;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.home.ans.holidays.dictionary.Destination;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TuiRequestTest {

    @Mock
    private Gson gson;

    @InjectMocks
    private TuiRequest tuiRequest;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void verify_translateValues_returnsCorrectCodesForGivenDestinations() {
//        given
        Set<Destination> destinations = ImmutableSet.of(Destination.GREECE);
//        when
        Collection<String> regions = tuiRequest.getRegionsForDestinations(destinations);
//        then
        assertThat(regions).isNotEmpty();
        assertThat(regions).isEqualTo(Arrays.asList("ATH", "ATH3", "CFU", "KGS", "CHQ", "GPA", "RHO", "ZTH", "GR"));
    }

}