/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.validator.impl;

import org.apache.pivot.wtk.validation.DoubleRangeValidator;
import org.apache.pivot.wtk.validation.Validator;
import org.moosbusch.lumPi.gui.form.editor.validator.spi.AbstractNumberValidator;

/**
 *
 * @author moosbusch
 */
public class DoubleNumberValidator extends AbstractNumberValidator<Double> {

    @Override
    public Validator getRangeValidator() {
        return new DoubleRangeValidator(Double.MIN_VALUE, Double.MAX_VALUE);
    }
}
