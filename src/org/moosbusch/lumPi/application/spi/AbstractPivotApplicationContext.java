/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.application.spi;

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
import org.moosbusch.lumPi.application.PivotApplicationContext;
import org.moosbusch.lumPi.application.SpringAnnotationInjector;
import org.moosbusch.lumPi.application.impl.DefaultSpringAnnotationInjector;
import org.moosbusch.lumPi.application.impl.PivotAnnotationConfigApplicationContext;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractPivotApplicationContext<T extends PivotAnnotationConfigApplicationContext>
        extends ClassPathXmlApplicationContext implements PivotApplicationContext {

    private SpringAnnotationInjector<?, ?> injector;

    public AbstractPivotApplicationContext(DesktopApplication application) {
        init(application);
    }

    private void init(DesktopApplication application) {
        Class<?> pivotFactoryBeanClass = Objects.requireNonNull(
                application.getPivotBeanFactoryClass());
        String[] beanPackages = application.getAnnotatedBeanPackages();
        Class<?>[] annotatedClasses = application.getAnnotatedClasses();
        registerShutdownHook();
        setConfigLocations(configLocationsFromURLs(
                application.getBeanConfigurations()));

        T annoCtx =
                Objects.requireNonNull(createAnnotationContext());
        annoCtx.registerShutdownHook();
        annoCtx.setLocation(application.getBXMLConfiguration());
        annoCtx.refresh();
        annoCtx.setChild(this);
        setParent(annoCtx);
        refresh();

        if ((ArrayUtils.isNotEmpty(annotatedClasses))) {
            annoCtx.register(ArrayUtils.add(annotatedClasses, pivotFactoryBeanClass));
        } else {
            annoCtx.register(pivotFactoryBeanClass);
        }

        if ((ArrayUtils.isNotEmpty(beanPackages))) {
            annoCtx.scan(beanPackages);
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

    protected abstract T createAnnotationContext();

    @Override
    public SpringAnnotationInjector<?, ?> getInjector() {
        if (injector == null) {
            injector = new DefaultSpringAnnotationInjector(
                    getBeanFactory());
        }

        return injector;
    }

    @Override
    public final PivotApplicationContext getChild() {
        return null;
    }

    @Override
    public final void setChild(PivotApplicationContext childContext) {
    }

    @Override
    public final boolean isParent() {
        return false;
    }

    @Override
    public final boolean isChild() {
        return true;
    }

    @Override
    public final boolean containsBean(String id) {
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
    public final <T> T getBean(Class<T> requiredType) throws BeansException {
        T result = super.getBean(requiredType);

        if (ClassUtils.isAssignable(result.getClass(), BindableWindow.class)) {
            SpringAnnotationInjector<?, ?> inj =
                    Objects.requireNonNull(getInjector());

            inj.inject(result);
        }

        return result;
    }

    @Override
    public final Object getBean(String id) {
        Object result = null;

        if (StringUtils.isNotBlank(id)) {
            result = super.getBean(id);

            if ((result == null) && (getParent() != null)) {
                result = getParent().getBean(id);
            }

            if ((result == null) && (getNamespace() != null)) {
                result = getNamespace().get(id);
            }
        }

        return result;
    }

    @Override
    public final <T> T getBean(String name, Class<T> requiredType) {
        Object result = super.getBean(name, requiredType);

        if ((result == null) && (getParent() != null)) {
            result = getParent().getBean(name, requiredType);
        }

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
    public final Object getBean(String name, Object... args) throws BeansException {
        Object result = super.getBean(name, args);

        if ((result == null) && (getParent() != null)) {
            result = getParent().getBean(name, args);
        }

        if ((result == null) && (getNamespace() != null)) {
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
    public final T getParent() {
        ApplicationContext result = super.getParent();

        if (result != null) {
            return (T) result;
        }

        return null;
    }

    @Override
    public final Map<String, Object> getNamespace() {
        return getParent().getNamespace();
    }

    @Override
    public final void setNamespace(Map<String, Object> pivotNamespace) {
        getParent().setNamespace(pivotNamespace);
    }

    @Override
    public final Resources getResources() {
        return getParent().getResources();
    }

    @Override
    public final void setResources(Resources resources) {
        getParent().setResources(resources);
    }

    @Override
    public URL getLocation() {
        return getParent().getLocation();
    }

    @Override
    public void setLocation(URL location) {
        getParent().setLocation(location);
    }
}
