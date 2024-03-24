package org.example.ipsearcher.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IpEntityResponse {
    private String query;
    private String country;
    private String regionName;
    private String city;
    private String trafficName;
}
