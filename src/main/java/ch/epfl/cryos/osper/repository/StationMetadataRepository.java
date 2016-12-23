package ch.epfl.cryos.osper.repository;

import ch.epfl.cryos.osper.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kryvych on 09/11/16.
 */
@Repository
public interface StationMetadataRepository extends JpaRepository<Station, Long> {

    @Query("select s from Station s where s.longitudeWgs84 is not null and s.latitudeWgs84 is not null and s.firstMeasureDate is not null")
    List<Station> findAll();

    @Query("select s from Station s " +
            "where s.network = :network and " +
            "s.longitudeWgs84 is not null and s.latitudeWgs84 is not null and s.firstMeasureDate is not null")
    List<Station> findByNetworkCustom(@Param("network") String network);

    Station findOne(Long id);



}