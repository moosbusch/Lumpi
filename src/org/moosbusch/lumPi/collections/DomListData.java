/*

 *
 */
package org.moosbusch.lumPi.collections;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.wtk.content.TreeBranch;
import org.apache.pivot.wtk.content.TreeNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author moosbusch
 */
public class DomListData extends ArrayList<TreeNode> {

    private static final long serialVersionUID = 3814066462648114463L;

    public final void fromDocument(Document document) {
        fromNode(document.getDocumentElement());
    }

    public void fromNode(Node node) {
        clear();
        TreeNode treeNode = traverseNode(node);
        add(treeNode);
    }

    private TreeNode traverseNode(Node elem) {
        TreeNode result;

        if (elem.hasChildNodes()) {
            result = new TreeBranch();
            NodeList childNodes = elem.getChildNodes();

            for (int cnt = 0; cnt < childNodes.getLength(); cnt++) {
                Node childNode = childNodes.item(cnt);
                ((TreeBranch)result).add(traverseNode(childNode));
            }
        } else {
            result = new TreeNode();
        }

        result.setText(elem.getNodeName());
        return result;
    }
}
