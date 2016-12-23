package ch.epfl.cryos.osper.service;

import ch.epfl.cryos.osper.model.Station;
import ch.epfl.cryos.osper.repository.StationMetadataRepository;
import org.apache.commons.lang3.StringUtils;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by kryvych on 19/10/16.
 */
@Service
public class StationMetadataSerivce {

    private final StationMetadataRepository metadataRepository;

    private final GeoJsonFeatureBuilder featureBuilder;

    @Inject
    public StationMetadataSerivce(StationMetadataRepository metadataRepository, GeoJsonFeatureBuilder featureBuilder) {
        this.metadataRepository = metadataRepository;
        this.featureBuilder = featureBuilder;
    }

    public FeatureCollection getStations(String network) {

        List<Station> stations = null;
        if (StringUtils.isEmpty(network)) {
            stations = metadataRepository.findAll();
        } else {
            stations = metadataRepository.findByNetworkCustom(network.toUpperCase());
        }

        return featureBuilder.buildFeatureCollection(stations);
    }

    public Feature getStationInfo(Long stationId) {
        Station station = metadataRepository.findOne(stationId);
        if (station == null) {
            throw new NoSuchElementException("Station with number " + stationId + " is not found.");
        }
        return featureBuilder.buildSingleFeature(station);
    }


}
