/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.action.spi;

import org.apache.pivot.beans.BXML;
import org.moosbusch.lumPi.action.ChildWindowAction;
import org.moosbusch.lumPi.gui.window.spi.BindableWindow;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractChildWindowAction extends LabelableAction
    implements ChildWindowAction {

    @BXML
    private BindableWindow applicationWindow;

    @Override
    public BindableWindow getApplicationWindow() {
        return applicationWindow;
    }

    @Override
    public void setApplicationWindow(BindableWindow applicationWindow) {
        this.applicationWindow = applicationWindow;
    }

}
