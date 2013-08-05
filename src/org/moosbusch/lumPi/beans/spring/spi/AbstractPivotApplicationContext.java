/*
Copyright 2013 Gunnar Kappei

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.moosbusch.lumPi.beans.spring.spi;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Objects;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.moosbusch.lumPi.application.DesktopApplication;
import org.moosbusch.lumPi.beans.spring.PivotApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractPivotApplicationContext
        extends AnnotationConfigApplicationContext implements PivotApplicationContext {

    private URL location;
    private Resources resources;
    private Map<String, Object> namespace;

    public AbstractPivotApplicationContext(DesktopApplication application) {
        init(application);
    }

    private void init(DesktopApplication application) {
        registerShutdownHook();

        try {
            loadXmlConfig(application);
            loadAnnotationConfig(application);
        } finally {
            refresh();
        }
    }

    private void loadAnnotationConfig(DesktopApplication application) {
        Class<?> pivotFactoryBeanClass = Objects.requireNonNull(
                application.getPivotBeanFactoryClass());
        String[] beanPackages = application.getAnnotatedBeanPackages();
        Class<?>[] annotatedClasses = application.getAnnotatedClasses();

        if ((ArrayUtils.isNotEmpty(annotatedClasses))) {
            register(ArrayUtils.add(annotatedClasses, pivotFactoryBeanClass));
        } else {
            register(pivotFactoryBeanClass);
        }

        if ((ArrayUtils.isNotEmpty(beanPackages))) {
            scan(beanPackages);
        }
    }

    private void loadXmlConfig(DesktopApplication application) {
        String[] configLocations = configLocationsFromURLs(
                application.getBeanConfigurations());

        if (ArrayUtils.isNotEmpty(configLocations)) {
            XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(this);

            for (int cnt = 0; cnt < configLocations.length; cnt++) {
                Resource res = getResource(configLocations[cnt]);

                if (res != null) {
                    xmlReader.loadBeanDefinitions(res);
                }
            }
        }
    }

    private String[] configLocationsFromURLs(URL[] locations) {
        if (ArrayUtils.isNotEmpty(locations)) {
            String[] result = new String[locations.length];

            for (int cnt = 0; cnt < result.length; cnt++) {
                result[cnt] = locations[cnt].toExternalForm();
            }

            return result;
        }

        return null;
    }

    @Override
    public boolean containsBean(String id) {
        boolean result = false;

        if (StringUtils.isNotBlank(id)) {
            result = super.containsBean(id);

            if ((!result) && (getParent() != null)) {
                result = getParent().containsBean(id);
            }

            if ((!result) && (getNamespace() != null)) {
                result = getNamespace().containsKey(id);
            }
        }

        return result;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        T result = super.getBean(requiredType);

        return result;
    }

    @Override
    public Object getBean(String id) {
        Object result = null;

        if (StringUtils.isNotBlank(id)) {
            result = super.getBean(id);

            if ((result == null) && (getNamespace() != null)) {
                result = getNamespace().get(id);
            }
        }

        return result;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        Object result = super.getBean(name, requiredType);

        if ((result == null) && (getNamespace() != null)) {
            result = getNamespace().get(name);

            if (result != null) {
                if (ClassUtils.isAssignable(result.getClass(), requiredType)) {
                    return (T) result;
                }
            }
        }

        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        Object result = super.getBean(name, args);

        if (result == null) {
            result = getNamespace().get(name);

            if (result != null) {
                Constructor<?> constructor;

                if (ArrayUtils.isNotEmpty(args)) {
                    Class<?>[] parameterTypes = new Class<?>[0];

                    for (Object arg : args) {
                        parameterTypes = ArrayUtils.add(parameterTypes, arg.getClass());
                    }

                    constructor = ConstructorUtils.getAccessibleConstructor(
                            result.getClass(), parameterTypes);
                } else {
                    constructor = ConstructorUtils.getAccessibleConstructor(
                            result.getClass(), new Class<?>[0]);
                }

                if (constructor != null) {
                    return result;
                }
            }
        }

        return null;
    }

    @Override
    public URL getLocation() {
        return location;
    }

    @Override
    public void setLocation(URL location) {
        this.location = location;
    }

    @Override
    public Map<String, Object> getNamespace() {
        return namespace;
    }

    @Override
    public void setNamespace(Map<String, Object> pivotNamespace) {
        this.namespace = pivotNamespace;
    }

    @Override
    public Resources getResources() {
        return resources;
    }

    @Override
    public void setResources(Resources resources) {
        this.resources = resources;
    }
}
