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

import org.apache.pivot.wtk.text.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */

@Configuration
public class PivotTextBean {

    @Bean
    public BulletedList createBulletedList() {
        return new BulletedList();
    }

    @Bean
    public ComponentNode createComponentNode() {
        return new ComponentNode();
    }

    @Bean
    public Document createDocument() {
        return new Document();
    }

    @Bean
    public ImageNode createImageNode() {
        return new ImageNode();
    }

    @Bean
    public List.Item createTextListItem() {
        return new List.Item();
    }

    @Bean
    public NumberedList createNumberedList() {
        return new NumberedList();
    }

    @Bean
    public Paragraph createParagraph() {
        return new Paragraph();
    }

    @Bean
    public Span createSpan() {
        return new Span();
    }

    @Bean
    public TextNode createTextNode() {
        return new TextNode();
    }

    @Bean
    public TextSpan createTextSpan() {
        return new TextSpan();
    }
}
