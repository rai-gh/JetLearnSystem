package jetlearnsystem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum JetTokenType {
    FD, BK, RT, LT, CS, SET_COLOR, REPEAT, IF, THEN, WAIT,
    FUNC_KEYWORD, NAME, CREATE, FUNC_CALL, END,
    MOVE_AMOUNT, ROTATE, CHANGE, TIMES_REPEAT,
    NUM, CONST, COLOR_NAME,
    PLUS, MINUS, TIMES, DIVIDE,
    LBRACKET, RBRACKET, LPAREN, RPAREN,
    OP,POS,
}

class JetToken {
    public JetTokenType type;
    public String value;
    public int position;
    public JetToken(JetTokenType type, String value, int position) { this.type = type; this.value = value; this.position = position; }
    @Override public String toString() { return "JetToken{" + "type=" + type + ", value='" + value + '\'' + ", position=" + position + '}'; }
}


public class JetLexer {

        private static final Map<Pattern, JetTokenType> TOKEN_PATTERNS = new LinkedHashMap<>();

    static {
        TOKEN_PATTERNS.put(Pattern.compile("^(前へ|まえへ)"), JetTokenType.FD);
        TOKEN_PATTERNS.put(Pattern.compile("^(後ろへ|うしろへ)"), JetTokenType.BK);
        TOKEN_PATTERNS.put(Pattern.compile("^(右へ|みぎへ)"), JetTokenType.RT);
        TOKEN_PATTERNS.put(Pattern.compile("^(左へ|ひだりへ)"), JetTokenType.LT);
        TOKEN_PATTERNS.put(Pattern.compile("^(初めへ|はじめへ)"), JetTokenType.CS);
        TOKEN_PATTERNS.put(Pattern.compile("^(色を|いろを)"), JetTokenType.SET_COLOR);
        TOKEN_PATTERNS.put(Pattern.compile("^(次を|つぎを)"), JetTokenType.REPEAT);
        TOKEN_PATTERNS.put(Pattern.compile("^(もし)"), JetTokenType.IF);
        TOKEN_PATTERNS.put(Pattern.compile("^(関数|かんすう)"), JetTokenType.FUNC_KEYWORD);
        TOKEN_PATTERNS.put(Pattern.compile("^(のとき)"), JetTokenType.THEN);
        TOKEN_PATTERNS.put(Pattern.compile("^(終わり|おわり)"), JetTokenType.END);
        TOKEN_PATTERNS.put(Pattern.compile("^(待機|たいき)"), JetTokenType.WAIT);
        TOKEN_PATTERNS.put(Pattern.compile("^(動く|うごく)"), JetTokenType.MOVE_AMOUNT);
        TOKEN_PATTERNS.put(Pattern.compile("^(回転|かいてん)"), JetTokenType.ROTATE);
        TOKEN_PATTERNS.put(Pattern.compile("^(に変える|にかえる)"), JetTokenType.CHANGE);
        TOKEN_PATTERNS.put(Pattern.compile("^(を作る|をつくる)"), JetTokenType.CREATE);
        TOKEN_PATTERNS.put(Pattern.compile("^(を動かす|をうごかす)"), JetTokenType.FUNC_CALL);
        TOKEN_PATTERNS.put(Pattern.compile("^(回繰り返す|かいくりかえす)"), JetTokenType.TIMES_REPEAT);
        
        TOKEN_PATTERNS.put(Pattern.compile("^(座標を表示|ざひょうをひょうじ)"), JetTokenType.POS);
        
        // 色名
        TOKEN_PATTERNS.put(Pattern.compile("^(黒|くろ|赤|あか|緑|みどり|黄色|きいろ|黄|き|青|あお|白|しろ)"), JetTokenType.COLOR_NAME);

        // 記号
        TOKEN_PATTERNS.put(Pattern.compile("^\\["), JetTokenType.LBRACKET);
        TOKEN_PATTERNS.put(Pattern.compile("^\\]"), JetTokenType.RBRACKET);
        TOKEN_PATTERNS.put(Pattern.compile("^\\("), JetTokenType.LPAREN);
        TOKEN_PATTERNS.put(Pattern.compile("^\\)"), JetTokenType.RPAREN);
        TOKEN_PATTERNS.put(Pattern.compile("^\\+"), JetTokenType.PLUS);
        TOKEN_PATTERNS.put(Pattern.compile("^-"), JetTokenType.MINUS);
        TOKEN_PATTERNS.put(Pattern.compile("^\\*"), JetTokenType.TIMES);
        TOKEN_PATTERNS.put(Pattern.compile("^/"), JetTokenType.DIVIDE);
        TOKEN_PATTERNS.put(Pattern.compile("^(=|<>|<=|>=|<|>)"), JetTokenType.OP);

        // データ型
        TOKEN_PATTERNS.put(Pattern.compile("^[0-9０-９]+"), JetTokenType.NUM);
        TOKEN_PATTERNS.put(Pattern.compile("^:[a-zA-Z\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF][a-zA-Z0-9\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]*"), JetTokenType.CONST);
    }
    
    // NAMEのパターンは分離
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF][a-zA-Z0-9\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]*");

    public List<JetToken> tokenize(String input) throws JetLexerException {
        List<JetToken> tokens = new ArrayList<>();
        int currentPos = 0;

        while (currentPos < input.length()) {
            String remaining = input.substring(currentPos).replaceAll("^[\\s　]+", "");
            int originalLengthBeforeTrim = input.substring(currentPos).length();
            currentPos += (originalLengthBeforeTrim - remaining.length());

            if (remaining.isEmpty()) break;

            boolean matched = false;
            
            // 1. まず定義済みの全キーワード・記号・データ型を試す
            for (Map.Entry<Pattern, JetTokenType> entry : TOKEN_PATTERNS.entrySet()) {
                Matcher matcher = entry.getKey().matcher(remaining);
                if (matcher.find()) {
                    String value = matcher.group();
                    tokens.add(new JetToken(entry.getValue(), value, currentPos));
                    currentPos += value.length();
                    matched = true;
                    break;
                }
            }

            if (matched) continue;

            // 2. キーワードにマッチしなかった場合、NAMEの可能性を試す
            Matcher nameMatcher = NAME_PATTERN.matcher(remaining);
            if (nameMatcher.find()) {
                String value = nameMatcher.group();
                tokens.add(new JetToken(JetTokenType.NAME, value, currentPos));
                currentPos += value.length();
                matched = true;
            }

            // 3. それでもマッチしないならエラー
            if (!matched) {
                throw new JetLexerException("認識できない文字です: '" + remaining.charAt(0) + "'", currentPos);
            }
        }
        return tokens;
    }
}