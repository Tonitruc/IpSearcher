package org.example.ipsearcher.service;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.dto.request.VpnRequest;
import org.example.ipsearcher.dto.response.IpResponse;
import org.example.ipsearcher.dto.response.VpnResponse;
import org.example.ipsearcher.model.IpEntity;
import org.example.ipsearcher.model.Vpn;
import org.example.ipsearcher.repository.IpRepository;
import org.example.ipsearcher.repository.VPNRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VPNService {
    private final VPNRepository vpnRepository;
    private final IpRepository ipRepository;
    private final IpService ipService;

    public VpnResponse getVPNById(@PathVariable Long id) {
        Optional<Vpn> existVPN = vpnRepository.findById(id);
        return existVPN.map(vpn -> new VpnResponse(vpn.getName(), vpn.getIpEntities().stream()
                .map(IpEntity::getQuery)
                .collect(Collectors.toList()))).orElse(null);
    }

    public List<VpnResponse> findAllVPN() {
        List<Vpn> vpns = vpnRepository.findAll();
        return  vpns.stream().map(vpn -> new VpnResponse(vpn.getName(), vpn.getIpEntities().stream().map(IpEntity::getQuery)
                        .collect(Collectors.toList()))).collect(Collectors.toList());
    }

    public VpnResponse addVPNWithExistIp(VpnRequest vpnDTO) {
        Vpn existVpn = vpnRepository.findByName(vpnDTO.getName());
        if(existVpn != null) {
            return null;
        }
        Set<IpEntity> ipEntities = new HashSet<>();
        for(String ip : vpnDTO.getIpEntities()) {
            IpEntity existIpEntity = ipRepository.findByQuery(ip);
            if(existIpEntity == null) {
                IpResponse ipResponse = ipService.getIpInfo(ip);
                existIpEntity = new IpEntity(ipResponse. getQuery(), ipResponse.getCountry(),
                        ipResponse.getRegionName(), ipResponse.getCity(), null);
            }
            ipEntities.add(existIpEntity);
        }
        Vpn vpn =  new Vpn(vpnDTO.getName(), ipEntities);
        vpnRepository.save(vpn);
        return new VpnResponse(vpn.getName(), vpn.getIpEntities().stream()
                .map(IpEntity::getQuery)
                .collect(Collectors.toList()));
    }

    public VpnResponse addIp(Long id, String ip) {
        Optional<Vpn> existVpn = vpnRepository.findById(id);
        if(existVpn.isEmpty()) {
            return null;
        }
        IpEntity ipEntity = ipRepository.findByQuery(ip);
        if(ipEntity == null)
        {
            IpResponse response = ipService.getIpInfo(ip);
            ipEntity = new IpEntity(response.getQuery(), response.getCountry(),
                    response.getRegionName(), response.getCity(), null);
            ipRepository.save(ipEntity);
        }
        existVpn.get().getIpEntities().add(ipEntity);
        vpnRepository.save(existVpn.get());
        return new VpnResponse(existVpn.get().getName(), existVpn.get().getIpEntities().stream()
                .map(IpEntity::getQuery)
                .collect(Collectors.toList()));
    }

    public VpnResponse removeIp(Long id, String ip) {
        Optional<Vpn> existVpn = vpnRepository.findById(id);
        if(existVpn.isEmpty()) {
            return null;
        }
        IpEntity ipEntity = ipRepository.findByQuery(ip);
        if(ipEntity == null)
        {
            return null;
        }
        existVpn.get().getIpEntities().remove(ipEntity);
        vpnRepository.save(existVpn.get());
        return new VpnResponse(existVpn.get().getName(), existVpn.get().getIpEntities().stream()
                .map(IpEntity::getQuery)
                .collect(Collectors.toList()));
    }

    public Boolean deleteVPN(Long id) {
        Optional<Vpn> isExist = vpnRepository.findById(id);
        if(isExist.isEmpty()) {
            return false;
        }
        vpnRepository.delete(isExist.get());
        return true;
    }
}
