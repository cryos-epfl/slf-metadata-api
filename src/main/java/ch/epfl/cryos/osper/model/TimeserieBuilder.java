package ch.epfl.cryos.osper.model;

import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeSet;

import java.time.ZonedDateTime;

public class TimeserieBuilder {
    private String description;
    private String code;
    private int deviceId;
    private String deviceType;
    private int aggregationInterval;
    private String aggregationType;
    private int relativePosition;
    private String unit;
    private TreeRangeSet<ZonedDateTime> activeRanges = TreeRangeSet.create();

    private String stationName;

    public TimeserieBuilder setStationName(String stationName) {
        this.stationName = stationName;
        return this;
    }

    public TimeserieBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TimeserieBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public TimeserieBuilder setDeviceId(int deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public TimeserieBuilder setDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public TimeserieBuilder setAggregationInterval(int aggregationInterval) {
        this.aggregationInterval = aggregationInterval;
        return this;
    }

    public TimeserieBuilder setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
        return this;
    }

    public TimeserieBuilder setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
        return this;
    }

    public TimeserieBuilder setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public TimeserieBuilder addTimeRange(Range<ZonedDateTime> range) {
        activeRanges.add(range);
        return this;
    }

    public Timeserie createTimeserie() {
        return new Timeserie(description, code, deviceId, deviceType, aggregationInterval, aggregationType, relativePosition, unit, stationName, activeRanges);
    }
}