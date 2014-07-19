/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.beans;

import org.apache.pivot.beans.BeanAdapter;

/**
 *
 * @author Gunnar Kappei
 */
public interface Bean extends PropertyChangeAware {

    public BeanAdapter getAdapter();

    public Object get(String key);

    public Object set(String key, Object value);

}
