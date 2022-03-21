package com.finalProyect.CynthiaLabrador.errors.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError {

    private String objeto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String campo;
    private String mensaje;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object valorRechazado;

}