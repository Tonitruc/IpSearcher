package org.example.ipsearcher.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class VPN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private ServerTraffic user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "vpn_ip_entity",
            joinColumns = @JoinColumn(name = "vpn_id"),
            inverseJoinColumns = @JoinColumn(name = "ip_entity_id"))
    @JsonManagedReference
    private Set<IpEntity> ipEntities = new HashSet<>();

    public VPN(String name, ServerTraffic user, Set<IpEntity> ipEntities) {
        this.name = name;
        this.user = user;
        this.ipEntities = ipEntities;
    }
}
