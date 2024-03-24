package org.example.ipsearcher.repository;

import org.example.ipsearcher.model.ServerTraffic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ServerTraffic, Long> {
    ServerTraffic findByUsername(String username);
}
