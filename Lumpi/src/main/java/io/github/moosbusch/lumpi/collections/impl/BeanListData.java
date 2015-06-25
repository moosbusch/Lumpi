/*
Copyright 2013 Gunnar Kappei

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package io.github.moosbusch.lumpi.collections.impl;

import org.apache.pivot.beans.BeanAdapter;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;

/**
 *
 * @author moosbusch
 */
public class BeanListData extends ArrayList<TreeNode> {

    private static final long serialVersionUID = -5650360576646682822L;

    public TreeNode fromBean(Object bean) {
        TreeBranch result = new TreeBranch();
        BeanAdapter beanAdapter;

        if (bean instanceof BeanAdapter) {
            beanAdapter = (BeanAdapter) bean;
        } else {
            beanAdapter = new BeanAdapter(bean);
        }

        for (String property : beanAdapter) {
            TreeNode node = new TreeNode();
            node.setText(property);
            result.add(node);
            Object propertyValue = beanAdapter.get(property);

            if (propertyValue != null) {
                result.add(fromBean(propertyValue));
            }
        }

        return null;
    }
}
