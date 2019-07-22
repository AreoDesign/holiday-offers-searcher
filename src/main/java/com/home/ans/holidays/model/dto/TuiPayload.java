package com.home.ans.holidays.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TuiPayload {
    private String offerType;
    private List<String> childrenBirthdays;
    private List<String> departuresCodes;
    private List<String> destinationsCodes;
    private Integer numberOfAdults;
    private String departureDateFrom;
    private String durationFrom;
    private String durationTo;
    private String site;
    private Metadata metadata;
    private List<Filter> filters;

    @Data
    @AllArgsConstructor
    public class Metadata {
        private Integer page;
        private Integer pageSize;
        private String sorting;
    }

    @Data
    @AllArgsConstructor
    public class Filter {
        private String filterId;
        private List<String> selectedValues;
    }

}
