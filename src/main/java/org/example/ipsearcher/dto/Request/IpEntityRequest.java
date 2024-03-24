package org.example.ipsearcher.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IpEntityRequest {
    private String query;
    private Long serverTrafficId;
}
