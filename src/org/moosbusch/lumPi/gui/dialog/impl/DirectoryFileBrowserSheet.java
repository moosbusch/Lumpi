/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moosbusch.lumPi.gui.dialog.impl;

import java.io.File;
import org.apache.pivot.util.Filter;
import org.moosbusch.lumPi.util.LumPiUtil;

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
        LumPiUtil.setComponentStyle(getFileBrowser(), "hideDisabledFiles", true);
    }

    private static class DirectoryFileFilter implements Filter<File> {

        @Override
        public boolean include(File item) {
            return ! (item.isDirectory());
        }
    }
}
