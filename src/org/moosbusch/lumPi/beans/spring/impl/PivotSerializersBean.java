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

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.io.FileSerializer;
import org.apache.pivot.json.JSONSerializer;
import org.apache.pivot.serialization.BinarySerializer;
import org.apache.pivot.serialization.ByteArraySerializer;
import org.apache.pivot.serialization.CSVSerializer;
import org.apache.pivot.serialization.PropertiesSerializer;
import org.apache.pivot.serialization.StringSerializer;
import org.apache.pivot.wtk.media.BufferedImageSerializer;
import org.apache.pivot.wtk.media.SVGDiagramSerializer;
import org.apache.pivot.wtk.text.PlainTextSerializer;
import org.apache.pivot.xml.XMLSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gunnar Kappei
 */
@Configuration
public class PivotSerializersBean {

    @Bean
    public FileSerializer createFileSerializer() {
        return new FileSerializer();
    }

    @Bean
    public BinarySerializer createBinarySerializer() {
        return new BinarySerializer();
    }

    @Bean
    public BufferedImageSerializer createBufferedImageSerializer() {
        return new BufferedImageSerializer();
    }

    @Bean
    public BXMLSerializer createBXMLSerializer() {
        return new BXMLSerializer();
    }

    @Bean
    public ByteArraySerializer createByteArraySerializer() {
        return new ByteArraySerializer();
    }

    @Bean
    public CSVSerializer createCSVSerializer() {
        return new CSVSerializer();
    }

    @Bean
    public JSONSerializer createJSONSerializer() {
        return new JSONSerializer();
    }

    @Bean
    public PlainTextSerializer createPlainTextSerializer() {
        return new PlainTextSerializer();
    }

    @Bean
    public PropertiesSerializer createPropertiesSerializer() {
        return new PropertiesSerializer();
    }

    @Bean
    public StringSerializer createStringSerializer() {
        return new StringSerializer();
    }

    @Bean
    public SVGDiagramSerializer createSVGDiagramSerializer() {
        return new SVGDiagramSerializer();
    }

    @Bean
    public XMLSerializer createXMLSerializer() {
        return new XMLSerializer();
    }
}
