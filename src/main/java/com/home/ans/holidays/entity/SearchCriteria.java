package com.home.ans.holidays.entity;

import com.home.ans.holidays.dictionary.BoardType;
import com.home.ans.holidays.dictionary.City;
import com.home.ans.holidays.dictionary.Destination;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "search_criteria")
@Data
@Slf4j
public class SearchCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "requestor_id", referencedColumnName = "id", nullable = false)
    private Requestor requestor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "searchCriteria")
    private AlertCriteria alertCriteria;

    private List<LocalDate> childrenBirthDates;

    @NonNull
    @NotBlank(message = "must define at least one adult person birth date!")
    private List<LocalDate> adultsBirthDates;

    private LocalDate departureDateFrom;

    @Nullable
    private LocalDate departureDateTo;

    @Nullable //when null then assign any | all stay length
    @Min(value = 3, message = "minimum stay length must not be less than 1 day!")
    @Max(value = 30, message = "minimum stay length must not be greater than 30 days!")
    private Integer stayLength;

    @NonNull
    @NotBlank(message = "must define at least one departure city!")
    @Enumerated(EnumType.STRING)
    private Set<City> departureCities;

    @Enumerated(EnumType.STRING)
    private Set<BoardType> boardTypes;

    @Enumerated(EnumType.STRING)
    private Set<Destination> destinations;

    @Min(value = 1, message = "Minimum hotel standard is one star! *")
    @Max(value = 5, message = "Maximum hotel standard is five stars! *****")
    @NotNull(message = "minimum hotel standard cannot be null! Please select value from 1 to 5 (stars).")
    private Integer minHotelStandard; //hotel standard to be defined in 'stars' unit

    private LocalDateTime creationTime;

    private boolean isActive;

    @Builder
    public SearchCriteria(@Nullable List<LocalDate> childrenBirthDates, @NotNull List<LocalDate> adultsBirthDates,
                          @Nullable LocalDate departureDateFrom, @Nullable LocalDate departureDateTo, @Nullable Integer stayLength,
                          @NotNull Set<City> departureCities, @Nullable Set<BoardType> boardTypes,
                          @Nullable Set<Destination> destinations, @Nullable Integer minHotelStandard) {
        this.childrenBirthDates = Objects.isNull(childrenBirthDates) ? Collections.emptyList() : childrenBirthDates;
        this.adultsBirthDates = adultsBirthDates;
        this.departureDateFrom = Objects.isNull(departureDateFrom) ? LocalDate.now() : departureDateFrom;
        this.departureDateTo = departureDateTo;
        this.stayLength = stayLength;
        this.departureCities = departureCities;
        this.boardTypes = boardTypes;
        this.destinations = destinations;
        validateAndAssignMinHotelStandard(minHotelStandard);
        this.creationTime = LocalDateTime.now();
        this.isActive = true;
    }

    private void validateAndAssignMinHotelStandard(Integer minHotelStandard) {
        if (Objects.isNull(minHotelStandard)) {
            log.warn("Given minimum hotel standard is null. Default value of 3 stars will be used.");
            this.minHotelStandard = 3;
        } else {
            this.minHotelStandard = minHotelStandard;
        }
    }
}
