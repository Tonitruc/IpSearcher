package org.example.ipsearcher.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.ipsearcher.model.ServerTraffic;

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
