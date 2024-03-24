package org.example.ipsearcher.controller;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.dto.Request.IpEntityRequest;
import org.example.ipsearcher.dto.Response.IpEntityResponse;
import org.example.ipsearcher.model.IpEntity;
import org.example.ipsearcher.service.IpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ip")
public class IpController {
    private final IpService ipService;

    @GetMapping("/get/{id}")
    public ResponseEntity<IpEntityResponse> getIpEntity(@PathVariable Long id) {
        IpEntity ipEntity = ipService.getIpEntity(id);
        if(ipEntity != null) {
            String trafficName = ipEntity.getServerTraffic() != null ? ipEntity.getServerTraffic().getTrafficName() : null;
            IpEntityResponse ipEntityResponse = new IpEntityResponse(ipEntity.getQuery(), ipEntity.getCountry(),
                    ipEntity.getRegionName(), ipEntity.getCity(), trafficName);
            return ResponseEntity.ok(ipEntityResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<IpEntityResponse> getAllIpEntity() {
        return ipService.getAllIpEntity();
    }

    @PostMapping("/search")
    public ResponseEntity<IpEntity> addIpEntity(@RequestParam String ip) {
        IpEntity ipEntity = ipService.addIpEntity(ip);
        if(ipEntity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.ok(ipEntity);
        }
    }

    @PostMapping("/ip_with_traffic")
    public ResponseEntity<IpEntity> addIpEntityWithExistTraffic(@RequestBody IpEntityRequest ipEntityRequest) {
        IpEntity ipEntity = ipService.addIpEntityWithExistTraffic(ipEntityRequest);
        if(ipEntity == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            return ResponseEntity.ok(ipEntity);
        }
    }


    @PutMapping("/change_traffic/{id}")
    public ResponseEntity<IpEntityResponse> changeTraffic(@PathVariable("id") Long ipEntityId,
                                                  @RequestParam Long serverTrafficId) {
        IpEntityResponse ipEntity = ipService.changeTraffic(ipEntityId, serverTrafficId);
        if(ipEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(ipEntity);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IpEntityResponse> updateServerTraffic(@PathVariable Long id, @RequestParam String ip) {
        IpEntityResponse ipEntity = ipService.updateIpEntity(id, ip);
        if(ipEntity != null) {
            return ResponseEntity.ok(ipEntity);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteServerTraffic(@PathVariable Long id) {
        Boolean isExist = ipService.deleteIpEntity(id);
        if(isExist) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }
}

