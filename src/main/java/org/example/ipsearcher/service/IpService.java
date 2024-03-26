package org.example.ipsearcher.service;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.dto.request.IpEntityRequest;
import org.example.ipsearcher.dto.response.IpEntityResponse;
import org.example.ipsearcher.dto.response.IpResponse;
import org.example.ipsearcher.model.IpEntity;
import org.example.ipsearcher.model.ServerTraffic;
import org.example.ipsearcher.model.Vpn;
import org.example.ipsearcher.repository.IpRepository;
import org.example.ipsearcher.repository.ServerTrafficRepository;
import org.example.ipsearcher.repository.VpnRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class IpService {
    private final RestTemplate restTemplate;
    private final IpRepository ipRepository;
    private final ServerTrafficRepository serverTrafficRepository;
    private final VpnRepository vpnRepository;
    private static final String IPADDRESS_PATTERN =
            "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$";

    private static final Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);

    public boolean isValidIp(String ip) {
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public IpResponse getIpInfo(String ip) {
        if (!isValidIp(ip)) {
            return null;
        }
        String baseUrl = "http://ip-api.com/json/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(ip);
        return restTemplate.getForObject(builder.toUriString(), IpResponse.class);
    }

    public IpEntity getIpEntity(Long id) {
        Optional<IpEntity> existIpEntity = ipRepository.findById(id);
        return existIpEntity.orElse(null);
    }

    public List<IpEntityResponse> getAllIpEntity() {
        return ipRepository.findAll().stream()
                .map(entity -> new IpEntityResponse(entity.getQuery(), entity.getCountry(),
                        entity.getRegionName(), entity.getCity(),
                        entity.getServerTraffic() != null ? entity.getServerTraffic().getTrafficName() : null))
                .toList();
    }

    public IpEntity addIpEntity(String ip) {
        IpResponse response = getIpInfo(ip);
        if(response == null) {
            return null;
        }
        IpEntity existIpEntity = ipRepository.findByQuery(ip);
        if(existIpEntity != null) {
            return null;
        }
        IpEntity ipEntity = new IpEntity(response.getQuery(), response.getCountry(),
                response.getRegionName(), response.getCity(), null);
        return ipRepository.save(ipEntity);
    }

    public IpEntity addIpEntityWithExistTraffic(IpEntityRequest ipDTO) {
        Optional<ServerTraffic> existServerTraffic = serverTrafficRepository.findById(ipDTO.getServerTrafficId());
        if(existServerTraffic.isEmpty()) {
            return null;
        }
        IpEntity existIpEntity = ipRepository.findByQuery(ipDTO.getQuery());
        if(existIpEntity != null) {
            return null;
        }
        IpResponse response = getIpInfo(ipDTO.getQuery());
        if(response == null) {
            return null;
        }
        IpEntity ipEntity = new IpEntity(response.getQuery(), response.getCountry(),
                response.getRegionName(), response.getCity(), existServerTraffic.get());
        return ipRepository.save(ipEntity);
    }

    public IpEntityResponse changeTraffic(Long ipEntityId, Long serverTrafficId) {
        Optional<IpEntity> existIpEntity = ipRepository.findById(ipEntityId);
        if(existIpEntity.isEmpty()) {
            return null;
        }
        Optional<ServerTraffic> existServerTraffic = serverTrafficRepository.findById(serverTrafficId);
        if(existServerTraffic.isEmpty()) {
            return null;
        }
        existIpEntity.get().setServerTraffic(existServerTraffic.get());
        ipRepository.save(existIpEntity.get());
        String trafficName = existIpEntity.get().getServerTraffic() != null ? existIpEntity.get().getServerTraffic().getTrafficName() : null;
        return  new IpEntityResponse(existIpEntity.get().getQuery(), existIpEntity.get().getCountry(),
                existIpEntity.get().getRegionName(), existIpEntity.get().getCity(), trafficName);
    }

    public IpEntityResponse updateIpEntity(Long id, String ip) {
        Optional<IpEntity> existIpEntity = ipRepository.findById(id);
        if(existIpEntity.isEmpty()) {
            return null;
        }
        IpResponse response = getIpInfo(ip);
        if(response == null) {
            return null;
        }
        IpEntity sameIpEntity = ipRepository.findByQuery(ip);
        if(sameIpEntity != null)
            return null;
        IpEntity ipEntity = new IpEntity(response.getQuery(), response.getCountry(),
                response.getRegionName(), response.getCity(), existIpEntity.get().getServerTraffic());
        ipEntity.setId(id);
        ipRepository.save(ipEntity);
        String trafficName = ipEntity.getServerTraffic() != null ? ipEntity.getServerTraffic().getTrafficName() : null;
        return new IpEntityResponse(ipEntity.getQuery(), ipEntity.getCountry(),
                ipEntity.getRegionName(), ipEntity.getCity(), trafficName);
    }

    public Boolean deleteIpEntity(Long id) {
        Optional<IpEntity> existIpEntity = ipRepository.findById(id);
        if(existIpEntity.isEmpty()) {
            return false;
        }
        List<Vpn> vpns = vpnRepository.findAll();
        for(Vpn vpn : vpns) {
            if(vpn.getIpEntities().contains(existIpEntity.get())) {
                vpn.getIpEntities().remove(existIpEntity.get());
            }
        }

        ipRepository.delete(existIpEntity.get());
        return true;
    }
}
