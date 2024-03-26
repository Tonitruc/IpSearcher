package org.example.ipsearcher.model;

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
@Table(name = "vpn_entity")
public class Vpn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "vpn_ip_entity",
            joinColumns = @JoinColumn(name = "vpn_id"),
            inverseJoinColumns = @JoinColumn(name = "ip_entity_id"))
    private Set<IpEntity> ipEntities = new HashSet<>();

    public Vpn(String name, Set<IpEntity> ipEntities) {
        this.name = name;
        this.ipEntities = ipEntities;
    }
}
