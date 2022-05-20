package com.finalProyect.CynthiaLabrador.comments.model;

import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Lob
    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "votes")
    @ElementCollection(targetClass=String.class)
    private List<String> votes = new ArrayList<String>();
}