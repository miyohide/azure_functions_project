# JavaでのAzure Functions

Azure FunctionsでのアプリをJavaで実装してみます。Mavenでは`mvn archetype:generate`でプロジェクトが生成できるので大変楽そうなのですが、あまりXMLファイルを書きたくなかったので、自分はGradleを使うようにしました。

https://docs.microsoft.com/ja-jp/azure/azure-functions/functions-create-first-java-gradle

過去にも似たようなことをやりましたが、Spring Cloud Functionのバージョンが上がっていたので改めてやり直し。

https://miyohide.hatenablog.com/entry/2020/07/26/174813

ここでは上には記されていないことを中心に記述します。

# Spring Cloud Function

将来的にRDBMSとの接続をすることを考えて、[Spring Cloud Function](https://spring.io/projects/spring-cloud-function)というフレームワークを使うことにしました。Spring Cloud Functionを使った記事はAzureのドキュメントにもあります。

https://docs.microsoft.com/ja-jp/azure/developer/java/spring-framework/getting-started-with-spring-cloud-function-in-azure

上記にあるサンプルでもいいのですが、私はSpring Cloud Functionのサンプルを参考にしました。

https://docs.spring.io/spring-cloud-function/docs/3.1.2/reference/html/azure.html#_microsoft_azure

これを書いている2021年3月21日ではSpring Cloud Functionは3.1.2で、前バージョンとはいくつか仕様変更が発生しています。

具体的には、3.1.1では`AzureSpringBootRequestHandler`を継承する必要がありましたが、3.1.2では`FunctionInvoker`を継承する必要があるようです（正確には、`AzureSpringBootRequestHandler`は非推奨化）。詳細はそれぞれのドキュメントを見ると良いかと思います。

3.1.1のもの
https://docs.spring.io/spring-cloud-function/docs/3.1.1/reference/html/index.html

3.1.2のもの
https://docs.spring.io/spring-cloud-function/docs/3.1.2/reference/html/index.html

# build.gradle

`build.gradle`にてAzure Functionsにデプロイする情報を記述します。記述する内容の詳細については以下のwikiをみると良いでしょう。

https://github.com/microsoft/azure-gradle-plugins/wiki/Configuration

今回はJava 11環境で動かしたかったのですが、`runtime`に何も指定しないとJava 8の環境になるようです。以下のように`runtime`に`javaVersion`を設定するとJava 11で生成されました。

```
azurefunctions {
    resourceGroup = 'sample_resource_group'
    appName = "sample_app_name"
    pricingTier = "Consumption"
    region = "japaneast"
    runtime {
        os = "windows"
        javaVersion = 11
    }
}
```

これでAzureにデプロイすると動くはずだったのですが、以下のログが出力され500エラーが発生しました。

```
2021-03-21T06:59:56.612 [Error] Executed 'Functions.hello' (Failed, Id=exxxxxxxxxx, Duration=638ms)Result: FailureException: IllegalArgumentException: Failed to locate main classStack: java.lang.IllegalStateException: Failed to discover main class. An attempt was made to discover main class as 'MAIN_CLASS' environment variable, system property as well as entry in META-INF/MANIFEST.MF (in that order).at org.springframework.cloud.function.utils.FunctionClassUtils.getStartClass(FunctionClassUtils.java:83)
```

この問題を解決するには`build.gradle`に以下の設定を追加します。この設定はマニフェストファイルにメインクラスのクラス名を指定して、実行可能なjarファイルとして生成することを意味しています。

```
jar {
    manifest {
        attributes 'Main-Class' : 'com.github.miyohide.Funcapp'
    }
}
```

# FunctionとかSupplierとかConsumerとか

Spring Cloud Functionを使おうとするとちょっと面食らうのが`Function`。
これは、Java 8から導入されたJava関数型インターフェースというもの。

http://www.ne.jp/asahi/hishidama/home/tech/java/functionalinterface.html

ちょっと書き方が慣れないけれども、徐々に慣れていくしかないかなと。

# テスト

テストを書くこともやってみました。`build.gradle`でのライブラリ指定をとりあえず以下のようにしました。

```groovy
dependencies {
    implementation 'org.springframework.cloud:spring-cloud-function-adapter-azure:3.1.2'
    testImplementation 'org.springframework.cloud:spring-cloud-starter-function-web:3.1.2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test:2.4.3'
}
```

`spring-boot-starter-test`のバージョンを指定するのがちょっといまいちなのでなんとかしたいところ...

JUnit 5では`build.gradle`にて以下の記述がないとうまくテストが認識されません。

```groovy
test {
    useJUnitPlatform()
}
```

参考
https://junit.org/junit5/docs/current/user-guide/#running-tests-build-gradle

テストの書き方ついては以下を参照。

http://www.ne.jp/asahi/hishidama/home/tech/java/junit/5/index.html
