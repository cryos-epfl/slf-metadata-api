package ch.epfl.cryos.osper.repository;

import ch.epfl.cryos.osper.model.StationId;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import javax.inject.Named;

/**
 * Created by kryvych on 10/11/16.
 */
@Named
public class StationNameResolver {

    private String SEPARATOR = ":";

    public StationNameResolver() {
    }

    public String buildName(String network, String stationName, int number) {
        return Joiner.on(SEPARATOR).join(network, stationName, number);
    }

    public StationId buildStationId(String fullName) {
        return new StationId(getNetwork(fullName), getStation(fullName), getStationNumber(fullName));
    }
    public String getNetwork(String fullName) {
        return getNamePart(fullName, 0).toUpperCase();
    }

    public String getStation(String fullName) {
        return getNamePart(fullName, 1).toUpperCase();
    }

    public int getStationNumber( String fullName) {
        return Integer.valueOf(getNamePart(fullName, 2));
    }

    private String getNamePart(String fullName, int position) {
        validateName(fullName);
        return Splitter.on(SEPARATOR).splitToList(fullName).get(position);
    }

    private void validateName(String fullName) {
        if (Splitter.on(SEPARATOR).splitToList(fullName).size() < 3) {
            throw new IllegalArgumentException("Station name should be formatted as: NETWORK_STATION_NUMBER");
        }

    }
}
