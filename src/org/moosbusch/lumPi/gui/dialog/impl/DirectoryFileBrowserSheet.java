/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.dialog.impl;

import java.io.File;
import org.apache.pivot.util.Filter;
import org.moosbusch.lumPi.util.PivotUtil;

/**
 *
 * @author Gunnar Kappei
 */
public class DirectoryFileBrowserSheet extends DefaultSubmitableFileBrowserSheet {

    public DirectoryFileBrowserSheet() {
        init();
    }

    private void init() {
        setDisabledFileFilter(new DirectoryFileFilter());
        PivotUtil.setComponentStyle(getFileBrowser(), "hideDisabledFiles", true);
    }

    private class DirectoryFileFilter implements Filter<File> {

        @Override
        public boolean include(File item) {
            return ! (item.isDirectory());
        }
    }
}
