package edu.asoldatov.nir.cb;

import ai.catboost.CatBoostModel;
import ai.catboost.CatBoostPredictions;

public class SandBox {

    public static void main(String[] args) {

        try (CatBoostModel model = CatBoostModel.loadModel("/Users/asoldatov/projects/myapp/nir/nir/src/main/resources/model.cbm")){

            CatBoostPredictions prediction = model.predict(new float[]{0.1f, 0.3f, 0.2f}, model.getFeatureNames());
// assuming that this is a regression task
            System.out.print("model value is " + prediction.get(0, 0));

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
