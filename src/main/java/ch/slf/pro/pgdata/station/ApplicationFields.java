package ch.slf.pro.pgdata.station;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Contains global application definition fields (constants).
 *
 * @author pertschy@slf.ch, Jun 15, 2015
 */
public class ApplicationFields {

    public static final ZoneId ZONE_ID = ZoneId.of("Europe/Zurich");
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone(ZONE_ID);

    public static final String SCHEMA = "data_quality.station";

    public static final String SECURITY_BUNDLE = "AVAL";
    public static final String BASE_SCAN_PACKAGES =
            "ch.slf.pro.pgdata.station.configuration,"
                    + "ch.slf.pro.pgdata.station.controller,"
                    + "ch.slf.pro.pgdata.station.repository,"
                    + "ch.slf.pro.pgdata.station.service"

            ;

    public static final String[] MODEL_SCAN_PACKAGES = {"ch.slf.pro.pgdata.station.model"};
    public static final String REPOSITORY_SCAN_PACKAGE = "ch.slf.pro.pgdata.station.repository";

    public static final String REST_MAPPING_COMPONENT_INFO = "/info";
    public static final String REST_METADATA = "/metadata";
    public static final String REST_MAPPING_EXCEPTION = "exception";

//    public static final int REPOSITORY_SPATIAL_ID = OracleCrsSridMap.SRID_EPSG4326;


    private ApplicationFields() {
        throw new AssertionError();
    }

}
