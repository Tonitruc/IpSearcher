package org.example.ipsearcher.service;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.dto.IpResponse;
import org.example.ipsearcher.model.IpEntity;
import org.example.ipsearcher.model.ServerTraffic;
import org.example.ipsearcher.model.VPN;
import org.example.ipsearcher.repository.IpRepository;
import org.example.ipsearcher.repository.UserRepository;
import org.example.ipsearcher.repository.VPNRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class IpService {
    private final IpRepository ipRepository;
    private final VPNRepository vpnRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public IpResponse getIpInfo(String ip, String username, String vpnName) {
        if (!isValidIp(ip)) {
            throw new IllegalArgumentException("Invalid IP address");
        }

        String baseUrl = "http://ip-api.com/json/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(ip);
        IpResponse response = restTemplate.getForObject(builder.toUriString(), IpResponse.class);

        IpEntity ipEntity = ipRepository.findByQuery(response.getQuery());
        if (ipEntity == null) {
            ipEntity = new IpEntity(response.getQuery(), response.getCountry(), response.getRegionName(), response.getCity());
            ipEntity = ipRepository.save(ipEntity);
        }

        ServerTraffic user = userRepository.findByUsername(username);
        if(user == null) {
            user = new ServerTraffic(username, "Fuck@gmail.com");
            user = userRepository.save(user);
        }

        Set<IpEntity> ips = new HashSet<>();
        ips.add(ipEntity);
        VPN vpnEntity = vpnRepository.findByName(vpnName);
        if(vpnEntity == null) {
            vpnEntity = new VPN(vpnName, user, ips);
            vpnRepository.save(vpnEntity);
        }
        else {
            vpnEntity.getIpEntities().add(ipEntity);
            vpnRepository.save(vpnEntity);
        }
        return response;
    }

    private boolean isValidIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }

        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }
}
