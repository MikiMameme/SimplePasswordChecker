# 簡易パスワード強度チェッカー

[![Java](https://img.shields.io/badge/Java-8%2B-red?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Made with](https://img.shields.io/badge/Made%20with-Claude-orange?logo=anthropic)](https://www.anthropic.com/)
[![Author](https://img.shields.io/badge/Author-MikiMameme-lightgrey)](https://github.com/MikiMameme)

![スクリーンショット](https://github.com/MikiMameme/SimplePasswordChecker/blob/main/Screenshot.png)

## 概要
よく使われるパスワードと、単純なパスワードを「危険なパスワード」と定義し、複雑で長いパスワードほど「安全なパスワード」と表示するGUIアプリケーションです。

**⚠️ 重要な注意事項**
- このソフトウェアは学習用に作成されました
- パスワード情報は保存・送信されません
- 万が一このソフトを使用したことによってパスワードが流出したとしても責任は一切取れません
- 自己責任でご使用ください

## 機能
- ✅ リアルタイムでパスワード強度をチェック
- ✅ 文字数、大文字、小文字、数字、記号の有無を確認
- ✅ よく使われる危険なパスワード30件を辞書でチェック(パスワード辞書は書き換え・追加も可能）
- ✅ 視覚的な強度表示（プログレスバー）
- ✅ わかりやすいフィードバックメッセージ

## チェック項目
1. **文字数**: 12文字以上を推奨
2. **大文字**: A-Z を含む
3. **小文字**: a-z を含む
4. **数字**: 0-9 を含む
5. **記号**: !@#$% などの特殊文字を含む
6. **辞書チェック**: よく使われる危険なパスワードでないか

## 危険なパスワード辞書
`bad_password.txt` には以下のような日本人が設定しがちな危険なパスワードが含まれています：
- 数字の連番（123456, 111111 など）
- よくある単語（password, qwerty など）
- 日本語由来（sakura, pokemon, naruto など）

出典: [日本人のパスワードランキング 2024 最新版](https://www.soliton.co.jp/products/csa/WP-CSA-2410B.pdf)

## 動作環境
- Java 8 以上
- Windows / macOS / Linux
  
## ファイル構成
password-checker_jar      #Jarファイル
PasswordCheckerGUI.java    # メインプログラム
bad_password.txt            # 危険なパスワード辞書
README.md                   # このファイル
screenshot.png              # スクリーンショット


## 学習ポイント
このプロジェクトで学べること：
- Java Swing を使った GUI 開発
- ファイル I/O（テキストファイル読み込み）
- Set（HashSet）を使った高速検索
- DocumentListener によるリアルタイム入力監視
- レイアウトマネージャー（BorderLayout, BoxLayout, GridLayout）
- ストリームAPI（`chars().anyMatch()`）

## 技術スタック
- **言語**: Java
- **GUI**: Swing
- **IDE**: IntelliJ IDEA

## ライセンス
このプロジェクトは学習用です。自由に使用・改変できますが、一切の保証はありません。

## 作者
神酒まめ（MikiMameme）


## 参考資料
- [Java Swing公式ドキュメント](https://docs.oracle.com/javase/tutorial/uiswing/)
- [日本人のパスワードランキング 2024 最新版](https://www.soliton.co.jp/products/csa/WP-CSA-2410B.pdf)
