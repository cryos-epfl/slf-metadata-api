package ch.epfl.cryos.osper.station.repository;

import ch.epfl.cryos.osper.station.model.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kryvych on 21/11/16.
 */
@Repository
public interface NetworkRepository extends JpaRepository<Network, String> {


}

