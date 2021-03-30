# Azure Functions上のアプリをRubyで作る

Azure Functionsでサポートしている言語は2021年3月現在、以下のものに限られています。

- C#
- JavaScript
- F#
- Java
- PowerShell
- Python
- TypeScript

詳細は以下のドキュメントに書かれています。

https://docs.microsoft.com/ja-jp/azure/azure-functions/supported-languages

自分が得意なRubyが入っていない...。ただ、Dockerを使えば動かせるというようなことが以下のドキュメントに書かれています（ドキュメントはR言語の例）。

https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-create-function-linux-custom-image?tabs=bash%2Cportal&pivots=programming-language-other

ここでは、Rubyで動くように実装してみることにします。

# プロジェクトの作成と設定

とは言っても、ほとんど上のドキュメントに書かれていることを実行するだけです。

`func init`コマンドでプロジェクトを作成します。

```
func init プロジェクト名 --worker-runtime custom --docker
```

これでプロジェクト名のディレクトリが作成されるので、そのディレクトリに移動後、`func new`で関数の設定ファイルである`function.json`を作成します。

```
cd プロジェクト名
func new --name Hello --template "HTTP trigger"
```

これで`Hello`ディレクトリに`function.json`が生成されます。

`function.json`を開き、`"authLevel"`の値を`"anonymous"`に変えておきます。これはAPIアクセスの時にAPIキーを求めるかどうかの設定です。詳細は以下のドッキュメントにある`authLevel`の項を参照してください。

https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-bindings-http-webhook-trigger?tabs=csharp#configuration

最後に`host.json`の設定をします。`"customHandler"`の部分を以下のように修正します。

```json
  "customHandler": {
    "description": {
      "defaultExecutablePath": "ruby",
      "workingDirectory": "",
      "arguments": ["app.rb"]
    },
    "enableForwardingHttpRequest": true
  }
```

私は`"enableForwardingHttpRequest"`の記述をすっかり見落としており、うまく動かなくてかなり悩みました。この項目の意味は以下のドキュメントを参照すると良いと思います。

https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-host-json#customhandler
