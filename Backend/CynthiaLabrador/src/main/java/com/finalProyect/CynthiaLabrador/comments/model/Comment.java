package com.finalProyect.CynthiaLabrador.comments.model;

import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@NamedEntityGraphs(
        {
                @NamedEntityGraph(
                        name = "Comment.composition",
                        attributeNodes = @NamedAttributeNode("composition")
                ),
                @NamedEntityGraph(
                        name = "Comment.author",
                        attributeNodes = @NamedAttributeNode("author")
                )
        }
)
@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Comment {

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
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "composition_id")
    private Composition composition;

    @Column(length = 10000)
    private String text;
}