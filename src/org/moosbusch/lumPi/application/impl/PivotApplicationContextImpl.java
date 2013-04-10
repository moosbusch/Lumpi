/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.application.impl;

import org.moosbusch.lumPi.application.DesktopApplication;
import org.moosbusch.lumPi.application.spi.AbstractPivotApplicationContext;

/**
 *
 * @author moosbusch
 */
public class PivotApplicationContextImpl extends AbstractPivotApplicationContext {

    public PivotApplicationContextImpl(DesktopApplication application) {
        super(application);
    }

    @Override
    protected PivotAnnotationConfigApplicationContext createAnnotationContext() {
        return new PivotAnnotationConfigApplicationContext();
    }

}
