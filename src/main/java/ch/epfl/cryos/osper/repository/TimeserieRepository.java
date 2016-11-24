package ch.epfl.cryos.osper.repository;

import ch.epfl.cryos.osper.model.Timeserie;
import ch.epfl.cryos.osper.model.TimeserieBuilder;
import com.google.common.collect.Range;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


/**
 * Created by kryvych on 09/11/16.
 */
@Repository
public class TimeserieRepository {

    private final JdbcTemplate jdbcTemplate;

    private final StationNameResolver nameResolver;

    @Inject
    public TimeserieRepository(JdbcTemplate jdbcTemplate, StationNameResolver nameResolver) {
        this.jdbcTemplate = jdbcTemplate;
        this.nameResolver = nameResolver;
    }

    @Transactional(readOnly = true)
    public List<Timeserie> getAllTimeseries() {
        return jdbcTemplate.query("select * from data_quality.v_timeserie", new TimeserieMapper());
    }

    public List<Timeserie> getTimeserieForStation(String fullStationName) {
        return jdbcTemplate.query("select * from data_quality.v_timeserie where network=? and stat_abk=? and stao_nr=?",
                new Object[]{
                        nameResolver.getNetwork(fullStationName),
                        nameResolver.getStation(fullStationName),
                        nameResolver.getStationNumber(fullStationName)},
                new TimeserieMapper());

    }

    public List<String> getMeasurandDescriptionByStation(String fullStationName) {
        return jdbcTemplate.query("select distinct measurand_description from data_quality.v_timeserie where network=? and stat_abk=? and stao_nr=?",
                new Object[]{
                        nameResolver.getNetwork(fullStationName),
                        nameResolver.getStation(fullStationName),
                        nameResolver.getStationNumber(fullStationName)},
                (resultSet, i) -> {
                    return resultSet.getString("measurand_description");
                });
    }

    public List<String> getMeasurandDescriptionByNetwork(String networkName) {
        return jdbcTemplate.query("select distinct measurand_description from data_quality.v_timeserie where network=?",
                new Object[]{networkName},
                (resultSet, i) -> {
                    return resultSet.getString("measurand_description");
                });
    }

    private class TimeserieMapper implements RowMapper<Timeserie> {
        @Override
        public Timeserie mapRow(ResultSet resultSet, int i) throws SQLException {
            TimeserieBuilder builder = new TimeserieBuilder();
            builder.setDeviceType(resultSet.getString("device_code"));
            builder.setCode(resultSet.getString("measurand_code"));
            builder.setStationName(nameResolver.buildName(resultSet.getString("network"), resultSet.getString("stat_abk"), resultSet.getInt("stao_nr")));
            builder.setDescription(resultSet.getString("measurand_description"));
            ZonedDateTime from = ZonedDateTime.ofInstant(resultSet.getTimestamp("since").toInstant(), ZoneId.of("UTC"));
            Timestamp untilTs = resultSet.getTimestamp("until");
            if (untilTs == null) {
                builder.addTimeRange(Range.atLeast(from));
            } else {
                ZonedDateTime until = ZonedDateTime.ofInstant(untilTs.toInstant(), ZoneId.of("UTC"));
                builder.addTimeRange(Range.closedOpen(from, until));
            }

            return builder.createTimeserie();
        }
    }
}
