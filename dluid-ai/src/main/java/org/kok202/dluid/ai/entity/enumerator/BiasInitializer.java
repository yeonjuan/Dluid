package org.kok202.dluid.ai.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BiasInitializer {
    ZERO(0),
    SMALL_VALUE(0.01),
    ONES(1),
    FOLLOW_GLOBAL_SETTING(0);

    @Getter
    private double bias;

}