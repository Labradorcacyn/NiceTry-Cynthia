package com.finalProyect.CynthiaLabrador.file.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {

    private String name;
    private String uri;
    private String type;
    private long size;
}
