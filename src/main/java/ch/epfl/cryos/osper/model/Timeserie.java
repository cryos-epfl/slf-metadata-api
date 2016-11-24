package ch.epfl.cryos.osper.model;

import ch.epfl.cryos.osper.util.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeSet;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Created by kryvych on 19/10/16.
 */
public class Timeserie {

    private long id;
    
    @JsonView(Profile.ListView.class)
    private String description;
    @JsonView(Profile.ListView.class)
    private String measurandCode;
    private int deviceId;
    private String deviceType;
   @JsonIgnore
    private int aggregationInterval;
    @JsonIgnore
    private String aggregationType;
    @JsonIgnore
    private int relativePosition;
    
    private String unit;

    private String stationName;

    private TreeRangeSet<ZonedDateTime> activeRanges;

    
    Timeserie(String description, String code, int deviceId, String deviceType, int aggregationInterval, String aggregationType, int relativePosition, String unit, String stationName, TreeRangeSet<ZonedDateTime> activeRanges) {
        this.description = description;
        this.measurandCode = code;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.aggregationInterval = aggregationInterval;
        this.aggregationType = aggregationType;
        this.relativePosition = relativePosition;
        this.unit = unit;
        this.stationName = stationName;
        this.activeRanges = activeRanges;
    }

@JsonView(Profile.StationView.class)
    public Range<ZonedDateTime> getActiveRange() {
       return activeRanges.span();
    }

@JsonView(Profile.StationView.class)
    public Set<Range<ZonedDateTime>> getAllTimeRanges() {
        return activeRanges.asRanges();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeasurandCode() {
        return measurandCode;
    }

    public void setMeasurandCode(String code) {
        this.measurandCode = code;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getAggregationInterval() {
        return aggregationInterval;
    }

    public void setAggregationInterval(int aggregationInterval) {
        this.aggregationInterval = aggregationInterval;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public int getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStationName() {
        return stationName;
    }
}
