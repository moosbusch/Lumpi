/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.window.spi;

import org.apache.pivot.beans.Bindable;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Frame;

/**
 *
 * @author moosbusch
 */
public abstract class BindableFrame extends Frame implements Bindable {

    public BindableFrame() {
    }

    public BindableFrame(String title) {
        super(title);
    }

    public BindableFrame(Component content) {
        super(content);
    }

    public BindableFrame(String title, Component content) {
        super(title, content);
    }

}
