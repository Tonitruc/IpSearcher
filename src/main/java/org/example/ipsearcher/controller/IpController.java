package org.example.ipsearcher.controller;

import lombok.AllArgsConstructor;
import org.example.ipsearcher.dto.IpResponse;
import org.example.ipsearcher.model.VPN;
import org.example.ipsearcher.service.IpService;
import org.example.ipsearcher.service.ServerTrafficService;
import org.example.ipsearcher.service.VPNService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class IpController {
    private final IpService ipService;
    private final ServerTrafficService userService;
    private final VPNService vpnService;

/*    @PostMapping("ip")
    public ResponseEntity<IpResponse> handleIpRequest(@RequestBody IpRequest ipRequest) {
        IpResponse response = ipService.getIpInfo(ipRequest.getQuery());
        return ResponseEntity.ok(response);
    }*/

    @PostMapping("/param")
    public ResponseEntity<IpResponse> getIpInfo(@RequestParam String ip,
                                                @RequestParam String username,
                                                @RequestParam String vpnName) {
        IpResponse response = ipService.getIpInfo(ip, username, vpnName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public List<VPN> findAllVPN() {
        return vpnService.findAllVPN();
    }
}

