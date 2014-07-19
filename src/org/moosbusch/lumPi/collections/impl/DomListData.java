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
package org.moosbusch.lumPi.collections.impl;

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
