/*

 *
 */
package org.moosbusch.lumPi.beans.spring.spi;

import java.util.Objects;
import org.moosbusch.lumPi.beans.spring.SpringAnnotationInjector;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractSpringAnnotationInjector<T extends ConfigurableBeanFactory,
        V extends AutowiredAnnotationBeanPostProcessor> implements SpringAnnotationInjector<T, V> {

    private final T beanFactory;
    private final V beanPostProcessor;

    public AbstractSpringAnnotationInjector(T beanFactory, V beanPostProcessor) {
        this.beanFactory = Objects.requireNonNull(beanFactory);
        this.beanPostProcessor = Objects.requireNonNull(beanPostProcessor);
        init();
    }

    public AbstractSpringAnnotationInjector(T beanFactory) {
        this.beanFactory = Objects.requireNonNull(beanFactory);
        this.beanPostProcessor = initBeanPostProcessor();
        init();
    }

    private void init() {
        getBeanPostProcessor().setBeanFactory(getBeanFactory());
    }

    private V initBeanPostProcessor() {
        return Objects.requireNonNull(createBeanPostProcessor());
    }

    protected abstract V createBeanPostProcessor();

    @Override
    public T getBeanFactory() {
        return beanFactory;
    }

    @Override
    public V getBeanPostProcessor() {
        return beanPostProcessor;
    }

    @Override
    public void inject(Object bean) {
        getBeanPostProcessor().processInjection(bean);
    }
}
