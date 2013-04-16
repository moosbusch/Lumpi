/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.beans.spring.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.beans.BindException;
import org.apache.pivot.collections.HashSet;
import org.apache.pivot.collections.Set;
import org.apache.pivot.serialization.Serializer;
import org.moosbusch.lumPi.beans.spring.SpringAwareSerializer;
import org.moosbusch.lumPi.util.PivotUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;

/**
 *
 * @author moosbusch
 */
public class SpringAwareBXMLSerializer extends BXMLSerializer
    implements SpringAwareSerializer {
    private ApplicationContext applicationContext;
    private boolean includeSuperTypeAnnotations = true;

    public SpringAwareBXMLSerializer(ApplicationContext applicationContext) {
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    @Override
    public final void handleFieldAnnotations(Annotation[] annotations, Object object,
            Class<?> type, Field field) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof BXML) {
                handleBxmlAnnotation((BXML) annotation, object, type, field);
            } else {
                handleFieldAnnotationsImpl(annotations, object, type, field);
            }
        }
    }

    protected void handleFieldAnnotationsImpl(Annotation[] annotations, Object object,
            Class<?> type, Field field) {
    }

    protected void handleBxmlAnnotation(BXML annotation, Object object,
            Class<?> type, Field field) {
        String id = annotation.id();

        if (StringUtils.isNotBlank(id)) {
            id = field.getName();
        }

        Object fieldValue = null;

        if (getApplicationContext().containsBean(id)) {
            fieldValue = getApplicationContext().getBean(id);
        } else if (getNamespace().containsKey(id)) {
            fieldValue = getNamespace().get(id);
        }

        try {
            FieldUtils.writeField(field, object, Objects.requireNonNull(fieldValue), true);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SpringAwareBXMLSerializer.class.getName()).log(
                    Level.SEVERE, null, "Cannot inject 'null' into field '" +
                    field.getName() + "' for class '" + object.getClass().getName() +
                    "'. Check corresponding BXML-annotation!" + ex);
        }
    }

//    protected void handleMethodAnnotations(Annotation[] annotations, Object object,
//            Class<?> type, Method method) {
//    }
//
//    protected void handleConstructorAnnotations(Annotation[] annotations,
//            Object object, Class<?> type, Constructor<?> constructor) {
//    }

    @Override
    protected Object newTypedObject(Class<?> type) throws InstantiationException,
            IllegalAccessException {
        return super.newTypedObject(type);
    }

    @Override
    protected SpringAwareSerializer newIncludeSerializer(Class<? extends Serializer<?>> type)
            throws InstantiationException, IllegalAccessException {
        return new SpringAwareBXMLSerializer(getApplicationContext());
    }

    @Override
    public void bind(Object object, Class<?> type) throws BindException {
        if (!ClassUtils.isAssignableValue(type, object)) {
            throw new IllegalArgumentException();
        }

        Set<String> bindingFieldNames = new HashSet<>();
        java.util.Set<Class<?>> annotatedTypes;

        if (isIncludeSuperTypeAnnotations()) {
            annotatedTypes = PivotUtil.getSuperTypes(type, true, true, true);
        } else {
            annotatedTypes = new java.util.HashSet<>();
            annotatedTypes.add(type);
        }

        for (Class<?> annotatedType : annotatedTypes) {
            Field[] fields = annotatedType.getDeclaredFields();
//            Method[] methods = superType.getDeclaredMethods();
//            Constructor<?>[] constructors = superType.getDeclaredConstructors();

//            for (Constructor<?> constructor : constructors) {
//                Annotation[] annotations = constructor.getAnnotations();
//
//                if (ArrayUtils.isNotEmpty(annotations)) {
//                    handleConstructorAnnotations(annotations, object, type, constructor);
//                }
//            }

            for (Field field : fields) {
                if (bindingFieldNames.add(field.getName())) {
                    Annotation[] annotations = field.getAnnotations();

                    if (ArrayUtils.isNotEmpty(annotations)) {
                        handleFieldAnnotations(annotations, object, type, field);
                    }
                }
            }

//            for (Method method : methods) {
//                Annotation[] annotations = method.getAnnotations();
//
//                if (ArrayUtils.isNotEmpty(annotations)) {
//                    handleMethodAnnotations(annotations, object, type, method);
//                }
//            }
        }
    }

    @Override
    public final ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    @Override
    public boolean isIncludeSuperTypeAnnotations() {
        return includeSuperTypeAnnotations;
    }

    @Override
    public void setIncludeSuperTypeAnnotations(boolean includeSuperTypeAnnotations) {
        this.includeSuperTypeAnnotations = includeSuperTypeAnnotations;
    }
}
