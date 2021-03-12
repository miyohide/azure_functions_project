# Azure Functions 勉強メモ

## 最初の関数を作成する

以下のチュートリアルをやる。

- [クイックスタート: コマンド ラインから Azure に TypeScript 関数を作成する](https://docs.microsoft.com/ja-jp/azure/azure-functions/create-first-function-cli-typescript?tabs=azure-cli%2Cbrowser)

以下の環境で実行。

- nodejs v14.15.5
- func 3.0.3388

## 全体概要や引数などのオブジェクトについて

以下のドキュメントに纏まっている。

- [Azure Functions の JavaScript 開発者向けガイド](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-reference-node?tabs=v2)

## `function.json`について

`function.json`はトリガーや出力の定義をする。詳細は以下に書かれている。

- [Azure Functions の HTTP トリガー](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-bindings-http-webhook-trigger?tabs=javascript)
- [Azure Functions での HTTP 出力バインド](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-bindings-http-webhook-output)
