package com.finalProyect.CynthiaLabrador.users.dto;

import com.finalProyect.CynthiaLabrador.composition.dto.GetCompositionDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUserDto {

    private UUID id;
    private String name;
    private String lastName;
    private String nick;
    private String email;
    private String avatar;
    private String userRoles;
    private String city;
    private List<GetCompositionDto> compositionList = new ArrayList<>();
}
