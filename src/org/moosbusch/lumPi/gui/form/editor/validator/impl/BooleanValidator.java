/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.form.editor.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.wtk.validation.Validator;

/**
 *
 * @author moosbusch
 */
public class BooleanValidator implements Validator {

    @Override
    public boolean isValid(String text) {
        if (StringUtils.isNotBlank(text)) {
            return ((text.toLowerCase().equals("true")) ||
                    (text.toLowerCase().equals("false")));
        }

        return false;
    }

}
