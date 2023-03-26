package edu.asoldatov.nir.importer.cadet;

import edu.asoldatov.nir.model.cadet.Cadet;
import org.apache.poi.ss.usermodel.Sheet;

public interface CadetImporter {

    Cadet importInternal(Sheet sheet);

}
