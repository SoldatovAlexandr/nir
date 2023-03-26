package edu.asoldatov.nir.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CadetImportDto {

    private String path;
    private String schoolName;
    private String groupName;

}
