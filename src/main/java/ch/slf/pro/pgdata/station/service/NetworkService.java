package ch.slf.pro.pgdata.station.service;

import ch.slf.pro.pgdata.station.model.Network;
import ch.slf.pro.pgdata.station.repository.NetworkRepository;
import ch.slf.pro.pgdata.station.repository.StationMetadataRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;
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
        List<Network> networks = networkRepository.findAll();
        networks.sort(Comparator.comparing(Network::getCode));
        return networks;
    }


}
