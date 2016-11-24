package ch.epfl.cryos.osper.service;

import ch.epfl.cryos.osper.model.Station;
import ch.epfl.cryos.osper.repository.StationNameResolver;
import ch.epfl.cryos.osper.repository.TimeserieRepository;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Created by kryvych on 15/11/16.
 */
@Named
public class GeoJsonFeatureBuilder {

    private final StationNameResolver stationNameResolver;

    private final TimeserieRepository timeserieRepository;

    @Inject
    public GeoJsonFeatureBuilder(StationNameResolver stationNameResolver, TimeserieRepository timeserieRepository) {
        this.stationNameResolver = stationNameResolver;
        this.timeserieRepository = timeserieRepository;
    }

    public FeatureCollection buildFeatureCollection(List<Station> stations) {
        FeatureCollection features = new FeatureCollection();
//        for (Station station : stations) {
//
//                features.add(buildFeature(station));
//
//        }

        stations.forEach(station -> features.add(buildFeature(station)));
        return features;
    }

    public Feature buildSingleFeature(Station station) {
        Feature feature = buildFeature(station);
        addTimeseries(feature);
        return feature;
    }

    protected Feature buildFeature(Station station) {
        Feature feature = new Feature();
        Point point = new Point(station.getLongitudeWgs84(), station.getLatitudeWgs84(), station.getAltitude());
        feature.setGeometry(point);
        String fullName = stationNameResolver.buildName(
                station.getNetwork(), station.getShortName(), station.getStationNumber());
        feature.setProperty("name", fullName);
        feature.setProperty("network", station.getNetwork());
        feature.setProperty("firstMeasureDate", station.getFirstMeasureDate());
        feature.setProperty("lastMeasureDate", station.getLastMeasureDate());
        feature.setProperty("description", station.getDescription());
        feature.setProperty("locationName", station.getLocationName());
        feature.setProperty("parameters", timeserieRepository.getMeasurandDescriptionByStation(feature.getProperty("name")));

        return feature;
    }

    protected void addTimeseries(Feature feature) {
        feature.setProperty("timeseries", timeserieRepository.getTimeserieForStation(feature.getProperty("name")));
    }

    protected void addParameters(Feature feature) {
        feature.setProperty("parameters", timeserieRepository.getMeasurandDescriptionByStation(feature.getProperty("name")));

    }


}
