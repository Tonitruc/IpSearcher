package org.example.ipsearcher.controller;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.dto.Request.ServerTrafficRequest;
import org.example.ipsearcher.model.ServerTraffic;
import org.example.ipsearcher.service.ServerTrafficService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/server_traffics")
@AllArgsConstructor
public class ServerTrafficController {
    private final ServerTrafficService serverTrafficService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ServerTraffic> getSeverTraffic(@PathVariable Long id) {
        ServerTraffic serverTraffic = serverTrafficService.getServerTraffic(id);
        if(serverTraffic != null) {
            return ResponseEntity.ok(serverTraffic);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<ServerTraffic> getAllServerTraffics() {
        return serverTrafficService.getAllServerTraffics();
    }

    @PostMapping("/add")
    public ResponseEntity<ServerTraffic> addServerTraffic(@RequestBody ServerTrafficRequest serverTrafficRequest) {
        ServerTraffic serverTraffic = serverTrafficService.addServerTraffic(serverTrafficRequest);
        if(serverTraffic != null) {
            return ResponseEntity.ok(serverTraffic);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServerTraffic> updateServerTraffic(@PathVariable Long id, @RequestBody ServerTrafficRequest serverTrafficRequest) {
        ServerTraffic serverTraffic = serverTrafficService.updateServerTraffic(id, serverTrafficRequest);
        if(serverTraffic != null) {
            return ResponseEntity.ok(serverTraffic);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteServerTraffic(@PathVariable Long id) {
        Boolean isExist = serverTrafficService.deleteServerTraffic(id);
        if(Boolean.TRUE.equals(isExist)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
