package edu.asoldatov.nir.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum School {

    TULASVU("Тульское СВУ","Тульская область"),
    SPBKBK("СПбКВК","Санкт-Петербург"),
    SPBNAHVMU("СПб Нахивское ВМУ","Санкт-Петербург"),
    SEVKAVSVU("Сев-Кав СВУ", "Республика Северная Осетия"),
    EKBSVU("Екб СВУ","Санкт-Петербург"),
    TUMENPKU("Тюмень ПКУ", "Тюменская область"),
    KAZANSVU("Казань СВУ","Республика Татарстан"),
    ULYANOVSKOESVU("Ульяновское СВУ", "Ульяновская область"),
    PETRAZAVODSKOESVU("Петрозаводское СВУ", "Республики Карелия");


    private final String label;
    private final String region;

    public static School findByLabel(String label) {
        return Arrays.stream(values()).filter(v -> v.label.equals(label)).findFirst().orElseThrow();
    }
}
