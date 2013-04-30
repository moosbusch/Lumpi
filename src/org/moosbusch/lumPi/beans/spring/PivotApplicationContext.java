/*

 *
 */
package org.moosbusch.lumPi.beans.spring;

import org.apache.pivot.beans.Resolvable;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author moosbusch
 */
public interface PivotApplicationContext
    extends ConfigurableApplicationContext, Resolvable {

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException;

    @Override
    public boolean containsBean(String id);

    @Override
    public Object getBean(String id);

    @Override
    public <T> T getBean(String name, Class<T> requiredType);

    @Override
    public Object getBean(String name, Object... args) throws BeansException;

    @Override
    public PivotApplicationContext getParent();

    public PivotApplicationContext getChild();

    public void setChild(PivotApplicationContext childContext);

    public boolean isParent();

    public boolean isChild();

}
