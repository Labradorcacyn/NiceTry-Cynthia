package com.finalProyect.CynthiaLabrador.champions.model;

import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.Parameter;

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
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(unique = true,updatable = false)
    private String name;

    private int cost;

    @Column(length = 10000)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "champion_traits",
            joinColumns = @JoinColumn(name = "champion_id"),
            inverseJoinColumns = @JoinColumn(name = "trait_id"))
    private List<Traits> traits = new ArrayList<>();


    //helpers
    public void addTrait(List<Traits> trait) {
        traits = trait;
        trait.forEach(t -> {
            t.getChampions().add(this);
        });
    }

    public void removeTraits(List<Traits> trait) {
        this.traits.removeAll(trait);
        trait.forEach(traits -> traits.getChampions().remove(this));
    }
}