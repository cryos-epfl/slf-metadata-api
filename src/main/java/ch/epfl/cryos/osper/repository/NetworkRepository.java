package ch.epfl.cryos.osper.repository;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by kryvych on 21/11/16.
 */
@Repository
public class NetworkRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StationNameResolver nameResolver;

    @Inject
    public NetworkRepository(JdbcTemplate jdbcTemplate, StationNameResolver nameResolver) {
        this.jdbcTemplate = jdbcTemplate;
        this.nameResolver = nameResolver;
    }

    public Map<String, Collection<String>> getAllNetworksWithStations() {
        ListMultimap<String, String> result =
                Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);


        jdbcTemplate.query("select stat_abk, stao_nr, netz from data_quality.sdbo_station2_vstationstandort where netz is not NULL ",
                (resultSet, i) -> {
                    result.put(resultSet.getString("netz"),
                            nameResolver.buildName(
                                    resultSet.getString("netz"),
                                    resultSet.getString("stat_abk"),
                                    resultSet.getInt("stao_nr")));
                    return resultSet.getString("netz");
                }
        );

        return result.asMap();

    }

    public List<String> getNetworks() {
        return jdbcTemplate.query("select distinct netz from data_quality.sdbo_station2_vstationstandort where netz is not NULL ",
                (resultSet, i) -> {
                    return resultSet.getString("netz");
                });
    }

}
