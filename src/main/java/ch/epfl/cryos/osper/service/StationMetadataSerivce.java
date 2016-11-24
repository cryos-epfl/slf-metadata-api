package ch.epfl.cryos.osper.service;

import ch.epfl.cryos.osper.model.Station;
import ch.epfl.cryos.osper.model.StationId;
import ch.epfl.cryos.osper.model.Timeserie;
import ch.epfl.cryos.osper.model.TimeserieBuilder;
import ch.epfl.cryos.osper.repository.StationMetadataRepository;
import ch.epfl.cryos.osper.repository.StationNameResolver;
import ch.epfl.cryos.osper.repository.TimeserieRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimeZone;

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

    public FeatureCollection getStations() {

        List<Station> all = metadataRepository.findAll();

        return featureBuilder.buildFeatureCollection(all);
    }

    public Feature getStationInfo(String stationName) {
        Station station = metadataRepository.findOne(nameResolver.buildStationId(stationName));
        if (station == null) {
            throw new NoSuchElementException("Station with name " + stationName + " in not found.");
        }
        return featureBuilder.buildSingleFeature(station);
    }


}
