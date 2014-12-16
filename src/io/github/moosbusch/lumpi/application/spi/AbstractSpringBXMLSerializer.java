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
package io.github.moosbusch.lumpi.application.spi;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.serialization.Serializer;
import io.github.moosbusch.lumpi.application.LumpiApplicationContext;
import io.github.moosbusch.lumpi.application.SpringBXMLSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Gunnar Kappei
 */
public abstract class AbstractSpringBXMLSerializer extends BXMLSerializer
        implements SpringBXMLSerializer {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        getNamespace().getMapListeners().add(getApplicationContext());
    }

    @Override
    protected abstract Object newTypedObject(Class<?> type);

    @Override
    protected abstract SpringBXMLSerializer newIncludeSerializer(Class<? extends Serializer<?>> type);

    protected Object readObjectImpl(InputStream inputStream)
            throws IOException, SerializationException {
        return super.readObject(inputStream);
    }

    @Override
    public final Object readObject(InputStream inputStream)
            throws IOException, SerializationException {
        return readObjectImpl(inputStream);
    }

    @Override
    public LumpiApplicationContext getApplicationContext() {
        return (LumpiApplicationContext) applicationContext;
    }

}
