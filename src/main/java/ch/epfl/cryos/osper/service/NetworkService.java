package ch.epfl.cryos.osper.service;

import ch.epfl.cryos.osper.repository.NetworkRepository;
import ch.epfl.cryos.osper.repository.TimeserieRepository;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by kryvych on 21/11/16.
 */
@Service
public class NetworkService {

    private final NetworkRepository networkRepository;

    private final TimeserieRepository timeserieRepository;

    @Inject
    public NetworkService(NetworkRepository networkRepository, TimeserieRepository timeserieRepository) {
        this.networkRepository = networkRepository;
        this.timeserieRepository = timeserieRepository;
    }

    public Map<String, Collection<String>> getNetWorksWithStations() {
        return networkRepository.getAllNetworksWithStations();
    }

    public List<String> getNetworks() {
        return networkRepository.getNetworks();
    }

    public Map<String, List<String>> getNetWorksWithParameters() {
        Map<String, List<String>> result = Maps.newHashMap();
        List<String> networks = networkRepository.getNetworks();
        for (String network : networks) {
            result.put(network, timeserieRepository.getMeasurandDescriptionByNetwork(network));
        }
        return result;
    }

}
