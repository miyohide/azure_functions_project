# Azure Functions 勉強メモ

## 最初の関数を作成する

以下のチュートリアルをやる。

- [クイックスタート: コマンド ラインから Azure に TypeScript 関数を作成する](https://docs.microsoft.com/ja-jp/azure/azure-functions/create-first-function-cli-typescript?tabs=azure-cli%2Cbrowser)

以下の環境で実行。

- nodejs v14.15.5
- func 3.0.3388

## 開発環境について

`npm start`で開発環境を起動。今回はHTTPトリガーで作っているので、ブラウザでアクセスすると動作が確認できる。

ファイルを修正したら、自動的にリロードされる。
## 全体概要や引数などのオブジェクトについて

以下のドキュメントに纏まっている。

- [Azure Functions の JavaScript 開発者向けガイド](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-reference-node?tabs=v2)

## `function.json`について

`function.json`はトリガーや出力の定義をする。詳細は以下に書かれている。

- [Azure Functions の HTTP トリガー](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-bindings-http-webhook-trigger?tabs=javascript)
- [Azure Functions での HTTP 出力バインド](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-bindings-http-webhook-output)

# Azureにデプロイする

## ログイン

`az login`コマンドを実行する。ブラウザが開いてログインが求められるので、ログインしたらOK。

## リソースグループを作成

`az group create`でリソースグループを作成する。

```
az group create --name リソースグループ名 --location 場所
```

場所は`az account list-locations`コマンドで得られる`Name`の値を指定する...と思っていたんだけれども、なんか使えないものもリストアップされる。

```
az account list-locations -o table
DisplayName               Name                 RegionalDisplayName
------------------------  -------------------  -------------------------------------
East US                   eastus               (US) East US
East US 2                 eastus2              (US) East US 2
South Central US          southcentralus       (US) South Central US
West US 2                 westus2              (US) West US 2
Australia East            australiaeast        (Asia Pacific) Australia East
Southeast Asia            southeastasia        (Asia Pacific) Southeast Asia
North Europe              northeurope          (Europe) North Europe
UK South                  uksouth              (Europe) UK South
West Europe               westeurope           (Europe) West Europe
Central US                centralus            (US) Central US
North Central US          northcentralus       (US) North Central US
West US                   westus               (US) West US
South Africa North        southafricanorth     (Africa) South Africa North
Central India             centralindia         (Asia Pacific) Central India
East Asia                 eastasia             (Asia Pacific) East Asia
Japan East                japaneast            (Asia Pacific) Japan East
Korea Central             koreacentral         (Asia Pacific) Korea Central
Canada Central            canadacentral        (Canada) Canada Central
France Central            francecentral        (Europe) France Central
Germany West Central      germanywestcentral   (Europe) Germany West Central
Norway East               norwayeast           (Europe) Norway East
Switzerland North         switzerlandnorth     (Europe) Switzerland North
UAE North                 uaenorth             (Middle East) UAE North
Brazil South              brazilsouth          (South America) Brazil South
Central US (Stage)        centralusstage       (US) Central US (Stage)
East US (Stage)           eastusstage          (US) East US (Stage)
East US 2 (Stage)         eastus2stage         (US) East US 2 (Stage)
North Central US (Stage)  northcentralusstage  (US) North Central US (Stage)
South Central US (Stage)  southcentralusstage  (US) South Central US (Stage)
West US (Stage)           westusstage          (US) West US (Stage)
West US 2 (Stage)         westus2stage         (US) West US 2 (Stage)
Asia                      asia                 Asia
Asia Pacific              asiapacific          Asia Pacific
Australia                 australia            Australia
Brazil                    brazil               Brazil
Canada                    canada               Canada
Europe                    europe               Europe
Global                    global               Global
India                     india                India
Japan                     japan                Japan
United Kingdom            uk                   United Kingdom
United States             unitedstates         United States
East Asia (Stage)         eastasiastage        (Asia Pacific) East Asia (Stage)
Southeast Asia (Stage)    southeastasiastage   (Asia Pacific) Southeast Asia (Stage)
Central US EUAP           centraluseuap        (US) Central US EUAP
East US 2 EUAP            eastus2euap          (US) East US 2 EUAP
West Central US           westcentralus        (US) West Central US
West US 3                 westus3              (US) West US 3
South Africa West         southafricawest      (Africa) South Africa West
Australia Central         australiacentral     (Asia Pacific) Australia Central
Australia Central 2       australiacentral2    (Asia Pacific) Australia Central 2
Australia Southeast       australiasoutheast   (Asia Pacific) Australia Southeast
Japan West                japanwest            (Asia Pacific) Japan West
Korea South               koreasouth           (Asia Pacific) Korea South
South India               southindia           (Asia Pacific) South India
West India                westindia            (Asia Pacific) West India
Canada East               canadaeast           (Canada) Canada East
France South              francesouth          (Europe) France South
Germany North             germanynorth         (Europe) Germany North
Norway West               norwaywest           (Europe) Norway West
Switzerland West          switzerlandwest      (Europe) Switzerland West
UK West                   ukwest               (Europe) UK West
UAE Central               uaecentral           (Middle East) UAE Central
Brazil Southeast          brazilsoutheast      (South America) Brazil Southeast
```

例えば`japan`ってものを指定すると、`not available`って出てしまう。

```
$ az group create --name miyohide1 --location japan
The provided location 'japan' is not available for resource group. List of available regions is 'centralus,eastasia,southeastasia,eastus,eastus2,westus,westus2,northcentralus,southcentralus,westcentralus,northeurope,westeurope,japaneast,japanwest,brazilsouth,australiasoutheast,australiaeast,westindia,southindia,centralindia,canadacentral,canadaeast,uksouth,ukwest,koreacentral,koreasouth,francecentral,southafricanorth,uaenorth,australiacentral,switzerlandnorth,germanywestcentral,norwayeast,australiacentral2'.
```

日本リージョンでは`japaneast`とか`japanwest`とかを指定すれば良いかなと思います。

## ストレージアカウントの作成

Azure Functionsの実行プログラムを格納する場所を作成する。

```
az storage account create --name ストレージアカウント名 --location 場所 --resource-group リソースグループ名
```

## Azure Functionsを作成する

[az functionapp create](https://docs.microsoft.com/ja-jp/cli/azure/functionapp?view=azure-cli-latest#az_functionapp_create)コマンドを使ってAzure Functionsアプリを作成する。細かいオプションはリンク先を参照。

```
az functionapp create --resource-group リソースグループ名 --consumption-plan-location japaneast --runtime node --runtime-version 14 --functions-version 3 --name AzureFunctionsアプリ名 --storage-account ストレージアカウント名
```

上記のコマンドで、以下の環境が構築される。

- 実行環境はNode.js 14.x系
- OSはWindows
- 従量課金プラン
- 作成と同時にApplication Insightsが作成される

## ビルド

本番環境用に手元にあるソースをビルドする。

```
npm run build:production
```

## アプリをAzureに送信する

Azure Functions Core Toolsのコマンドである[func azuzre functionapp publish](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-run-local?tabs=macos%2Ccsharp%2Cbash#publish-to-azure)を実行して、ローカルにあるコードをAzureに送信します。

```
func azure functionapp publish AzureFunctionsアプリ名
```

こんな結果が表示されます。

```
Getting site publishing info...
Creating archive for current directory...
Uploading 10.8 KB [###########################################################]
Upload completed successfully.
Deployment completed successfully.
Syncing triggers...
Functions in miyohidefuncapp1:
    HttpExample - [httpTrigger]
        Invoke url: アクセス先URL
```

`Invoke url`に`?code=`からはじまる長い文字列がついているのは、[関数のアクセスキー](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-bindings-http-webhook-trigger?tabs=javascript#authorization-keys)という機能がついているため。ローカルではこの値にかかわらずアクセスできるが、Azureに送信したアプリを動かすにはこの`code`の値が一致していないと動かない（401エラーが出る）。

# ログを見る

Azure Functionsを作成したらそれに紐づいてApplication Insightsも作られているので、いちいち無効化してなければログは見られる。

クエリのところで何を入れれば良いかがよく分からなかったんだけれども、`traces`にアプリで`context.log`の内容が見れた。

出力される項目がたくさんあるので、見たい項目を絞りたい場合は`project`で見たい項目だけを指定すれば良い。

```
traces
| project timestamp, message, operation_id
```

ここでは日時とメッセージのほか`operation_id`というのを指定。これが同じものは同じリクエストでの処理っぽい。

`requests`の`duration`でその関数の実行時間が分かるみたい。今回はNode.jsで動かしたんだけれども、20分ぐらい処理がないと、次の処理に時間がかかる感じ。今回の例では、250ms以内で納まっていたものが、一気に4.6秒に跳ね上がった。おそらくこの内容に該当している。

[Understanding serverless cold start](https://azure.microsoft.com/ja-jp/blog/understanding-serverless-cold-start/)

今回はNode.jsだったんだけれども、Javaならどれぐらいかかるのか、Linuxならどうなのかを実験したい。

# テストを書く

Azure Functionsにおけるテストの書き方については、[Azure Functions のコードをテストするための戦略](https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-test-a-function)に記述がある。

上記のドキュメントは、JavaScriptの例でTypeScriptはまた違う気がしたので、他の資料を探す。

[TypeScript Deep Dive日本語版のJest](https://typescript-jp.gitbook.io/deep-dive/intro-1/jest)の記事を参考にする。
