package io.gof.tender.repository;

import io.gof.tender.domain.Bid;
import io.gof.tender.domain.Project;
import io.gof.tender.domain.Representative;
import io.gof.tender.domain.Vendor;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends GraphRepository<Bid> {
    @Query( " MATCH (vendor:Vendor), (project:Project), (bid:Bid), (rep:Representative) " +
            " WHERE id(vendor)  = {0} " +
            "   AND id(project) = {1} " +
            "   AND id(bid)     = {2} " +
            "   AND id(rep)     = {3} " +
            "CREATE UNIQUE (project) -[:HAS_BIDDING]-> (bid) " +
            "CREATE UNIQUE (bid) -[:REPRESENTED_BY]-> (rep) " +
            "CREATE UNIQUE (bid) -[:FROM]-> (vendor); ")
    void bid(Vendor vendor, Project project, Bid bid, Representative rep);

    @Query( " MATCH (vendor:Vendor), (project:Project), (bid:Bid) " +
            " WHERE id(vendor)  = {0} " +
            "   AND id(project) = {1} " +
            "   AND id(bid)     = {2} " +
            "CREATE UNIQUE (project) -[:HAS_BIDDING]-> (bid) " +
            "CREATE UNIQUE (bid) -[:FROM]-> (vendor); ")
    void bid(Vendor vendor, Project project, Bid bid);
}
