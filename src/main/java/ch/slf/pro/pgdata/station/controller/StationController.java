package ch.slf.pro.osper.station.controller;

import ch.slf.pro.osper.station.ApplicationFields;
import ch.slf.pro.osper.station.model.Network;
import ch.slf.pro.osper.station.service.NetworkService;
import ch.slf.pro.osper.station.service.StationMetadataSerivce;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by kryvych on 30/09/16.
 */
@RestController
@RequestMapping(ApplicationFields.REST_METADATA)
@Api(value = "Controller for accessing station metadata data")
public class StationController {

    private static final Logger log = LoggerFactory.getLogger(StationController.class);

    @Autowired
    private StationMetadataSerivce service;

    @Autowired
    private NetworkService networkService;


    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(
            value = "stations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get data", notes = "Returns station metadata in GeoJSON format. ", response = String.class)

    public FeatureCollection getAllStations(@RequestParam(value = "networks", required=false) Set<String> networks) {
        return service.getStations(networks);
    }


    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(
            value = "stations/{stationId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get data", notes = "Returns station metadata in GeoJSON format. ", response = String.class)

    public Feature getStationInfo(
            @PathVariable(value = "stationId") @ApiParam(value = "Station id", required = true) Long stationId
    ) {
        return service.getStationInfo(stationId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(
            value = "networks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get data", notes = "Returns network names. ", response = String.class)

    public List<Network> getAllNetworks() {
        return networkService.getNetworks();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(
            value = "networks/{network}/stations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get data", notes = "Returns networks with a list of their stations. ", response = String.class)

    public FeatureCollection getNetworksWithStations(
            @PathVariable(value = "network") @ApiParam(value = "Network code", required = true) String network
    ) {
        return service.getStations(Sets.newHashSet(network));
    }



}
