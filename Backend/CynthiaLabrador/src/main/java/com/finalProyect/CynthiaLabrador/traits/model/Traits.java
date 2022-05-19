package com.finalProyect.CynthiaLabrador.traits.model;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private String description;

    private String avatar;

    @ManyToMany(mappedBy = "traits")
    private List<Champion> champions = new ArrayList<>();
}
