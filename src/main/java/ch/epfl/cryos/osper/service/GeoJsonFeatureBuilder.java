package ch.epfl.cryos.osper.service;

import ch.epfl.cryos.osper.model.Station;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;

import javax.inject.Named;
import java.util.List;

/**
 * Created by kryvych on 15/11/16.
 */
@Named
public class GeoJsonFeatureBuilder {

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
         feature.setProperty("name", station.getShortName());
         feature.setProperty("network", station.getNetwork());
         feature.setProperty("number", station.getStationNumber());
         feature.setProperty("firstMeasureDate", station.getFirstMeasureDate());
         feature.setProperty("lastMeasureDate", station.getLastMeasureDate());
         feature.setProperty("description", station.getDescription());
         feature.setProperty("locationName", station.getLocationName());

         return feature;
    }





}
