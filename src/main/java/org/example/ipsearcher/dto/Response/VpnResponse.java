package org.example.ipsearcher.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VpnResponse {
    private String name;
    private List<String> ips;
}
