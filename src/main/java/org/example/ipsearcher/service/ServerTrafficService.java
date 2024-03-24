package org.example.ipsearcher.service;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.dto.request.ServerTrafficRequest;
import org.example.ipsearcher.model.ServerTraffic;
import org.example.ipsearcher.repository.ServerTrafficRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServerTrafficService {
    private final ServerTrafficRepository serverTrafficRepository;

    public ServerTraffic getServerTraffic(Long id) {
        Optional<ServerTraffic> existServerTraffic = serverTrafficRepository.findById(id);
        return existServerTraffic.orElse(null);
    }

    public List<ServerTraffic> getAllServerTraffics() {
        return serverTrafficRepository.findAll();
    }

    public ServerTraffic addServerTraffic(ServerTrafficRequest serverTrafficRequest) {
        ServerTraffic serverTraffic = serverTrafficRepository.findByTrafficName(serverTrafficRequest.getTrafficName());
        if(serverTraffic != null) {
            return null;
        } else {
            serverTraffic = new ServerTraffic(serverTrafficRequest.getTrafficName());
            return serverTrafficRepository.save(serverTraffic);
        }
    }

    public ServerTraffic updateServerTraffic(@PathVariable Long id, ServerTrafficRequest serverTrafficRequest) {
        ServerTraffic serverTraffic = serverTrafficRepository.findByTrafficName(serverTrafficRequest.getTrafficName());
        if(serverTraffic != null) {
            return null;
        }
        Optional<ServerTraffic> existServerTraffic = serverTrafficRepository.findById(id);
        if(existServerTraffic.isEmpty()) {
            return null;
        }
        serverTraffic = new ServerTraffic(serverTrafficRequest.getTrafficName());
        serverTraffic.setId(id);
        return serverTrafficRepository.save(serverTraffic);
    }

    public Boolean deleteServerTraffic(Long id) {
        Optional<ServerTraffic> existServerTraffic = serverTrafficRepository.findById(id);
        if(existServerTraffic.isEmpty()) {
            return false;
        }
        serverTrafficRepository.delete(existServerTraffic.get());
        return true;
    }
}
