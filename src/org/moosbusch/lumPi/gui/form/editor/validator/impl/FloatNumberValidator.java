/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.validator.impl;

import org.apache.pivot.wtk.validation.FloatRangeValidator;
import org.apache.pivot.wtk.validation.Validator;
import org.moosbusch.lumPi.gui.form.editor.validator.spi.AbstractNumberValidator;

/**
 *
 * @author moosbusch
 */
public class FloatNumberValidator extends AbstractNumberValidator<Float> {

    @Override
    public Validator getRangeValidator() {
        return new FloatRangeValidator(Float.MIN_VALUE, Float.MAX_VALUE);
    }
}
