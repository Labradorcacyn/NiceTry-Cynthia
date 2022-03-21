package com.finalProyect.CynthiaLabrador.follow.model;

import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class PeticionSeguimiento implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String texto;

    @ManyToOne
    private UserEntity destinatario;

    @ManyToOne
    private UserEntity remitente;

    @PreRemove
    public void nullearDestinatarios(){
        remitente.setPeticionSeguimientoList(null);
    }
}