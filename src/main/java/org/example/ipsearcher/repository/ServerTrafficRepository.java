package org.example.ipsearcher.repository;

import org.example.ipsearcher.model.ServerTraffic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerTrafficRepository extends JpaRepository<ServerTraffic, Long> {
    ServerTraffic findByTrafficName(String trafficName);
}
