package ch.epfl.cryos.osper.model;

import ch.epfl.cryos.osper.ApplicationFields;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by kryvych on 19/10/16.
 */
@Entity
@IdClass(StationId.class)
@Table(name = "sdbo_station2_vstationstandort", schema = ApplicationFields.SCHEMA)
public class Station implements Serializable {

    @Id
    @Column(name = "netz")
    @NotNull
    private String network;

    @Id
    @Column(name = "stat_abk")
    @NotNull
    private String shortName;

    @Id
    @Column(name = "stao_nr")
    @NotNull
    private int stationNumber;

    @Column(name = "date_first_measure", columnDefinition = "timestamp without time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstMeasureDate;

    @Column(name = "date_last_measure", columnDefinition = "timestamp without time zone", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMeasureDate;

    @Column(name = "label")
    private String description;

    @Column(name = "stat_name")
    private String locationName;

    @Column(name = "wgs84_longitude")
    private double longitudeWgs84;

    @Column(name = "wgs84_latitude")
    private double latitudeWgs84;

    @Column(name="stao_h")
    private Double altitude;

    @Transient
    private List<Timeserie> timeseries;

    private Station() {
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String name) {
        this.shortName = name;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public Date getFirstMeasureDate() {
        return firstMeasureDate;
    }

    public void setFirstMeasureDate(Date firstMeasureDate) {
        this.firstMeasureDate = firstMeasureDate;
    }

    public Date getLastMeasureDate() {
        return lastMeasureDate;
    }

    public void setLastMeasureDate(Date lastMeasureDate) {
        if (lastMeasureDate == null) {
            System.out.println("Station.setLastMeasureDate");
            lastMeasureDate = null;
        } else {

            this.lastMeasureDate = lastMeasureDate;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLongitudeWgs84() {
        return longitudeWgs84;
    }

    public void setLongitudeWgs84(double longitudeWgs84) {
        this.longitudeWgs84 = longitudeWgs84;
    }

    public double getLatitudeWgs84() {
        return latitudeWgs84;
    }

    public void setLatitudeWgs84(double latitudeWgs84) {
        this.latitudeWgs84 = latitudeWgs84;
    }

    public double getAltitude() {
        if (altitude == null) {
            return 0.0;
        } else {
            return altitude;
        }
    }

    public void setAltitude(Double altitude) {
        if (altitude == null) {
            this.altitude = 0.0;
        }
        else {
            this.altitude = altitude;
        }
    }

    public List<Timeserie> getTimeseries() {
        return timeseries;
    }

    public void setTimeseries(List<Timeserie> timeseries) {
        this.timeseries = timeseries;
    }

    @Override
    public String toString() {
        return "Station{" +
                "network='" + network + '\'' +
                ", shortName='" + shortName + '\'' +
                ", stationNumber=" + stationNumber +
                ", firstMeasureDate=" + firstMeasureDate +
                ", lastMeasureDate=" + lastMeasureDate +
                ", description='" + description + '\'' +
                ", locationName='" + locationName + '\'' +
                ", longitudeWgs84=" + longitudeWgs84 +
                ", latitudeWgs84=" + latitudeWgs84 +
                ", altitude=" + altitude +
                '}';
    }
}
