package com.finalProyect.CynthiaLabrador.users.model;

import com.finalProyect.CynthiaLabrador.follow.model.PeticionSeguimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;

    private String nombre;

    private String apellidos;

    private String nick;

    @NaturalId
    @Column(unique = true,updatable = false)
    private String email;


    private LocalDate fechaNacimiento;

    private String avatar;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private UserRoles userRoles;

    /*@OneToMany
    private List<Champ> listaChamps= new ArrayList<>();*/

    /*@OneToMany
    private List<Champ> listaChampsFav= new ArrayList<>();*/


    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserEntity> followers= new ArrayList<>();

    @OneToMany(mappedBy = "destinatario")
    @Builder.Default
    private List<PeticionSeguimiento> peticionSeguimientoList = new ArrayList<>();

    //HELPERS

    public void addPeticion(PeticionSeguimiento p){
        if (this.getPeticionSeguimientoList() == null){
            this.setPeticionSeguimientoList(new ArrayList<>());
        }
        this.getPeticionSeguimientoList().add(p);
    }

    public void addFollower(UserEntity u) {
        this.followers = List.of(u);
        u.getFollowers().add(this);
    }

    public void removeFollower(UserEntity u) {
        u.getFollowers().remove(this);
        this.followers = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+ userRoles.name()));
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
