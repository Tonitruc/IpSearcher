package org.example.ipsearcher.controller;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.dto.Request.VpnRequest;
import org.example.ipsearcher.dto.Response.VpnResponse;
import org.example.ipsearcher.service.VPNService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vpn")
@AllArgsConstructor
public class VPNController {
    private final VPNService vpnService;

    @GetMapping("/get/{id}")
    public ResponseEntity<VpnResponse> getIpEntity(@PathVariable Long id) {
        VpnResponse vpn = vpnService.getVPNById(id);
        if(vpn != null) {
            return ResponseEntity.ok(vpn);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<VpnResponse> getAllIpEntity() {
        return vpnService.findAllVPN();
    }

    @PostMapping("/add_with_exist_ip")
    public ResponseEntity<VpnResponse> addVPNWithExistIp(@RequestBody VpnRequest vpnRequest) {
        VpnResponse vpn = vpnService.addVPNWithExistIp(vpnRequest);
        if(vpn != null) {
            return ResponseEntity.ok(vpn);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/add_ip/{id}")
    public ResponseEntity<VpnResponse> addIp(@PathVariable Long id, @RequestParam String ip) {
        VpnResponse vpn = vpnService.addIp(id, ip);
        if(vpn != null) {
            return ResponseEntity.ok(vpn);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/remove_ip/{id}")
    public ResponseEntity<VpnResponse> removeIp(@PathVariable Long id, @RequestParam String ip) {
        VpnResponse vpn = vpnService.removeIp(id, ip);
        if(vpn != null) {
            return ResponseEntity.ok(vpn);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteServerTraffic(@PathVariable Long id) {
        Boolean isExist = vpnService.deleteVPN(id);
        if(isExist) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
