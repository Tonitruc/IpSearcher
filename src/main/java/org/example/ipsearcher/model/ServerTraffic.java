package org.example.ipsearcher.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "server_traffic_entity")
public class ServerTraffic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trafficName;

    @OneToMany(mappedBy = "serverTraffic", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    Set<IpEntity> ipEntities = new HashSet<>();

    public ServerTraffic(String trafficName) {
        this.trafficName = trafficName;
    }
}
