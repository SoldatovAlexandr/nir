package edu.asoldatov.nir.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ToothValueType {

    A("A", 0),
    A_ru("А", 0),
    B("B", 1),
    B_ru("В", 1),
    C("C", 2),
    C_ru("C", 2),
    D("D", 3),
    E("E", 4),
    E_ru("E", 4),
    F("F", 6),
    G("G", 7),
    EMPTY("", 9);//TODO cпросить у Вани

    private final String label;
    private final Integer value;

    public static int getValueByLabel(String value) {
        return Arrays.stream(values())
                .filter(v -> v.label.equals(value.toUpperCase()))
                .map(v -> v.value)
                .findFirst()
                .orElseThrow();
    }
}
