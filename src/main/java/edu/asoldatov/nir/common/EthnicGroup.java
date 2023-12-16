package edu.asoldatov.nir.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@Getter
@RequiredArgsConstructor
public enum EthnicGroup {
    RUSSIAN("русский", List.of("русский", "", "рксский", "русская")),
    TATAR("татарин", List.of("татарин")),
    OSSETIAN("осетин", List.of("осетин", "асетин")),
    MARI("мариец", List.of("марийец")),
    KABARDIAN("кабардинец", List.of("кабардинец")),
    CHECHEN("чеченец", List.of("чеченец")),
    DAGESTAN("дагестанец", List.of("дагестанец")),
    ARMENIAN("армянин", List.of("армянин", "арменин")),
    BASKIR("башкир", List.of("башкир")),
    ARAB("араб", List.of("араб")),
    AZERBAIJANI("азербайджанец", List.of("азербайджанец")),
    CHUVASH("чуваш", List.of("чуваш")),
    UKRAINIAN("украинец", List.of("украинец")),
    GEORGIAN("грузин", List.of("грузин")),
    KALMYK("калмык", List.of("калмык")),
    KAZAKH("казах", List.of("казах")),
    KYRGYZ("киргиз", List.of("киргиз")),
    AVAR("аварец", List.of("авврец")),
    TAJIK("таджик", List.of("таджик")),
    UDMURT("удмурт", List.of("удмурт")),
    UZBEK("узбек", List.of("узбек")),
    LATVIAN("латыш", List.of("латыш")),
    BURYAT("бурят", List.of("бурят")),
    ANOTHER("другое", List.of("-"));

    private final String label;
    private final List<String> various;

    public static String getValue(String val) {
        for (EthnicGroup group : values()) {
            for (String variant : group.various) {
                if (variant.equals(val)) {
                    return group.label;
                }
            }
        }
        return ANOTHER.label;
    }
}
