package io.gof.tender.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import io.gof.tender.BaseTester;
import io.gof.tender.domain.Location;
import io.gof.tender.domain.Project;
import io.gof.tender.domain.Tuple;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectLoader extends BaseTester {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectLoader.class);

    @Test
    public void loadJSONFile() throws Exception{
        File projectsDir = new File("projects");
        for(File file : projectsDir.listFiles()){
            Map projectMap = new ObjectMapper().readValue(file, Map.class);
            Project project = Project.builder()
                .code(getStr(projectMap.get("code")))
                .name(getStr(projectMap.get("name")))
                .biddingCode(getStr(projectMap.get("code")))
                .biddingStepsLink(getStr(projectMap.get("biddingStepsLink")))
                .biddingDateStr(getStr(projectMap.get("date")))
                .category(getStr(projectMap.get("category ")))
                .desc(getStr(projectMap.get("desc")))
                .fiscalYear(getStr(projectMap.get("fiscalYear")))
                .organizer(getStr(projectMap.get("organizer")))
                .workUnit(getStr(projectMap.get("workUnit")))
            .build();

            Map contractType = (Map) projectMap.get("contractType");
            if(contractType != null){
                project.setContractType(
                    Project.ContractType.builder()
                        .fiscalYearImposition(getStr(contractType.get("fiscalYearImposition")))
                        .paymentMethod(getStr(contractType.get("paymentMethod")))
                        .sourceOfFund(getStr(contractType.get("paymentMethod")))
                    .build()
                );
            }

            Map method = (Map) projectMap.get("method");
            if(method != null){
                project.setMethod(
                    Project.Method.builder()
                        .biddingMethod(getStr(method.get("biddingMethod")))
                        .documentMethod(getStr(method.get("documentMethod")))
                        .evaluationMethod(getStr(method.get("evaluationMethod")))
                        .qualificationMethod(getStr(method.get("qualificationMethod")))
                    .build()
                );
            }

            Map price = (Map) projectMap.get("price");
            if(price != null){
                project.setPrice(
                    Project.Price.builder()
                        .ceiling(getStr(price.get("ceiling")))
                        .estimated(getStr(price.get("estimated")))
                    .build()
                );
            }

            Map qualification = (Map) projectMap.get("qualification");
            if(qualification != null){
                ArrayList<Map> permitsMap = (ArrayList<Map>)qualification.get("permits");
                Tuple[] permits = new Tuple[]{};
                Tuple[] requirements = new Tuple[]{};

                if(permitsMap != null){
                    for(Map permit : permitsMap){
                        permits = ArrayUtils.add(permits,
                            Tuple.builder()
                                .code(getStr(permit.get("code")))
                                .name(getStr(permit.get("name")))
                            .build());
                    }
                }

                ArrayList<Map> requirementsMap = (ArrayList<Map>)qualification.get("requirements");
                if(requirementsMap != null){
                    for(Map requirement : requirementsMap){
                        requirements = ArrayUtils.add(requirements,
                            Tuple.builder()
                                .code(getStr(requirement.get("code")))
                                .name(getStr(requirement.get("name")))
                            .build());
                    }
                }

                project.setQualification(
                    Project.Qualification.builder()
                        .permits(permits)
                        .requirements(requirements)
                    .build()
                );
            }

            project = projects.save(project);

            if(projectMap.get("locations") != null){
                for(Map locationMap : (ArrayList<Map>)projectMap.get("locations")){
                    Location location = Location.builder()
                        .project(project)
                        .address(getStr(locationMap.get("address")))
                    .build();

                    locations.save(location);
                }
            }

            LOG.info("Project : " + project.getCode() + " done");
        }
    }

    @Test
    public void loadGeoLoc() throws Exception{
        List<Location> locationsList = locations.findAll();

        Geocoder geocoder = new Geocoder();
        for(Location location: locationsList){
            if (location.getAddress() != null) {
                String address = null;
                String[] addresses = StringUtils.split(location.getAddress(), "-");
                if(addresses.length > 1){
                    if(StringUtils.isNotEmpty(StringUtils.trim(addresses[1]))){
                        address = location.getAddress();
                    }else {
                        address = addresses[0];
                    }
                } else {
                    address = StringUtils.trim(location.getAddress().replace("-", ""));
                }

                GeocodeResponse response = geocoder.geocode(new GeocoderRequest(address,"id"));

                if(response.getResults() != null && response.getResults().size() > 0){
                    saveLocations(location, response.getResults().get(0));
                } else if (addresses.length > 1){
                    address = StringUtils.trim(addresses[0]);
                    response = geocoder.geocode(new GeocoderRequest(address,"id"));

                    if(response.getResults() != null && response.getResults().size() > 0){
                        saveLocations(location, response.getResults().get(0));
                    }else {
                        address = StringUtils.trim(addresses[1]);
                        response = geocoder.geocode(new GeocoderRequest(address,"id"));

                        if(response.getResults() != null && response.getResults().size() > 0){
                            saveLocations(location, response.getResults().get(0));
                        }else {
                            LOG.debug("address not found : " + location.getAddress());
                        }
                    }
                }
            }
        }
    }

    private void saveLocations(Location location, GeocoderResult result){
        LatLng coordinate = result.getGeometry().getLocation();
        location.setCoordinate(new double[]{coordinate.getLat().doubleValue(), coordinate.getLng().doubleValue()});
        location.setGeolocAddress(result.getFormattedAddress());

        locations.save(location);
    }

    private String getStr(Object str){
        return str != null ? StringUtils.trim((String) str) : null;
    }
}
