package org.example.ipsearcher.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class VpnRequest {
    private String name;
    private List<String> ipEntities;
}
