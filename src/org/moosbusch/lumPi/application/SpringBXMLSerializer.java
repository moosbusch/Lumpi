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
package org.moosbusch.lumPi.application;

import java.io.IOException;
import java.net.URL;
import org.apache.pivot.beans.BindException;
import org.apache.pivot.beans.Resolvable;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.serialization.Serializer;
import org.apache.pivot.util.Resources;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Gunnar Kappei
 */
public interface SpringBXMLSerializer extends Serializer<Object>, Resolvable {

    public void bind(Object object, Class<?> type) throws BindException;

    public ApplicationContext getApplicationContext();

    public Object readObject(URL locationArgument)
            throws IOException, SerializationException;

    public Object readObject(URL locationArgument, Resources resourcesArgument)
            throws IOException, SerializationException;

}
