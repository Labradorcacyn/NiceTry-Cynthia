package com.finalProyect.CynthiaLabrador.errors.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class ApiError {

    private HttpStatus estado;
    private int codigo;
    private String mensaje;
    private String ruta;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime fecha = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> subErrores;

    public ApiError(HttpStatus estado, String mensaje, String ruta) {
        this.estado = estado;
        this.codigo = estado.value();
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
        this.ruta = ruta;
    }

    public ApiError(HttpStatus estado, String mensaje, String ruta, List<ApiSubError> subErrores) {
        this(estado, mensaje, ruta);
        this.subErrores = subErrores;
    }
}
