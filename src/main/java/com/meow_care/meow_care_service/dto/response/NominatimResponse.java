package com.meow_care.meow_care_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimResponse {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;

    @JsonProperty("display_name")
    private String displayName;

}
