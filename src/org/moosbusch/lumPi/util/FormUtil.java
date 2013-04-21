/*

 *
 */
package org.moosbusch.lumPi.util;

//import com.google.inject.Injector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author moosbusch
 */
public class FormUtil {

    public static final String COLLECTION_EMPTY_PROPERTY_NAME = "empty";
    public static final String CLASS_PROPERTY_NAME = "class";

    private FormUtil() {
    }

    public static boolean isExcludedProperty(Class<?> type, String propertyName,
            Map<String, Class<?>[]>... excludedProperties) {
        Map<String, Class<?>[]> excludedProps = null;

        if (ArrayUtils.isNotEmpty(excludedProperties)) {
            for (Map<String, Class<?>[]> exPropsMap : excludedProperties) {
                if (excludedProps == null) {
                    excludedProps = exPropsMap;
                } else {
                    excludedProps.putAll(exPropsMap);
                }
            }
        } else {
            excludedProps = new HashMap<>();
        }

        if (excludedProps.containsKey(propertyName)) {
            Class<?>[] ignPropsType = excludedProps.get(propertyName);

            Set<Class<?>> superTypes = PivotUtil.getSuperTypes(
                    type, true, true, true);

            if (!ArrayUtils.contains(ignPropsType, type)) {
                for (Class<?> superType : superTypes) {
                    if (!ArrayUtils.contains(ignPropsType, superType)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static Map<String, Class<?>[]> getDefaultIgnoredProperties() {
        Map<String, Class<?>[]> result = new HashMap<>();
        result.put(CLASS_PROPERTY_NAME, new Class<?>[]{java.lang.Class.class});
        result.put(COLLECTION_EMPTY_PROPERTY_NAME, new Class<?>[]{java.util.Collection.class,
            org.apache.pivot.collections.Collection.class, java.lang.String.class});
        return result;
    }

    public static Class<?>[] getDefaultIgnoredTypes() {
        return new Class<?>[] {Class.class, ClassLoader.class};
    }

    public static Map<String, Class<?>> getPropertyTypesMap(final Class<?> type,
            Map<String, Class<?>[]>... excludedProperties) {
        final Map<String, Class<?>> result = new HashMap<>(0);
        PropertyDescriptor[] propDescs =
                PropertyUtils.getPropertyDescriptors(type);
        Map<String, Class<?>[]> excludedProps = null;

        if (ArrayUtils.isNotEmpty(excludedProperties)) {
            for (Map<String, Class<?>[]> exPropsMap : excludedProperties) {
                if (excludedProps == null) {
                    excludedProps = exPropsMap;
                } else {
                    excludedProps.putAll(exPropsMap);
                }
            }
        } else {
            excludedProps = new HashMap<>();
        }

        for (PropertyDescriptor propDesc : propDescs) {
            Class<?> propertyType = propDesc.getPropertyType();

            if (propertyType != null) {
                String propertyName = propDesc.getName();

                if (excludedProps.containsKey(propertyName)) {
                    Class<?>[] ignPropsType = excludedProps.get(
                            Objects.requireNonNull(propertyName));

                    if (!ArrayUtils.contains(ignPropsType, type)) {
                        Set<Class<?>> superTypes =
                                PivotUtil.getSuperTypes(type, false, true, true);

                        for (Class<?> superType : superTypes) {
                            if (ArrayUtils.contains(ignPropsType, superType)) {
                                continue;
                            }
                        }
                    }

                    continue;
                }

                result.put(propertyName, propertyType);
            }
        }

        return result;
    }

    public static Map<String, Object> getPropertyValuesMap(Object bean)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> result = new HashMap<>();
        PropertyDescriptor[] propDescs =
                PropertyUtils.getPropertyDescriptors(bean.getClass());

        for (PropertyDescriptor propDesc : propDescs) {
            String propertyName = propDesc.getName();
            Object propertyValue = PropertyUtils.getProperty(bean, propertyName);
            result.put(propertyName, propertyValue);
        }

        return result;
    }
}
