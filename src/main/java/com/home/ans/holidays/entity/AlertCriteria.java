package com.home.ans.holidays.entity;


import com.home.ans.holidays.dictionary.Destination;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "alert_criteria")
@Data
@Slf4j
public class AlertCriteria {

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private SearchCriteria searchCriteria;

    @Email
    @NonNull
    @NotBlank(message = "Alert criterion must have at least 1 email defined to send notifications")
    private List<String> eMails;

    @Nullable
    private LocalDate holidayStart;

    @Nullable
    private LocalDate holidayEnd;

    @Nullable
    @Enumerated(EnumType.STRING)
    private Set<Destination> destinations;

    @Nullable
    private Integer priceMax;

    @Nullable
    private Double minHotelStandard;

}
