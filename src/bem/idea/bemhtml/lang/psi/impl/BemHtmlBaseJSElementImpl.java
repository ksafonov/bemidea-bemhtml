package bem.idea.bemhtml.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.LiteralTextEscaper;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BemHtmlBaseJSElementImpl extends BemHtmlElementImpl implements PsiLanguageInjectionHost {

    public BemHtmlBaseJSElementImpl(ASTNode node) {
        super(node);
    }

    public PsiLanguageInjectionHost updateText(@NotNull String text) {
        ASTNode valueNode = getNode().getFirstChildNode();
        ((LeafElement)valueNode).replaceWithText(text);
        return this;
    }

    @NotNull
    public LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper() {
        return new StringLiteralEscaper(this);
    }

    @Override
    public boolean isValidHost() {
        return true;
    }

    // Copy from IDEA10 to avoid exceptions in IDEA11
    public class StringLiteralEscaper<T extends PsiLanguageInjectionHost> extends LiteralTextEscaper<T> {
        private int[] outSourceOffsets;

        public StringLiteralEscaper(T host) {
            super(host);
        }

        public boolean decode(@NotNull final TextRange rangeInsideHost, @NotNull StringBuilder outChars) {
            String subText = rangeInsideHost.substring(myHost.getText());
            outSourceOffsets = new int[subText.length()+1];
            return parseStringCharacters(subText, outChars, outSourceOffsets);
        }

        public int getOffsetInHost(int offsetInDecoded, @NotNull final TextRange rangeInsideHost) {
            int result = offsetInDecoded < outSourceOffsets.length ? outSourceOffsets[offsetInDecoded] : -1;
            if (result == -1) return -1;
            return (result <= rangeInsideHost.getLength() ? result : rangeInsideHost.getLength()) + rangeInsideHost.getStartOffset();
        }

        public boolean isOneLine() {
            return true;
        }

        public boolean parseStringCharacters(@NotNull String chars, @NotNull StringBuilder outChars, @Nullable int[] sourceOffsets) {
          assert sourceOffsets == null || sourceOffsets.length == chars.length()+1;
          if (chars.indexOf('\\') < 0) {
            outChars.append(chars);
            if (sourceOffsets != null) {
              for (int i = 0; i < sourceOffsets.length; i++) {
                sourceOffsets[i] = i;
              }
            }
            return true;
          }
          int index = 0;
          final int outOffset = outChars.length();
          while (index < chars.length()) {
            char c = chars.charAt(index++);
            if (sourceOffsets != null) {
              sourceOffsets[outChars.length()-outOffset] = index - 1;
              sourceOffsets[outChars.length() + 1 -outOffset] = index;
            }
            if (c != '\\') {
              outChars.append(c);
              continue;
            }
            if (index == chars.length()) return false;
            c = chars.charAt(index++);
            switch (c) {
              case'b':
                outChars.append("\\b");
                break;

              case't':
                outChars.append("\\t");
                break;

              case'n':
                outChars.append("\\n");
                break;
              case'f':
                outChars.append("\\f");
                break;

              case'r':
                outChars.append("\\r");
                break;

              case'"':
                outChars.append("\\\"");
                break;

              case'\'':
                outChars.append("\\'");
                break;

              case'\\':
                outChars.append("\\\\");
                break;

              case'0':
              case'1':
              case'2':
              case'3':
              case'4':
              case'5':
              case'6':
              case'7': {
                char startC = c;
                int v = (int)c - '0';
                if (index < chars.length()) {
                  c = chars.charAt(index++);
                  if ('0' <= c && c <= '7') {
                    v <<= 3;
                    v += c - '0';
                    if (startC <= '3' && index < chars.length()) {
                      c = chars.charAt(index++);
                      if ('0' <= c && c <= '7') {
                        v <<= 3;
                        v += c - '0';
                      }
                      else {
                        index--;
                      }
                    }
                  }
                  else {
                    index--;
                  }
                }
                outChars.append((char)v);
              }
              break;

              case'u':
                if (index + 4 <= chars.length()) {
                  try {
                    int v = Integer.parseInt(chars.substring(index, index + 4), 16);
                    //line separators are invalid here
                    if (v == 0x000a || v == 0x000d) return false;
                    c = chars.charAt(index);
                    if (c == '+' || c == '-') return false;
                    outChars.append((char)v);
                    index += 4;
                  }
                  catch (Exception e) {
                    return false;
                  }
                }
                else {
                  return false;
                }
                break;

              default:
                return false;
            }
            if (sourceOffsets != null) {
              sourceOffsets[outChars.length()-outOffset] = index;
            }
          }
          return true;
        }

    }
}
