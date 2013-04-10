/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.validator.impl;

import org.apache.pivot.wtk.validation.IntRangeValidator;
import org.apache.pivot.wtk.validation.Validator;
import org.moosbusch.lumPi.gui.form.editor.validator.spi.AbstractNumberValidator;

/**
 *
 * @author moosbusch
 */
public class ShortNumberValidator extends AbstractNumberValidator<Short> {

    @Override
    public Validator getRangeValidator() {
        return new IntRangeValidator(Short.MIN_VALUE, Short.MAX_VALUE);
    }
}
