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
package org.moosbusch.lumPi.gui;

import java.util.Comparator;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.pivot.collections.LinkedList;
import org.apache.pivot.wtk.Component;
import org.moosbusch.lumPi.util.LumPiUtil;

/**
 *
 * @author moosbusch
 */
public interface Selection<T extends Object> {

    public T getValue();

    public Expression getExpression();

    public Component getEventSource();

    public void setEventSource(Component evtSource);

    public T evaluate() throws Exception;


    public static final class Expression extends LinkedList<String> {

        private static final long serialVersionUID = -7096146211981035687L;
        public static final char DEFAULT_EXPRESSION_TOKEN_SEPARATOR = '.';
        private char separatorChar;

        public Expression() {
            this.separatorChar = DEFAULT_EXPRESSION_TOKEN_SEPARATOR;
        }

        public Expression(char separatorChar) {
            this.separatorChar = Objects.requireNonNull(separatorChar);
        }

        public Expression(String expression) {
            this(expression, DEFAULT_EXPRESSION_TOKEN_SEPARATOR);
        }

        public Expression(String expression, char separatorChar) {
            this.separatorChar = Objects.requireNonNull(separatorChar);
            parseExpression(expression);
        }

        private void parseExpression(String expr) {
            clear();

            if (StringUtils.isNotBlank(expr)) {
                String[] exprTokens = StringUtils.split(
                        LumPiUtil.requireNotBlank(expr), getSeparatorChar());

                for (String exprToken : exprTokens) {
                    add(StringUtils.deleteWhitespace(exprToken));
                }
            }
        }

        public String getSubExpression(int startIndex, int tokenCount) {
            String separator = new String(new char[]{getSeparatorChar()});
            String result = new String();

            if (tokenCount == 0) {
                return get(startIndex);
            } else {
                for (int cnt = 0; cnt < tokenCount; cnt++) {
                    String token = get(startIndex + cnt);
                    result = result + getSeparatorChar() + token;
                }

                if (StringUtils.startsWith(result, separator)) {
                    result = StringUtils.substringAfter(result, separator);
                }

                if (StringUtils.endsWith(result, separator)) {
                    result = StringUtils.substringBeforeLast(result, separator);
                }
            }

            return result;
        }

        public String getSubExpression(int tokenCount) {
            return getSubExpression(0, tokenCount);
        }

        public char getSeparatorChar() {
            return separatorChar;
        }

        public void setSeparatorChar(char separatorChar) {
            this.separatorChar = Objects.requireNonNull(separatorChar);
        }

        @Override
        public void setComparator(Comparator<String> comparator) {
        }

        @Override
        public String toString() {
            if (!isEmpty()) {
                String result = getSubExpression(0, getLength());

                if (StringUtils.isNotBlank(result)) {
                    return result;
                }
            }

            return super.toString();
        }
    }
}
