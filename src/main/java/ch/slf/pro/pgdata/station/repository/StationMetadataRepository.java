package ch.slf.pro.pgdata.station.repository;

import ch.slf.pro.pgdata.station.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by kryvych on 09/11/16.
 */
@Repository
public interface StationMetadataRepository extends JpaRepository<Station, Long> {

    @Query("select s from Station s where s.longitudeWgs84 is not null and s.latitudeWgs84 is not null")
    List<Station> findAll();

    @Query("select s from Station s " +
            "where s.network in (:networks) and " +
            "s.longitudeWgs84 is not null and s.latitudeWgs84 is not null")
    List<Station> findByNetworkCustom(@Param("networks") Collection<String> networks);

    Station findOne(Long id);



}