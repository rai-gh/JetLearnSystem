# JetLearnSystem 開発者向けドキュメント

## 概要

JetLearnSystemは、日本語で記述されたタートルグラフィックスプログラミング言語「JET」を学習・実行できるJavaベースの教育アプリケーションです。

**作成者**: Ryoma Ohno・Raiki Yoshino（法政大学）  
**Version**: 1.0.3 (2026/02/24)

---

## アーキテクチャ概要

システムは以下の3つの主要な層で構成されています：

1. **プレゼンテーション層** - GUI フレーム（Swing）
2. **ビジネスロジック層** - Controllerなど
3. **言語処理層** - Lexer・Parserなど

---

## クラス仕様書

### エントリーポイント

#### `JetLearnSystem`
**ファイル**: `src/jetlearnsystem/JetLearnSystem.java`

**機能**: アプリケーションのメインエントリーポイント

**主要メソッド**:
- `main(String[] args)` - アプリケーション起動
  - Nimbus ルック&フィールを設定
  - イベントディスパッチスレッドで`HomeFrame`を表示

**用途**: 起動処理の全体的な流れを制御します。

---

### GUI フレームクラス

#### `HomeFrame`
**ファイル**: `src/jetlearnsystem/HomeFrame.java`

**機能**: ホーム画面。ユーザーが2つのモード（自由使用、学習）を選択

**属性**:
- `jButton1` - 「自由に使う」ボタン
- `jButton2` - 「JETを学習する」ボタン
- `jButton3`, `jButton4`, `jButton5` - ナビゲーション・ヘルプボタン
- `jButton7` - ヘルプ表示ボタン

**主要メソッド**:
- `jButton1ActionPerformed()` - freeuseFrame を表示
- `jButton2ActionPerformed()` - learnFrame を表示
- `jButton7ActionPerformed()` - JET コマンドヘルプを表示

**カスタマイズポイント**:
- ボタンのアイコンは `/image/` ディレクトリから取得
- UIは ComponentListener でウィンドウサイズに応じてリサイズ対応
- 背景色: RGB(223, 255, 214)

#### `freeuseFrame`
**ファイル**: `src/jetlearnsystem/freeuseFrame.java`

**機能**: 自由プログラミングモード。ユーザーが自由にJETコードを入力・実行できるエディタ

**主要機能**:
- コードエディタペイン
- TurtleFrame（タートル描画エリア）
- Run, RunAll, Next, Back, Reset ボタン
- ステップ実行対応

**依存クラス**:
- `ProgramExecutionController` - プログラム実行管理
- `TurtleFrame` - タートル描画フレーム

#### `learnFrame`
**ファイル**: `src/jetlearnsystem/learnFrame.java`

**機能**: 学習モード。段階的な問題を解きながらJETを学習

**主要機能**:
- 問題表示エリア
- コードエディタ
- 実行ボタン（Run, RunAll）
- 採点フロー

**依存クラス**:
- `ExerciseController` - 演習全体の制御
- `TurtleFrame` - タートル描画フレーム

#### `DrawingFrame`
**ファイル**: `src/jetlearnsystem/DrawingFrame.java`

**機能**: 採点・結果表示フレーム

**主要機能**:
- ユーザー実行結果の表示
- 模範解答の自動描画（ゴースト表示）
- 自己採点UI（正解/不正解選択）

---

### コントローラークラス

#### `ExerciseController`
**ファイル**: `src/jetlearnsystem/ExerciseController.java`

**機能**: 演習モード全体のロジック管理

**主要属性**:
- `ProgramExecutionController programController` - プログラム実行管理
- `TurtleFrame turtleFrame` - タートルフレーム
- `ProblemLoader problemLoader` - 問題ローダー
- `ProblemDefinition currentProblem` - 現在の問題
- `ProblemSequenceDecider sequenceDecider` - 問題進行ロジック

**主要メソッド**:
- `getStartProblemId()` - 開始問題IDを取得
- `startExercise(String problemId)` - 新しい演習を開始
  - 問題定義を読み込み
  - UIに問題を表示
  - ゴースト描画を実行
- `executeGhostDrawing(String expectedCode)` - 模範解答を自動描画（別スレッド）
- `moveToNextProblem(boolean isCorrect)` - 正解/不正解に応じて次問題へ遷移
- `handleSelfScoreChoice(String action)` - 自己採点結果を処理
- `startScoringFlow(String codeText)` - 採点フロー開始

**インターフェース**: `ExerciseUIUpdater`
- GUI フレームが実装すべきコールバック
  - `displayProblem()` - 問題を表示
  - `displayResult()` - 結果を表示
  - `notifyExecutionStart/End()` - 実行状態通知
  - `showSelfScorePopup()` - 採点ダイアログ表示

**開発者向けヒント**:
- `ExerciseUIUpdater` をカスタム実装することで、新しいUI形式に対応可能
- ゴースト描画は別スレッドで実行される

---

#### `ProgramExecutionController`
**ファイル**: `src/jetlearnsystem/ProgramExecutionController.java`

**機能**: JETプログラム実行エンジン管理（GUI非依存）

**主要属性**:
- `Turtle turtle` - タートルオブジェクト
- `TurtleFrame turtleFrame` - フレーム参照
- `List<JetInstruction> parsedProgram` - パース済みプログラム
- `int currentInstructionIndex` - 現在の命令インデックス
- `Map<String, Object> globalVariables` - グローバル変数
- `List<Integer> historyIndices` - 実行履歴

**主要メソッド**:
- `handleRunAction(String programText)` - プログラムを解析、ステップ実行開始
  - JetLexer でトークン化
  - JetParser で構文解析
  - 命令リスト作成
- `handleNextAction()` - 次の命令を1ステップ実行
- `handleBackAction()` - 1ステップ戻る（プログラムを再実行）
- `handleRunAllAction(String programText)` - プログラム全体を一括実行
- `handleResetAction()` - キャンバスと実行状態をリセット

**スレッド実行**: すべてのアクションが別スレッドで実行され、UI フリーズを防止

**インターフェース**: `ButtonStateUpdater`
- ボタン有効/無効状態を通知

---

### 言語処理クラス

#### `JetLexer`
**ファイル**: `src/jetlearnsystem/JetLexer.java`

**機能**: JET言語の字句解析（トークン化）

**トークンタイプ** (`JetTokenType` enum):
- **移動命令**: FD（前へ）, BK（後ろへ）, RT（右へ）, LT（左へ）
- **その他命令**: CS（初期化）, WAIT（待機）, POS（座標表示）
- **制御構文**: REPEAT（繰り返し）, IF（条件分岐）, FUNC_KEYWORD（関数）
- **データ**: NUM（数値）, CONST（定数）, COLOR_NAME（色名）, NAME（名前）
- **演算子**: PLUS, MINUS, TIMES, DIVIDE, OP（比較演算子）

**主要メソッド**:
- `tokenize(String input) throws JetLexerException` - 入力文字列をトークンリストに変換
  - 全角・半角数字対応
  - 日本語キーワード対応
  - 不正な文字でエラースロー

**内部処理**:
1. 定義済みパターンをLinkedHashMapで保持（順序保証）
2. 正規表現マッチングで逐次トークン化
3. 空白（半角スペース・全角スペース）をスキップ
4. NAMEパターンは最後に試す（キーワード優先）

---

#### `JetParser`
**ファイル**: `src/jetlearnsystem/JetParser.java`

**機能**: JET言語の構文解析（ASTまたは命令リスト生成）

**主要属性**:
- `List<JetToken> tokens` - トークンリスト
- `int currentTokenIndex` - 現在のトークンインデックス
- `static Map<String, FunctionDefinition> functionTable` - グローバル関数定義

**主要メソッド**:
- `parse()` - トークンリストを JetInstruction リストに変換
- `parseInstruction()` - 単一命令を解析
  - switch で各命令タイプを処理
  - FD, BK, RT, LT, CS, REPEAT, IF, FUNC, WAIT, POS など
- `parseCodeBlock()` - `[...]` 内の命令ブロックを解析
- `parseCondition()` - IF条件を解析（BinaryCondition 生成）
- `parseCalcNum()` - 四則演算式を解析
- `parseTerm()` - 乗除演算を解析
- `parseFact()` - 数値、定数、括弧式を解析

**エラーハンドリ**:
- `JetParseException` - 構文エラーをスロー
  - エラーメッセージ + トークン位置を記録

**拡張性**:
- `getFunctionTable()` で グローバル関数定義にアクセス
- 新しい命令を追加する場合: parseInstruction() の switch に case 追加

---

### タートルグラフィックス関連クラス

#### `Turtle`
**ファイル**: `src/jetlearnsystem/Turtle.java`

**機能**: タートルオブジェクト。位置、方向、ペン状態を管理

**主要属性**:
- `double x, y` - 位置座標
- `double direction` - 向き（度数法）
- `Color penColor` - ペン色
- `boolean penDown` - ペン下ろし状態
- `boolean animationEnabled` - アニメーション有効状態
- `boolean ghostMode` - ゴーストモード（背景に軌跡を描画）

**主要メソッド**:
- `forward(int distance)` - 前に進む
- `backward(int distance)` - 後ろに進む
- `turnRight(int angle)` - 右に回転
- `turnLeft(int angle)` - 左に回転
- `resetState()` - 初期状態にリセット
- `setGhostMode(boolean ghostMode)` - ゴーストモード設定
- `setPenColor(Color color)` - ペン色設定
- `drawLine()` - 線を描画

**使用**: TurtleFrame 内で操作される

#### `TurtleFrame`
**ファイル**: `src/jetlearnsystem/TurtleFrame.java`

**機能**: タートルグラフィックスの描画エリア（JPanel）

**主要機能**:
- タートルの軌跡を描画
- ゴースト軌跡を背景に表示
- メッセージ表示（情報、エラー）
- キャンバスクリア
- リペイント管理

**主要メソッド**:
- `getTurtle()` - Turtle オブジェクトを取得
- `clear()` - キャンバスをクリア
- `clearGhostHistory()` - ゴースト履歴をクリア
- `displayInfoMessage(String msg)` - 情報メッセージ表示
- `displayErrorMessage(String msg)` - エラーメッセージ表示
- `repaint()` - 画面再描画

---

### データモデルクラス

#### `ProblemDefinition`
**ファイル**: `src/jetlearnsystem/ProblemDefinition.java`

**機能**: 演習問題の定義

**属性**:
- `String id` - 問題ID
- `String title` - 問題タイトル
- `String description` - 問題説明
- `String modelCode` - 模範解答コード
- `String modelImage` - 模範解答の描画結果（画像パス）

**用途**: 問題ローダーが JSON/XML から読み込む

#### `ExerciseStructure`
**ファイル**: `src/jetlearnsystem/ExerciseStructure.java`

**機能**: 演習全体の構造定義（問題の進行順序）

**属性**:
- `String startId` - 開始問題ID
- 問題の依存関係・正解時の次問題 など

#### `ScoreResult`
**ファイル**: `src/jetlearnsystem/ScoreResult.java`

**機能**: 採点結果

**属性**:
- `boolean isCorrect` - 正解判定
- `String message` - 結果メッセージ
- `long executionTime` - 実行時間（ミリ秒）
- `boolean isTimeout` - タイムアウト判定

---

### ローダークラス

#### `ProblemLoader`
**ファイル**: `src/jetlearnsystem/ProblemLoader.java`

**機能**: JSON/ファイルから問題定義を読み込む

**主要メソッド**:
- `loadProblem(String problemId)` - 指定IDの問題定義を読み込み
s
#### `ExerciseStructureLoader`
**ファイル**: `src/jetlearnsystem/ExerciseStructureLoader.java`

**機能**: 演習構造ファイルを読み込む

**主要メソッド**:
- `loadExerciseStructure()` - 演習構造を読み込み

---

### 判定・シーケンス関連クラス

#### `ProblemSequenceDecider`（インターフェース）
**ファイル**: `src/jetlearnsystem/ProblemSequenceDecider.java`

**機能**: 次の問題IDを決定するインターフェース

**メソッド**:
- `getNextProblemId(String currentId, boolean isCorrect, Map<String, Object> contextData)` 
  - 正解/不正解に応じて次問題IDを返す
  - "END" を返すと演習終了

#### `BasicSequenceDecider`
**ファイル**: `src/jetlearnsystem/BasicSequenceDecider.java`

**機能**: ProblemSequenceDecider の基本実装

**特徴**:
- ExerciseStructure から問題進行を取得
- 固定的な問題順序を定義

---

### 例外クラス

#### `JetLexerException`
**ファイル**: `src/jetlearnsystem/JetLexerException.java`

**用途**: 字句解析エラー
- エラーメッセージ + トークン位置

#### `JetParseException`
**ファイル**: `src/jetlearnsystem/JetParseException.java`

**用途**: 構文解析エラー
- エラーメッセージ + トークン位置

#### `JetExecutionException`
**ファイル**: `src/jetlearnsystem/JetExecutionException.java`

**用途**: 実行時エラー

---

### ユーティリティクラス

#### `linenumber`
**ファイル**: `src/jetlearnsystem/linenumber.java`

**機能**: コードエディタの行番号表示

---
