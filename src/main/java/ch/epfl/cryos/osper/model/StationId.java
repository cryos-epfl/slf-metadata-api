package ch.epfl.cryos.osper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by kryvych on 17/11/16.
 */
public class StationId implements Serializable{

    private String network;

    private String shortName;

    private int stationNumber;

    public StationId() {
    }

    public StationId(String network, String shortName, int stationNumber) {
        this.network = network;
        this.shortName = shortName;
        this.stationNumber = stationNumber;
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

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationId stationId = (StationId) o;

        if (stationNumber != stationId.stationNumber) return false;
        if (!network.equals(stationId.network)) return false;
        return shortName.equals(stationId.shortName);

    }

    @Override
    public int hashCode() {
        int result = network.hashCode();
        result = 31 * result + shortName.hashCode();
        result = 31 * result + stationNumber;
        return result;
    }
}
