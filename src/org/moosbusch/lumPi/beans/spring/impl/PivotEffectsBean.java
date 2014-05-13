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

import org.apache.pivot.wtk.effects.*;
import org.apache.pivot.wtk.effects.easing.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class PivotEffectsBean {

    @Bean
    public BaselineDecorator createBaselineDecorator() {
        return new BaselineDecorator();
    }

    @Bean
    public BlurDecorator createBlurDecorator() {
        return new BlurDecorator();
    }

    @Bean
    public ClipDecorator createClipDecorator() {
        return new ClipDecorator();
    }

    @Bean
    public DropShadowDecorator createDropShadowDecorator() {
        return new DropShadowDecorator();
    }

    @Bean
    public FadeDecorator createFadeDecorator() {
        return new FadeDecorator();
    }

    @Bean
    public GrayscaleDecorator createGrayscaleDecorator() {
        return new GrayscaleDecorator();
    }

    @Bean
    public OverlayDecorator createOverlayDecorator() {
        return new OverlayDecorator();
    }

    @Bean
    public ReflectionDecorator create() {
        return new ReflectionDecorator();
    }

    @Bean
    public RotationDecorator createRotationDecorator() {
        return new RotationDecorator();
    }

    @Bean
    public SaturationDecorator createSaturationDecorator() {
        return new SaturationDecorator();
    }

    @Bean
    public ScaleDecorator createScaleDecorator() {
        return new ScaleDecorator();
    }

    @Bean
    public ShadeDecorator createShadeDecorator() {
        return new ShadeDecorator();
    }

    @Bean
    public TagDecorator createTagDecorator() {
        return new TagDecorator();
    }

    @Bean
    public TranslationDecorator createTranslationDecorator() {
        return new TranslationDecorator();
    }

    @Bean
    public WatermarkDecorator createWatermarkDecorator() {
        return new WatermarkDecorator();
    }

    @Bean
    public Circular createCircular() {
        return new Circular();
    }

    @Bean
    public Cubic createCubic() {
        return new Cubic();
    }

    @Bean
    public Exponential createExponential() {
        return new Exponential();
    }

    @Bean
    public Linear createLinear() {
        return new Linear();
    }

    @Bean
    public Quadratic createQuadratic() {
        return new Quadratic();
    }

    @Bean
    public Quartic createQuartic() {
        return new Quartic();
    }

    @Bean
    public Quintic createQuintic() {
        return new Quintic();
    }

    @Bean
    public Sine createSine() {
        return new Sine();
    }
}
