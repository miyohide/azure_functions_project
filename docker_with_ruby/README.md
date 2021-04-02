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

# アプリの実装

アプリは[Sinatra](http://sinatrarb.com/)を使って実装します。リクエストがあったらテキストを返すだけの簡単なものでしたらRailsよりも[Sinatra](http://sinatrarb.com/)のほうが楽でしょう。実装もこれだけです。

```ruby
require 'sinatra'

set :bind, '0.0.0.0'
set :port, ENV['FUNCTIONS_CUSTOMHANDLER_PORT']

get '/api/Hello' do
  'Hello Ruby World'
end
```

`:bind`に`0.0.0.0`を指定してどこからでもアクセスを受け付け、`:port`でリクエストを受け付けるポートを環境変数`FUNCTIONS_CUSTOMHANDLER_PORT`で指定する必要があるのが注意点といえば注意点かと思います。

# Gemfileの準備

`Gemfile`にて利用するライブラリを記述します。単純なSinatraの昨日しか使わないので、以下の記述になります。

```ruby
source "https://rubygems.org"

gem 'sinatra', '~> 2.1.0'
```

# Dockerfileの準備

ドキュメントによればAzure Functionsの環境として作成するにはベースイメージとして`mcr.microsoft.com/azure-functions/dotnet:3.0-appservice`を使う必要があるとのこと。

https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-create-function-linux-custom-image?tabs=bash%2Cportal&pivots=programming-language-other#build-the-container-image-and-test-locally

イメージの実装はこのリポジトリのようです。

https://github.com/Azure/azure-functions-docker

Rubyを動かすためにはいろいろな方法があるかと思いますが、手っ取り早くまずは`apt-get install ruby`でインストールしてみます。ここでインストールされるRubyは2.5系だったので、`gem i bunder`も実行しています。

```dockerfile
FROM mcr.microsoft.com/azure-functions/dotnet:3.0-appservice
ENV AzureWebJobsScriptRoot=/home/site/wwwroot \
    AzureFunctionsJobHost__Logging__Console__IsEnabled=true

RUN apt update && \
    apt install -y ruby

WORKDIR /home/site/wwwroot

RUN gem install bundler --no-doc

COPY Gemfile /home/site/wwwroot/
RUN bundler install
COPY . /home/site/wwwroot/
```

# 実行

後は、`docker build`でDockerイメージを作成し、`docker run`で動かした後に`http://localhost:8080/api/Hello`にアクセスすると、`Hello Ruby World`という文字列がブラウザ上に表示されます。

コンソールログには以下のような文字列が出力されていました。Sinatraが出力しているログが`fail`になっているのがちょっと気になりますが、とりあえず動いているようです。

```
      Host lock lease acquired by instance ID '000000000000000000000000714A9D2E'.
info: Function.Hello[1]
      Executing 'Functions.Hello' (Reason='This function was programmatically called via the host APIs.', Id=66a1a41b-2d0c-4277-90d3-a5ef3990be81)
fail: Host.Function.Console[0]
      127.0.0.1 - - [01/Apr/2021:11:40:05 +0000] "GET /api/Hello HTTP/1.1" 200 16 0.0004
fail: Host.Function.Console[0]
      127.0.0.1 - - [01/Apr/2021:11:40:05 UTC] "GET /api/Hello HTTP/1.1" 200 16
fail: Host.Function.Console[0]
      - -> /api/Hello
info: Function.Hello[2]
      Executed 'Functions.Hello' (Succeeded, Id=66a1a41b-2d0c-4277-90d3-a5ef3990be81, Duration=66ms)
```
