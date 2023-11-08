package com.airepublic.wego.exercise.carparkinfo.util;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class CarparkInfoJsonMapping {
    @JsonAlias("total_lots")
    public String totalLots;
    @JsonAlias("lots_available")
    public String availableLots;

    @JsonAnySetter
    public void ignore(final String key, final String value) {
    }
}
