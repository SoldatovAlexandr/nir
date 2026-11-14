package edu.asoldatov.nir.common;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum HealthGroup {
    I("I", 1),
    II("II", 2),
    III("I", 3),
    IV("IV", 4),
    V("V", 5);

    private final String label;
    private final int value;

    public static int getValueByLabel(String v) {
        return Arrays.stream(values())
                .filter(val -> val.label.equals(v))
                .mapToInt(val -> val.value)
                .findFirst()
                .orElse(0);
    }
}
