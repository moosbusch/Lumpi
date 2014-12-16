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
package io.github.moosbusch.lumpi.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author moosbusch
 */
public class FormUtil {

    public static final String COLLECTION_EMPTY_PROPERTY_NAME = "empty";
    public static final String CLASS_PROPERTY_NAME = "class";

    private FormUtil() {
    }

    public static boolean isExcludedProperty(Class<?> type, String propertyName) {
        return isExcludedProperty(type, propertyName, null);
    }

    public static boolean isExcludedProperty(Class<?> type, String propertyName,
            Map<String, Set<Class<?>>> excludedProperties) {
        Map<String, Set<Class<?>>> excludedProps = excludedProperties;

        if (excludedProps != null) {
            if (excludedProps.containsKey(propertyName)) {
                Set<Class<?>> ignoredPropertyTypes = excludedProps.get(propertyName);
                Set<Class<?>> superTypes = LumpiUtil.getSuperTypes(
                        type, true, true, true);

                if (!ignoredPropertyTypes.contains(type)) {
                    if (superTypes.stream().anyMatch((superType)
                            -> (!ignoredPropertyTypes.contains(superType)))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static Map<String, Set<Class<?>>> getDefaultIgnoredProperties() {
        Map<String, Set<Class<?>>> result = new HashMap<>();
        result.put(CLASS_PROPERTY_NAME,
                LumpiUtil.asSet(new Class<?>[]{java.lang.Class.class}));
        result.put(COLLECTION_EMPTY_PROPERTY_NAME,
                LumpiUtil.asSet(new Class<?>[]{java.util.Collection.class,
                    org.apache.pivot.collections.Collection.class, java.lang.String.class}));
        return result;
    }

    public static Set<Class<?>> getDefaultIgnoredTypes() {
        Set<Class<?>> result = new HashSet<>();
        result.add(Class.class);
        result.add(ClassLoader.class);
        return result;
    }

    public static Map<String, Class<?>> getPropertyTypesMap(final Class<?> type) {
        return getPropertyTypesMap(type, null);
    }

    public static Map<String, Class<?>> getPropertyTypesMap(final Class<?> type,
            Map<String, Set<Class<?>>> excludedProperties) {
        final Map<String, Class<?>> result = new HashMap<>();
        PropertyDescriptor[] propDescs
                = PropertyUtils.getPropertyDescriptors(type);
        Map<String, Set<Class<?>>> excludedProps = excludedProperties;

        if (excludedProps != null) {
            for (PropertyDescriptor propDesc : propDescs) {
                Class<?> propertyType = propDesc.getPropertyType();

                if (propertyType != null) {
                    String propertyName = propDesc.getName();

                    if (excludedProps.containsKey(propertyName)) {
                        Set<Class<?>> ignoredPropertyTypes =
                                excludedProps.get(propertyName);

                        if (!ignoredPropertyTypes.contains(type)) {
                            Set<Class<?>> superTypes
                                    = LumpiUtil.getSuperTypes(type, false, true, true);

                            for (Class<?> superType : superTypes) {
                                if (ignoredPropertyTypes.contains(superType)) {
                                    result.put(propertyName, propertyType);
                                    break;
                                }
                            }
                        }

                    }
                }
            }
        }

        return result;
    }

    public static Map<String, Object> getPropertyValuesMap(Object bean)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> result = new HashMap<>();
        PropertyDescriptor[] propDescs
                = PropertyUtils.getPropertyDescriptors(bean.getClass());

        for (PropertyDescriptor propDesc : propDescs) {
            String propertyName = propDesc.getName();
            Object propertyValue = PropertyUtils.getProperty(bean, propertyName);
            result.put(propertyName, propertyValue);
        }

        return result;
    }

}
