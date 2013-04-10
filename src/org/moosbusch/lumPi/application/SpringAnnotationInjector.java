/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.application;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 *
 * @author moosbusch
 */
public interface SpringAnnotationInjector<T extends ConfigurableBeanFactory,
        V extends AutowiredAnnotationBeanPostProcessor> {

    public T getBeanFactory();

    public V getBeanPostProcessor();

    public void inject(Object bean);
}
