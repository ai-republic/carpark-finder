package com.airepublic.wego.exercise.carparkinfo.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class CarparkDataJsonMapping {
    @JsonAlias("carpark_info")
    public List<CarparkInfoJsonMapping> carparkInfo;
    @JsonAlias("carpark_number")
    public String carparkNumber;

    @JsonAnySetter
    public void ignore(final String key, final String value) {
    }

}
