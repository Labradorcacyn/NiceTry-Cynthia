package com.finalProyect.CynthiaLabrador.composition.model;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.comments.model.Comment;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.Parameter;

@NamedEntityGraphs(
        {
                @NamedEntityGraph(
                        name = "Composition.champions",
                        attributeNodes = @NamedAttributeNode("champions")
                ),
                @NamedEntityGraph(
                        name = "Composition.comments",
                        attributeNodes = @NamedAttributeNode("comments")
                )
        }
)
@Entity
@Table(name = "composition")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Composition {
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

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @Nullable
    private UserEntity author;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @ManyToMany()
    @JoinTable(
            name = "composition_champion",
            joinColumns = @JoinColumn(name = "composition_id"),
            inverseJoinColumns = @JoinColumn(name = "champion_id")
    )
    private List<Champion> champions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "composition")
    private List<Comment> comments;

    @Column(name = "votes")
    @ElementCollection(targetClass=String.class)
    private List<String> votes = new ArrayList<String>();
}
