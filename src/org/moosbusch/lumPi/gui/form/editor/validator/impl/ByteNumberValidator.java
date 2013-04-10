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
public class ByteNumberValidator extends AbstractNumberValidator<Byte> {

    @Override
    public Validator getRangeValidator() {
        return new IntRangeValidator(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

}
