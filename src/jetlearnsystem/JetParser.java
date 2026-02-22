package jetlearnsystem;

import jetlearnsystem.instructions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class JetParser {
    private List<JetToken> tokens;
    private int currentTokenIndex;
    private static Map<String, FunctionDefinition> functionTable = new HashMap<>();

    public JetParser(List<JetToken> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public List<JetInstruction> parse() throws JetParseException {
        List<JetInstruction> program = new ArrayList<>();
        if (tokens.isEmpty()) {
            return program;
        }
        while (peek() != null) {
            program.add(parseInstruction());
        }
        return program;
    }

    private JetToken peek() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        }
        return null;
    }

    private JetToken consume(JetTokenType expectedType) throws JetParseException {
        JetToken token = peek();
        if (token == null) {
            throw new JetParseException("予期しないコマンドの終わりです。'" + expectedType + "' が期待されました。", tokens.size() > 0 ? tokens.get(tokens.size() - 1).position : 0);
        }
        if (token.type == expectedType) {
            currentTokenIndex++;
            return token;
        } else {
            throw new JetParseException("予期しないトークンです。'" + expectedType + "' が期待されましたが、'" + token.value + "'(" + token.type + ") が見つかりました。", token.position);
        }
    }

    private JetInstruction parseInstruction() throws JetParseException {
        JetToken firstToken = peek();
        if (firstToken == null) {
            throw new JetParseException("コマンドが空です。", 0);
        }
        
        switch (firstToken.type) {
            case FD:
                consume(JetTokenType.FD);
                Object fdDistance = parseFact();
                consume(JetTokenType.MOVE_AMOUNT);
                return new FdInstruction(fdDistance);
            case BK:
                consume(JetTokenType.BK);
                Object bkDistance = parseFact();
                consume(JetTokenType.MOVE_AMOUNT);
                return new BkInstruction(bkDistance);
            case RT:
                consume(JetTokenType.RT);
                Object rtAngle = parseFact();
                consume(JetTokenType.ROTATE);
                return new RtInstruction(rtAngle);
            case LT:
                consume(JetTokenType.LT);
                Object ltAngle = parseFact();
                consume(JetTokenType.ROTATE);
                return new LtInstruction(ltAngle);
            case CS:
                consume(JetTokenType.CS);
                return new CsInstruction();
            case SET_COLOR:
                consume(JetTokenType.SET_COLOR);
                JetToken colorToken = consume(JetTokenType.COLOR_NAME);
                consume(JetTokenType.CHANGE);
                return new SetColorInstruction(colorToken.value);
            case REPEAT:
                consume(JetTokenType.REPEAT);
                Object times = parseFact();
                consume(JetTokenType.TIMES_REPEAT);
                List<JetInstruction> instructionsToRepeat = parseCodeBlock();
                return new RepeatInstruction(times, instructionsToRepeat);
            case FUNC_KEYWORD:
                consume(JetTokenType.FUNC_KEYWORD);
                String funcName = consume(JetTokenType.NAME).value;
                
                if (peek() != null && peek().type == JetTokenType.CREATE) {
                    consume(JetTokenType.CREATE);
                    List<String> params = parseParameters();
                    List<JetInstruction> instructions = parseCodeBlock();
                    consume(JetTokenType.END);
                    return new ToInstruction(funcName, params, instructions);

                } else if (peek() != null && peek().type == JetTokenType.FUNC_CALL) {
                    consume(JetTokenType.FUNC_CALL);
                    List<Object> args = parseArguments();
                    return new FuncInstruction(funcName, args);
                } else {
                    throw new JetParseException("関数の後ろには「を作る」または「を動かす」が期待されました。", peek().position);
                }
            case WAIT:
                consume(JetTokenType.WAIT);
                Object waitDuration = parseFact();
                return new WaitInstruction(waitDuration);
            case IF:
                consume(JetTokenType.IF);
                Condition condition = parseCondition();
                consume(JetTokenType.THEN);
                List<JetInstruction> ifBlockInstructions = parseCodeBlock();
                return new IfInstruction(condition, ifBlockInstructions);
            
            case POS:
                consume(JetTokenType.POS);
                return new PosInstruction();
            
            default:
                throw new JetParseException("不明なコマンド、または文法エラーです: '" + firstToken.value + "'", firstToken.position);
        }
    }
    
    private List<JetInstruction> parseCodeBlock() throws JetParseException {
        List<JetInstruction> instructions = new ArrayList<>();
        consume(JetTokenType.LBRACKET);
        while (peek() != null && peek().type != JetTokenType.RBRACKET) {
            instructions.add(parseInstruction());
        }
        consume(JetTokenType.RBRACKET);
        return instructions;
    }
    
    private List<String> parseParameters() throws JetParseException {
        List<String> params = new ArrayList<>();
        while (peek() != null && peek().type == JetTokenType.CONST) {
            params.add(consume(JetTokenType.CONST).value);
        }
        return params;
    }

    private int parseCalcNum() throws JetParseException {
        Object result = parseTerm();
        if (!(result instanceof Integer)) {
             throw new JetParseException("計算式に変数を含めることはまだできません。", 0);
        }
        int finalResult = (Integer) result;

        while (peek() != null) {
            if (peek().type == JetTokenType.PLUS) {
                consume(JetTokenType.PLUS);
                finalResult += (Integer)parseTerm();
            } else if (peek().type == JetTokenType.MINUS) {
                consume(JetTokenType.MINUS);
                finalResult -= (Integer)parseTerm();
            } else {
                break;
            }
        }
        return finalResult;
    }

    private int parseTerm() throws JetParseException {
         Object result = parseFact();
        if (!(result instanceof Integer)) {
             throw new JetParseException("計算式に変数を含めることはまだできません。", 0);
        }
        int finalResult = (Integer) result;

        while (peek() != null) {
            if (peek().type == JetTokenType.TIMES) {
                consume(JetTokenType.TIMES);
                finalResult *= (Integer)parseFact();
            } else if (peek().type == JetTokenType.DIVIDE) {
                consume(JetTokenType.DIVIDE);
                finalResult /= (Integer)parseFact();
            } else {
                break;
            }
        }
        return finalResult;
    }

    private Object parseFact() throws JetParseException {
        JetToken token = peek();
        if (token.type == JetTokenType.NUM) {
            return Integer.parseInt(consume(JetTokenType.NUM).value);
        } else if (token.type == JetTokenType.CONST) {
            return consume(JetTokenType.CONST).value;
        } else if (token.type == JetTokenType.LPAREN) {
            consume(JetTokenType.LPAREN);
            int result = parseCalcNum();
            consume(JetTokenType.RPAREN);
            return result;
        } else {
            throw new JetParseException("計算式で予期しないトークンです: " + token.value, token.position);
        }
    }

    private List<Object> parseArguments() throws JetParseException {
        List<Object> args = new ArrayList<>();
        while (peek() != null && 
               peek().type != JetTokenType.RBRACKET && 
               peek().type != JetTokenType.FUNC_KEYWORD &&
               peek().type != JetTokenType.REPEAT &&
               peek().type != JetTokenType.IF) { 
            args.add(parseFact());
        }
        return args;
    }

    private Condition parseCondition() throws JetParseException {
        Object left = parseFact();
        JetToken opToken = consume(JetTokenType.OP);
        Object right = parseFact();
        return new BinaryCondition(left, opToken.value, right);
    }

    public static Map<String, FunctionDefinition> getFunctionTable() {
        return functionTable;
    }
}