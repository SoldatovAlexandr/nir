package edu.asoldatov.nir.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Years {
    Y_2011("2011-2012", 2012),
    Y_2012("2012-2013", 2013),
    Y_2013("2013-2014", 2014),
    Y_2014("2014-2015", 2015),
    Y_2015("2015-2016", 2016),
    Y_2016("2016-2017", 2017),
    Y_2017("2017-2018", 2018);

    private final String label;
    private final int year;

    public static Years getByLabel(String label) {
        return Arrays.stream(values()).filter(v -> v.label.equals(label)).findFirst().orElseThrow();
    }

    public int calculateAgeByYear(int age) {
        return age - (2018 - this.year);
    }

}
