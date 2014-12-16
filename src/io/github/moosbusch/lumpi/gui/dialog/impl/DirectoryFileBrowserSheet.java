/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.moosbusch.lumpi.gui.dialog.impl;

import java.io.File;
import org.apache.pivot.util.Filter;
import io.github.moosbusch.lumpi.util.LumpiUtil;

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
        LumpiUtil.setComponentStyle(getFileBrowser(), "hideDisabledFiles", true);
    }

    private static class DirectoryFileFilter implements Filter<File> {

        @Override
        public boolean include(File item) {
            return ! (item.isDirectory());
        }
    }
}
