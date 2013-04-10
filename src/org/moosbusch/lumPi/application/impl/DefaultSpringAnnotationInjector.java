/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.application.impl;

import org.moosbusch.lumPi.beans.spring.spi.AbstractSpringAnnotationInjector;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 *
 * @author moosbusch
 */
public class DefaultSpringAnnotationInjector extends AbstractSpringAnnotationInjector
        <ConfigurableBeanFactory, AutowiredAnnotationBeanPostProcessor> {

    public DefaultSpringAnnotationInjector(ConfigurableBeanFactory beanFactory,
            AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        super(beanFactory, beanPostProcessor);
    }

    public DefaultSpringAnnotationInjector(ConfigurableBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected AutowiredAnnotationBeanPostProcessor createBeanPostProcessor() {
        return new AutowiredAnnotationBeanPostProcessor();
    }

}
