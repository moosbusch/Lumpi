/*

 *
 */
package org.moosbusch.lumPi.beans.spring;

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
