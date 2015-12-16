package io.gof.tender.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gof.tender.BaseTester;
import io.gof.tender.domain.Location;
import io.gof.tender.domain.Project;
import io.gof.tender.domain.Tuple;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class ProjectLoader extends BaseTester {
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
        }
    }

    private String getStr(Object str){
        return str != null ? StringUtils.trim((String) str) : null;
    }
}
