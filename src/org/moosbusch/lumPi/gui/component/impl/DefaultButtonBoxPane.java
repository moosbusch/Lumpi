/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.component.impl;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Orientation;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author moosbusch
 */
public class DefaultButtonBoxPane extends BoxPane {

    public DefaultButtonBoxPane() {
        this(Orientation.HORIZONTAL);
    }

    public DefaultButtonBoxPane(Orientation orientation) {
        super(orientation);
        init();
    }

    private void init() {
        PivotUtil.setComponentStyle(this, "padding", 10);
        PivotUtil.setComponentStyle(this, "horizontalAlignment", "right");
        PivotUtil.setComponentStyle(this, "verticalAlignment", "bottom");
    }

}
