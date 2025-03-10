package com.example.aiProjects.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class MarketDataResponse {
    @JsonProperty("Meta Data")
    private Map<String, String> metaData;

    @JsonProperty("Weekly Time Series")
    private Map<String, MarketData> weeklyTimeSeries;

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public Map<String, MarketData> getWeeklyTimeSeries() {
        return weeklyTimeSeries;
    }

    public void setWeeklyTimeSeries(Map<String, MarketData> weeklyTimeSeries) {
        this.weeklyTimeSeries = weeklyTimeSeries;
        if (weeklyTimeSeries != null) {
            weeklyTimeSeries.forEach((date, marketData) -> marketData.setDate(date));
        }
    }
}