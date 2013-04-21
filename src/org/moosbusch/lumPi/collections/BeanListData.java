/*

 *
 */
package org.moosbusch.lumPi.collections;

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
