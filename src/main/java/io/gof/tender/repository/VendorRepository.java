package io.gof.tender.repository;

import io.gof.tender.domain.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, String> {
    Vendor findByBusinessRegistrationId(String code);
}
