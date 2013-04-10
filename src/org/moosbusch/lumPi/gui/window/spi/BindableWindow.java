/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.window.spi;

import org.apache.pivot.beans.Bindable;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Window;

/**
 *
 * @author moosbusch
 */
public abstract class BindableWindow extends Window implements Bindable {

    public BindableWindow() {
    }

    public BindableWindow(Component content) {
        super(content);
    }

}
