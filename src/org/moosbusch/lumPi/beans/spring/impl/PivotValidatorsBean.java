/*
 * Copyright 2014 Gunnar Kappei.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moosbusch.lumPi.beans.spring.impl;

import org.apache.pivot.wtk.validation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class PivotValidatorsBean {

    @Bean
    public BigDecimalValidator createBigDecimalValidator() {
        return new BigDecimalValidator();
    }

    @Bean
    public BigIntegerValidator createBigIntegerValidator() {
        return new BigIntegerValidator();
    }

    @Bean
    public ComparableRangeValidator<?> createComparableRangeValidator() {
        return new ComparableRangeValidator();
    }

    @Bean
    public ComparableValidator<?> createComparableValidator() {
        return new ComparableValidator();
    }

    @Bean
    public DecimalValidator createDecimalValidator() {
        return new DecimalValidator();
    }

    @Bean
    public DoubleRangeValidator createDoubleRangeValidator() {
        return new DoubleRangeValidator();
    }

    @Bean
    public DoubleValidator createDoubleValidator() {
        return new DoubleValidator();
    }

    @Bean
    public EmptyTextValidator createEmptyTextValidator() {
        return new EmptyTextValidator();
    }

    @Bean
    public FloatRangeValidator createFloatRangeValidator() {
        return new FloatRangeValidator();
    }

    @Bean
    public FloatValidator createFloatValidator() {
        return new FloatValidator();
    }

    @Bean
    public IntRangeValidator createIntRangeValidator() {
        return new IntRangeValidator();
    }

    @Bean
    public IntValidator createIntValidator() {
        return new IntValidator();
    }

    @Bean
    public NotEmptyTextValidator createNotEmptyTextValidator() {
        return new NotEmptyTextValidator();
    }

    @Bean
    public RegexTextValidator createRegexTextValidator() {
        return new RegexTextValidator();
    }
}
