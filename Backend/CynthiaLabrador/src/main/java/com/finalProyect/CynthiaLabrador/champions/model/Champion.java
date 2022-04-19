package com.finalProyect.CynthiaLabrador.champions.model;

import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "champions")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Champion {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;

    @NaturalId
    @Column(unique = true,updatable = false)
    private String name;

    private int cost;

    //@OneToMany
   // private List<Traits> traits;

    private String avatar;
}

