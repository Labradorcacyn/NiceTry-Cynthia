package com.finalProyect.CynthiaLabrador.traits.model;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "traits")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Traits {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,updatable = false)
    private String name;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT", length = 65535)
    private String description;

    private String avatar;

    @ManyToMany(mappedBy = "traits")
    private List<Champion> champions = new ArrayList<>();
}
