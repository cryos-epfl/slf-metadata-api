package ch.epfl.cryos.osper.station.service;

import ch.epfl.cryos.osper.station.model.Station;
import ch.epfl.cryos.osper.station.repository.StationMetadataRepository;
import ch.slf.pro.common.util.exception.SlfProRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

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
            throw  SlfProRuntimeException.builder("Station with ID " + stationId + " in not found.", "dae.noStation")
                    .status(HttpStatus.NOT_FOUND).build();        }
        return featureBuilder.buildSingleFeature(station);
    }


}
