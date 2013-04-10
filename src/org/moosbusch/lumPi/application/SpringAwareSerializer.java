/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.application;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.apache.pivot.beans.Resolvable;
import org.apache.pivot.serialization.Serializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author moosbusch
 */
public interface SpringAwareSerializer extends Serializer<Object>,
        Resolvable, ApplicationContextAware {

    public void handleFieldAnnotations(Annotation[] annotations, Object object,
            Class<?> type, Field field);

    public boolean isIncludeSuperTypeAnnotations();

    public void setIncludeSuperTypeAnnotations(boolean includeSuperTypeAnnotations);

    public ApplicationContext getApplicationContext();
}
