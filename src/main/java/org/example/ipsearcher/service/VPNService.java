package org.example.ipsearcher.service;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.model.VPN;
import org.example.ipsearcher.repository.VPNRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class VPNService {
    private final VPNRepository vpnRepository;

    public List<VPN> findAllVPN() {
        return vpnRepository.findAll();
    }
}
