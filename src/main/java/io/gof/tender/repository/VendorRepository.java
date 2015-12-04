package io.gof.tender.repository;

import io.gof.tender.domain.Vendor;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends GraphRepository<Vendor> {
    @Query( " MATCH (vendor:Vendor) " +
            " WHERE vendor.name = {0} " +
            "RETURN vendor; ")
    Vendor findByName(String name);

    @Query( " MATCH (vendor:Vendor) " +
            " WHERE vendor.businessRegistrationId = {0} " +
            "RETURN vendor; ")
    Vendor findByBusinessRegistrationId(String id);
}
