package org.example.ipsearcher.repository;

import org.example.ipsearcher.model.VPN;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VPNRepository extends JpaRepository<VPN, Long> {
    VPN findByName(String name);
}
