/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.util;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moosbusch
 */
public abstract class EdtRunnable implements Runnable {

    public final void invokeLater() {
        EventQueue.invokeLater(this);
    }

    public final void invokeAndWait() {
        if (!EventQueue.isDispatchThread()) {
            try {
                EventQueue.invokeAndWait(this);
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.getLogger(EdtRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            invokeLater();
        }
    }
}
