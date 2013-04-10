/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.validator.spi;

import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.wtk.validation.IntRangeValidator;
import org.apache.pivot.wtk.validation.Validator;

/**
 *
 * @author moosbusch
 */
public abstract class AbstractNumberValidator<T extends Number> implements Validator {

    public abstract Validator getRangeValidator();

    @Override
    public boolean isValid(String text) {
        if (StringUtils.isNotBlank(text)) {
            return (getRangeValidator().isValid(text));
        }

        return false;
    }
}
