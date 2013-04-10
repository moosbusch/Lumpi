/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.action;

import org.apache.pivot.beans.Bindable;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public interface ChildWindowAction extends Bindable {

    public BindableWindow getApplicationWindow();

    public void setApplicationWindow(BindableWindow applicationWindow);
}
