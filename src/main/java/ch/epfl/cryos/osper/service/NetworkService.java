package ch.epfl.cryos.osper.service;

import ch.epfl.cryos.osper.model.Network;
import ch.epfl.cryos.osper.repository.NetworkRepository;
import ch.epfl.cryos.osper.repository.StationMetadataRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by kryvych on 21/11/16.
 */
@Service
public class NetworkService {

    private final NetworkRepository networkRepository;
    private final StationMetadataRepository stationMetadataRepository;

    @Inject
    public NetworkService(NetworkRepository networkRepository, StationMetadataRepository stationMetadataRepository) {
        this.networkRepository = networkRepository;
        this.stationMetadataRepository = stationMetadataRepository;
    }


    public List<Network> getNetworks() {
        return networkRepository.findAll();
    }


}
