package edu.asoldatov.nir.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum School {

    TULASVU("Тульское СВУ"),
    SPBKBK("СПбКВК"),
    SPBNAHVMU("СПб Нахивское ВМУ"),
    SEVKAVSVU("Сев-Кав СВУ"),
    EKBSVU("Екб СВУ"),
    TUMENPKU("Тюмень ПКУ"),
    KAZANSVU("Казань СВУ"),
    ULYANOVSKOESVU("Ульяновское СВУ"),
    PETRAZAVODSKOESVU("Петрозаводское СВУ");

    private final String label;

    public static School findByLabel(String label) {
        return Arrays.stream(values()).filter(v -> v.label.equals(label)).findFirst().orElseThrow();
    }
}
