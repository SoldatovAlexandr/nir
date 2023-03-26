package edu.asoldatov.nir.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImportResultDto {

    private Integer importedCount;
    private Integer errorCount;
    private List<String> errors;

}
