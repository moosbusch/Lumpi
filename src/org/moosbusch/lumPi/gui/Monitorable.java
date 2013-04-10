/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui;

import org.apache.pivot.beans.BeanMonitor;

/**
 *
 * @author moosbusch
 */
public interface Monitorable {

    public BeanMonitor getMonitor();

    public static class Adapter implements Monitorable {

        private final BeanMonitor monitor;

        public Adapter() {
            this.monitor = new BeanMonitor(this);
        }

        @Override
        public BeanMonitor getMonitor() {
            return monitor;
        }

    }
}
