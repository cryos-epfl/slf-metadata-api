package ch.slf.pro.pgdata.station.service;

import ch.slf.pro.pgdata.station.model.Station;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;

import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by kryvych on 15/11/16.
 */
@Named
public class GeoJsonFeatureBuilder {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public GeoJsonFeatureBuilder() {
    }

    FeatureCollection buildFeatureCollection(List<Station> stations) {
        FeatureCollection features = new FeatureCollection();

        stations.forEach(station -> features.add(buildSingleFeature(station)));
        return features;
    }

    Feature buildSingleFeature(Station station) {
        Feature feature = new Feature();
        Point point = new Point(station.getLongitudeWgs84(), station.getLatitudeWgs84(), station.getAltitude());
        feature.setGeometry(point);

        feature.setProperty("id", station.getId());
        feature.setProperty("name", station.getCode());
        feature.setProperty("network", station.getNetwork());
        feature.setProperty("firstMeasureDate", station.getFirstMeasureDate() != null ? dateFormat.format(station.getFirstMeasureDate()) : null);
        feature.setProperty("lastMeasureDate", station.getLastMeasureDate() != null ? dateFormat.format(station.getLastMeasureDate()) : null);
        feature.setProperty("description", station.getDescription());
        feature.setProperty("locationName", station.getLocationName());

        return feature;
    }


}
