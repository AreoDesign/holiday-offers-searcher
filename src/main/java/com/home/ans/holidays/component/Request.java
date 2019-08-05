package com.home.ans.holidays.component;

import com.google.gson.Gson;
import com.home.ans.holidays.dictionary.BoardType;
import com.home.ans.holidays.dictionary.City;
import com.home.ans.holidays.dictionary.Destination;
import com.home.ans.holidays.entity.SearchCriteria;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public abstract class Request {
    //beans
    protected Gson gson;
    //search fields
    protected List<LocalDate> childrenBirthDates;
    protected List<LocalDate> adultsBirthDates;
    protected int childrenCount;
    protected int adultsCount;
    protected LocalDate departureDateFrom;
    protected LocalDate departureDateTo;
    protected Integer minStayLength;
    protected Integer maxStayLength;
    protected Collection<String> departureCities;
    protected Collection<String> boardTypes;
    protected Collection<String> destinations;
    protected int minHotelStandard;

    Request(SearchCriteria searchCriteria) {
        this.childrenBirthDates = searchCriteria.getChildrenBirthDates();
        this.adultsBirthDates = searchCriteria.getAdultsBirthDates();
        this.childrenCount = searchCriteria.getChildrenBirthDates().size();
        this.adultsCount = searchCriteria.getAdultsBirthDates().size();
        this.departureDateFrom = searchCriteria.getDepartureDateFrom();
        this.departureDateTo = searchCriteria.getDepartureDateTo();
        this.minStayLength = getPeriodForStayLength(searchCriteria.getStayLength()).getLeft();
        this.maxStayLength = getPeriodForStayLength(searchCriteria.getStayLength()).getRight();
        this.departureCities = getAirportCodesForDepartureCities(searchCriteria.getDepartureCities());
        this.boardTypes = getCodesForBoardTypes(searchCriteria.getBoardTypes());
        this.destinations = getRegionsForDestinations(searchCriteria.getDestinations());
        this.minHotelStandard = searchCriteria.getMinHotelStandard();
    }

    protected abstract Collection<String> getRegionsForDestinations(Collection<Destination> destinations);

    protected abstract Collection<String> getCodesForBoardTypes(Collection<BoardType> boardTypes);

    protected abstract Collection<String> getAirportCodesForDepartureCities(Collection<City> cities);

    protected abstract Pair<Integer, Integer> getPeriodForStayLength(@Nullable Integer stayLength);

    protected abstract void setGson(Gson gson);

}
