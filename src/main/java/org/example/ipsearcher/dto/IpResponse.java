package org.example.ipsearcher.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IpResponse {
    private String query;
    private String country;
    private String regionName;
    private String city;
}
