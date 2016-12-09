package ch.epfl.cryos.osper.service;

import ch.epfl.cryos.osper.model.Station;
import ch.epfl.cryos.osper.repository.StationMetadataRepository;
import ch.epfl.cryos.osper.repository.StationNameResolver;
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

    private final StationNameResolver nameResolver;

    @Inject
    public StationMetadataSerivce(StationMetadataRepository metadataRepository, GeoJsonFeatureBuilder featureBuilder, StationNameResolver nameResolver) {
        this.metadataRepository = metadataRepository;
        this.featureBuilder = featureBuilder;
        this.nameResolver = nameResolver;
    }

    public FeatureCollection getStations(String network) {

        List<Station> stations = null;
        if (StringUtils.isEmpty(network)) {
            stations = metadataRepository.findAll();
        } else {
            stations = metadataRepository.findByNetworkCuatom(network.toUpperCase());
        }

        return featureBuilder.buildFeatureCollection(stations);
    }

    public Feature getStationInfo(String stationName) {
        Station station = metadataRepository.findOne(nameResolver.buildStationId(stationName));
        if (station == null) {
            throw new NoSuchElementException("Station with name " + stationName + " in not found.");
        }
        return featureBuilder.buildSingleFeature(station);
    }


}
