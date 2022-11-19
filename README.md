# SpringBootのフレームワーク拡張のサンプルプロジェクト
- SpringBootを使う場合に、よく使用するフレームワーク拡張のサンプル実装である。

## ソフトウェアフレームワーク
* OSSのライブラリが標準で提供される機能を含め、SpringBootをつかったソフトウェアフレームワークの各機能と実現方式は以下の通りである。
* フレームワーク拡張機能のソースコードはcom.example.fwパッケージ配下に格納されている。    

| 分類 | 機能 | 機能概要と実現方式 | 拡張実装 | 拡張実装の格納パッケージ |
| ---- | ---- | ---- | ---- | ---- |
| オンライン | オンラインAP制御 | SpringMVCの機能を利用し、ユーザからの要求受信、ビジネスロジック実行、応答返却まで一連の定型的な処理を実行を制御する。 | - | - |
| | ファイルダウンロード | SpringMVCの機能を利用し、CSVファイルをダウンロードする機能を提供する。 | ○ | com.example.fw.web.view |
| | 共通画面レイアウト| Thymeleafの機能を利用し、ヘッダ、フッタ等の全画面共通のHTMLのレイアウトを一元管理する。 | - | - |
| | ページネーション | Thymeleafの機能を利用し、一覧表示する際のページネーションの画面部品を提供する。 | ○ | com.example.fw.web.page |
| | 入力チェック| Java BeanValidationとSpringのValidation機能を利用し、単項目チェックや相関項目チェックといった画面の入力項目に対する形式的なチェックを実施する。 | ○ | com.example.fw.common.validation |
|  | 認証・認可| Spring Securityを利用し、DBで管理するユーザ情報をもとに認証、認可を行う。 | - | - |
| | セッション管理 | 通常、SpringMVCのセッション管理機能で管理するが、オートスケーリング等の対応のため、APサーバ上で保持していたセッション情報をRedisサーバ（AWSの場合、ElastiCache for Redis）に外部化するためにSpring Session Data Redisを利用する。 | - | - |
| | 集約例外ハンドリング | SpringMVCのControllerAdviceやAOPを利用し、エラー（例外）発生時、エラーログの出力、DBのロールバック、エラー画面やエラー電文の返却といった共通的なエラーハンドリングを実施する。 | ○ | com.example.fw.web.advice、com.example.fw.web.aspect |
| | トランザクション管理機能 | Spring Frameworkのトランザクション管理機能を利用して、@Transactionalアノテーションによる宣言的トランザクションを実現する機能を提供する。 | - | - |
| | 分散トレーシング | Spring Cloud Sleathを利用して、トレースIDやスパンIDをAP間でのREST API呼び出しで引継ぎログに記録することで、分散トレーシングを実現する。 | - | - |
| | ヘルスチェック | Spring Boot Actuatorを利用して、ヘルスチェックエンドポイントを提供する。その他、Micrometerメトリックの情報提供も行う。 | - | - |
| | グレースフルシャットダウン | SpringBootの機能で、Webサーバ（組み込みTomcat）のグレースフルシャットダウン機能を提供する 。 | - | - |
| バッチ | TBD |  |  |  |
| オン・バッチ共通 | RDBアクセス | MyBatisやSpringとの統合機能を利用し、DBコネクション取得、SQLの実行等のRDBへのアクセスのため定型的な処理を実施し、ORマッピングやSQLマッピングと呼ばれるドメイン層とインフラ層のインピーダンスミスマッチを吸収する機能を提供する。 | - | - |
| | HTTPクライアント | WebClientやRestTemplateを利用してREST APIの呼び出しやサーバエラー時の例外の取り扱いを制御する。 | ○ | com.example.fw.common.httpclient |
| | リトライ・サーキットブレーカ | Spring Cloud Circuit Breaker（Resillience4j）を利用し、REST APIの呼び出しでの一時的な障害に対するリトライやフォールバック処理等を制御する。なお、AWSリソースのAPI呼び出しは、AWS SDKにてエクスポネンシャルバックオフによりリトライ処理を提供。 | - | - |
| | 非同期実行依頼 | Spring JMS、AWS SQS Java Messaging Libraryを利用し、SQSの標準キューを介した非同期実行依頼のメッセージを送信する。 | ○ | com.example.fw.common.async |
| | メッセージ管理 | MessageResourceで画面やログに出力するメッセージを管理する。 | ○ | com.example.fw.common.message |
| | 例外 | RuntimeExceptionを継承し、エラーコード（メッセージID）やメッセージを管理可能な共通的なビジネス例外、システム例外を提供する。 | ○ | com.example.fw.common.exception |
| | ロギング | Slf4jとLogback、SpringBootのLogback拡張の機能を利用し、プロファイルによって動作環境に応じたログレベルや出力先（ファイルや標準出力）、出力形式（タブ区切りやJSON）に切替可能とする。またメッセージIDをもとにログ出力可能な汎用的なAPIを提供する。 | ○ | com.example.fw.common.logging |
| | プロパティ管理 | SpringBootのプロパティ管理を使用して、APから環境依存のパラメータを切り出し、プロファイルによって動作環境に応じたパラメータ値に置き換え可能とする。 | - | - |
| | オブジェクトマッピング | MapStructを利用し、類似のプロパティを持つリソースオブジェクトやDTOとドメインオブジェクト間で、値のコピーやデータ変換処理を簡単にかつ高速に行えるようにする。 | - | - |
| | DI | Springを利用し、DI（依存性の注入）機能を提供する。 | - | - |
| | AOP | SpringとAspectJAOPを利用し、AOP機能を提供する。 | - | - |
| | ボイラープレートコード排除 | Lombokを利用し、オブジェクトのコンストラクタやGetter/Setter等のソースコードを自動生成し、ボイラープレートコードを排除する。 | - | - |

## プロジェクトの説明
- オンライン処理、バッチ処理用のソフトウェアフレームワークを提供している。
- sample-fw-parentフォルダ配下が、Mavenの階層型のマルチモジュールプロジェクトになっている。

|     フォルダ        |              説明               |
|--------------------|-----------------------------|
|  sample-fw-parent  |   Mavenの親プロジェクト      |
|  sample-fw-common  |   オンライン・バッチ処理共通のフレームワーク機能のプロジェクト      |
|  sample-fw-web     |   オンライン処理のフレームワーク機能のプロジェクト      |
|  sample-fw-batch   |   バッチ処理のフレームワーク機能のプロジェクト      |

## CodeArtifactでの管理
- ソフトウェアフレームワークのjarをCodeArtifactをMavenリポジトリとして使用して管理し、アプリケーションのpomで、依存関係を設定することが想定される
- cfnフォルダに、CodeBuildでCI実行し、Mavenビルド、CodeArtifactへMavenデプロイするためのCloudFormationのサンプルを提供する。

### IAMの作成
```sh
cd cfn

aws cloudformation validate-template --template-body file://cfn-iam.yaml
aws cloudformation create-stack --stack-name Demo-IAM-Stack --template-body file://cfn-iam.yaml --capabilities CAPABILITY_IAM
```

### CodeArtifactの作成
```sh
aws cloudformation validate-template --template-body file://cfn-codeartifact.yaml
aws cloudformation create-stack --stack-name Demo-CodeArtifact-Stack --template-body file://cfn-codeartifact.yaml
```

### CodeBuildの作成
```sh
aws cloudformation validate-template --template-body file://cfn-codebuild.yaml
aws cloudformation create-stack --stack-name FW-CodeBuild-Stack --template-body file://cfn-codebuild.yaml
```
* Artifact用のS3バケット名を変えるには、それぞれのcfnスタック作成時のコマンドでパラメータを指定する
    * 「--parameters ParameterKey=ArtifactS3BucketName,ParameterValue=(バケット名)」

* 取得したMavenリポジトリをS3にキャッシュする。キャッシュ用のS3のパス（バケット名/プレフィックス）を変えるには、それぞれのcfnスタック作成時のコマンドでパラメータを指定する
    * 「--parameters ParameterKey=CacheS3Location,ParameterValue=(パス名)」

### CodeBuildでのCI実行
* CodeBuildプロジェクトが作成されるので、それぞれビルド実行し、CodeArtifactへjarをデプロイ

## （参考）マルチモジュールプロジェクトの作り方
- 参考
    - https://www.baeldung.com/maven-multi-module

- 親プロジェクト作成
```sh
mvn archetype:generate -DgroupId=com.example -DartifactId=sample-fw-parent
```

- 親プロジェクトのpackagingをpomとして追加

```xml
<packaging>pom</packaging>
```

- 子プロジェクト作成

```sh
cd sample-fw-parent
mvn archetype:generate -DgroupId=com.example -DartifactId=sample-fw-common
mvn archetype:generate -DgroupId=com.example -DartifactId=sample-fw-web
mvn archetype:generate -DgroupId=com.example -DartifactId=sample-fw-batch
```