package org.example.ipsearcher.repository;

import org.example.ipsearcher.model.Vpn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VpnRepository extends JpaRepository<Vpn, Long> {
    Vpn findByName(String name);
}
