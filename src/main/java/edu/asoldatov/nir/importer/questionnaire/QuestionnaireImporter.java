package edu.asoldatov.nir.importer.questionnaire;

import edu.asoldatov.nir.model.questionnaire.QuestionnaireCadet;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public interface QuestionnaireImporter {

    List<QuestionnaireCadet> importInternal(Sheet sheet);

}
