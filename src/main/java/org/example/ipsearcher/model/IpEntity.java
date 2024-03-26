package org.example.ipsearcher.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ip_information")
public class IpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query;
    private String country;
    private String regionName;
    private String city;

    @ManyToMany(mappedBy = "ipEntities")
    private Set<Vpn> vpns = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "server_traffic_id")
    private ServerTraffic serverTraffic;

    public IpEntity(String query, String country, String regionName, String city, ServerTraffic serverTraffic) {
        this.query = query;
        this.country = country;
        
        this.regionName = regionName;
        this.city = city;
        this.serverTraffic = serverTraffic;
    }
}

