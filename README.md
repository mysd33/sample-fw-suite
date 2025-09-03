# SpringBootのフレームワーク拡張のサンプルプロジェクト
- SpringBootを使う場合に、よく使用するフレームワーク拡張のサンプル実装である。

## ソフトウェアフレームワーク
* OSSのライブラリが標準で提供される機能を含め、SpringBootをつかったソフトウェアフレームワークの各機能と実現方式は以下の通りである。
* フレームワーク拡張機能のソースコードはcom.example.fwパッケージ配下に格納されている。    

| 分類 | 機能 | 機能概要と実現方式 | 拡張実装 | 拡張実装の格納パッケージ |
| ---- | ---- | ---- | ---- | ---- |
| オンライン | オンラインAP制御 | SpringMVCの機能を利用し、ユーザからの要求受信、ビジネスロジック実行、応答返却まで一連の定型的な処理を実行を制御する。 | - | - |
| | ファイルアップロード | SpringMVCのMultipartFileを利用し、ファイルをアップロードする機能を提供する。 | - | - |
| | ファイルダウンロード | SpringMVCのViewや、Resourceを利用し、ファイルをダウンロードする機能を提供する。 | ○ | com.example.fw.web.view<br/>com.example.fw.web.io |
| | 共通画面レイアウト| Thymeleafの機能を利用し、ヘッダ、フッタ等の全画面共通のHTMLのレイアウトを一元管理する。 | - | - |
| | ページネーション | Thymeleafの機能を利用し、一覧表示する際のページネーションの画面部品を提供する。 | ○ | com.example.fw.web.page |
|  | 認証・認可| Spring Securityを利用し、DBで管理するユーザ情報をもとに認証、認可を行う。 | - | - |
| | セッション管理 | 通常、SpringMVCのセッション管理機能で管理するが、オートスケーリング等の対応のため、APサーバ上で保持していたセッション情報をRedisサーバ（AWSの場合、ElastiCache for Redis）に外部化するためにSpring Session Data Redisを利用する。 | - | - |
| | 集約例外ハンドリング（オンライン） | SpringMVCのControllerAdviceやAOPを利用し、エラー（例外）発生時、エラーログの出力、DBのロールバック、エラー画面やエラー電文の返却といった共通的なエラーハンドリングを実施する。 | ○ | com.example.fw.web.advice、com.example.fw.web.aspect |
| | トランザクション管理 | Spring Frameworkのトランザクション管理機能を利用して、@Transactionalアノテーションによる宣言的トランザクションを実現する機能を提供する。 | - | - |
| | ヘルスチェック | Spring Boot Actuatorを利用して、ヘルスチェックエンドポイントを提供する。その他、Micrometerメトリックの情報提供も行う。 | - | - |
| | グレースフルシャットダウン | SpringBootの機能で、Webサーバ（組み込みTomcat）のグレースフルシャットダウン機能を提供する 。 | - | - |
| | トランサクショントークンチェック | TERASOLUNA Server Frameworkの共通ライブラリの機能を利用して、不正な画面遷移を防止するトランザクションチェック機能を提供する 。 | - | com.example.fw.web.token |
| | Open APIドキュメント生成 | Springdoc-openapiの機能で、RestController等の実装、アノテーション情報からOpen APIドキュメントをjson、yaml、html（Swagger-UI）形式を提供する 。 | - | - |
| バッチ | バッチAP実行制御 | Spring BootおよびSpringBatchによりコマンドラインで指定された対象のジョブを起動する機能を提供する。また、SpringBatchに関する実行制御機能を提供する。 | ○ | com.example.fw.batch.core |
| | 非同期AP実行制御 | Spring JMSとAmazon SQS Java Messaging Libraryを利用しSQSの標準キューを介した非同期実行依頼のメッセージを受信し、SpringBatchにより対象のジョブを起動する機能を提供する。 | ○ | com.example.fw.batch.async |
| | 大量データアクセス | SpringBatchのItemReader、ItemWriterを利用し、大容量のファイルやDBのレコードを逐次読み書きする機能を提供する。 | - | - |
| | 集約例外ハンドリング（バッチ） | エラー（例外）発生時、SpringBatchの機能によりDBのロールバックするとともに、JobExecutionListenerを利用しエラーログの出力といった共通的なエラーハンドリングを実施する。 | ○ | com.example.fw.batch.exeption<br/>com.example.fw.batch.listener |
| | トランザクション管理 | Spring Frameworkのトランザクション管理機能を利用して、タスクレットやチャンクに対するトランザクション管理を実現する機能を提供する。 | - | - |
| | SQS Local起動 | 開発端末での動作確認のため、AP起動時にSQSのローカル実行が可能なFake ElasticMQを起動する機能を提供する。 | ○ | com.example.fw.common.async |
| オン・バッチ共通 | RDBアクセス | MyBatisやSpringとの統合機能を利用し、DBコネクション取得、SQLの実行等のRDBへのアクセスのため定型的な処理を実施し、ORマッピングやSQLマッピングと呼ばれるドメイン層とインフラ層のインピーダンスミスマッチを吸収する機能を提供する。<br/>また@TransactionalがreadOnlyかによって、Auroraのクラスタエンドポイントとリーダーエンドポイントを動的に切り替えて接続する。 | ○ | com.example.fw.common.datasource |
| | DynamoDBアクセス | AWS SDK for Java 2.xのDynamoDB拡張クライアント（DynamoDbEnhancedClient）を使って、DBへのアクセス機能を提供する。 | ○ | com.example.fw.common.dynamodb |
| | DynamoDBトランザクション管理機能 | DynamoDBのトランザクション管理機能（TransactWriteItems）を利用する操作に対して、AOP機能を利用して、@DynamoDBTransactionalアノテーションによる宣言的トランザクションを実現する機能を提供する。 | ○ | com.example.fw.common.dynamodb |
| | オブジェクトストレージ（S3）アクセス | AWS SDK for Java 2.xのS3クライアント（S3Client）を使って、S3のアクセス機能を提供する。開発時にS3アクセスできない場合を考慮して通常のファイルシステムへのFakeに切り替える。 | ○ | com.example.fw.common.objectstorage |
| | HTTPクライアント | WebClientやRestTemplateを利用してREST APIの呼び出しやサーバエラー時の例外の取り扱いを制御する。 | ○ | com.example.fw.common.httpclient |
| | リトライ・サーキットブレーカ | Spring Cloud Circuit Breaker（Resillience4j）を利用し、REST APIの呼び出しでの一時的な障害に対する遮断やフォールバック処理等を制御する。また、WebClientのリトライ機能でエクスポネンシャルバックオフによりリトライを実現する。なお、AWSリソースのAPI呼び出しは、AWS SDKにてエクスポネンシャルバックオフによりリトライ処理を提供。 | - | - |
| | 非同期実行依頼 | Spring JMS、Amazon SQS Java Messaging Libraryを利用し、SQSの標準キューを介した非同期実行依頼のメッセージを送信する。 | ○ | com.example.fw.common.async |
| | スケジュールバッチ起動 | application.ymlに定義した独自のスケジュールバッチ定義を読み込みバッチAPへ非同期実行依頼を実施するSpringBootのCLIアプリケーションを提供する。 | ○ | com.example.fw.common.schedule |
| | PDF帳票出力 | Jasper Reportsを利用してPDF帳票作成する。<br/>Jasper Reportsを使った帳票の詳しい説明は[こちら](https://github.com/mysd33/sample-jasperreports-springboot) | ○ | com.example.fw.common.reports |
| | PDF署名 | PDFに署名を付与する機能を提供する。署名の種類として、PKCS#12のキーストアをもとに、OpenPDFを使った基本的な署名、DSS(Digital Signature Service)を使ったPAdES署名を作成する。なおPAdESについてはAWS KMSで管理する鍵を使った署名付与にも対応している。詳しい説明は[こちら](https://github.com/mysd33/sample-jasperreports-springboot) | ○ | com.example.fw.common.digitalsignature |
| | 署名鍵管理 | PDF署名機能で使用する署名鍵を、AWS KMSに作成し管理する機能を提供する。KMSで作成したキーペアに対して公開鍵の取得、電子署名の付与、CSRやテスト用自己署名証明書作成に対応している。詳しい説明は[こちら](https://github.com/mysd33/sample-jasperreports-springboot) | ○ | com.example.fw.common.keymanagment |
| | システム日時取得 | システム日時を取得するAPIを提供する。テスト時を考慮し、固定のシステム日時を外部から設定できる。 | ○ | com.example.fw.common.systemdate |
| | 入力チェック| Java BeanValidationとSpringのValidation機能を利用し、単項目チェックや相関項目チェックといった画面の入力項目に対する形式的なチェックを実施する。 | ○ | com.example.fw.common.validation<br/>com.example.fw.web.validation |
| | メッセージ管理 | MessageResourceで画面やログに出力するメッセージを管理する。 | ○ | com.example.fw.common.message |
| | 例外 | RuntimeExceptionを継承し、エラーコード（メッセージID）やメッセージを管理可能な共通的なビジネス例外、システム例外を提供する。 | ○ | com.example.fw.common.exception |
| | ロギング | Slf4jとLogback、SpringBootのLogback拡張、ver3.4からのStructured Logs機能を利用し、プロファイルによって動作環境に応じたログレベルや出力先（ファイルや標準出力）、出力形式（タブ区切りやJSON）に切替可能とする。またメッセージIDをもとにログ出力可能な汎用的なAPIを提供する。<br/>また、logback-accessを利用しTomcatのアクセスログを出力可能とする。 | ○ | com.example.fw.common.logging<br/>com.example.fw.web.servlet |
| | 分散トレーシング（ログ） | Micrometer Tracingを利用して、トレースIDやスパンIDをAP間でのREST API呼び出しで引継ぎ、ログにも記録することを実現する。 | - | - |
| | 分散トレーシング（X-Ray） | X-Rayによるサービス間の分散トレーシング・可視化を実現する。2パターンの実装を提供している。<br>(1) AWS X-Ray SDKを利用する<br>(2)ADOT(AWS Distro for Open Telemetry)でAP側は未実装で自動計測する | ○ | com.example.fw.web.aspect<br>com.example.fw.servlet<br>com.example.fw.common.async<br>com.example.fw.common.dynamodb<br>com.example.fw.common.httpclient<br>com.example.fw.common.objectstorage <br> ※(1)の場合|
| | メトリクス転送（CloudWatch） | Spring Cloud for AWSの機能により、JVM等、Spring Boot Actuatorが提供するメトリクスをCloudWatchメトリクスへ転送する。カスタムメトリクスとしてMyBatisのSQLの実行状況に対応する。 | - | - |
| | プロパティ管理 | SpringBootのプロパティ管理を使用して、APから環境依存のパラメータを切り出し、プロファイルによって動作環境に応じたパラメータ値に置き換え可能とする。 | - | - |
| | プロパティ管理（SSM、Secrets Manager） | Spring Cloud for AWSの機能により、AWSのSSMパラメータストアやSecrets Managerに切り出したAPから環境依存のパラメータを、プロファイルによって動作環境に応じたパラメータ値に置き換え可能とする。 | - | - |
| | オブジェクトマッピング | MapStructを利用し、類似のプロパティを持つリソースオブジェクトやDTOとドメインオブジェクト間で、値のコピーやデータ変換処理を簡単にかつ高速に行えるようにする。 | - | - |
| | DI | Springを利用し、DI（依存性の注入）機能を提供する。 | - | - |
| | AOP | SpringとAspectJAOPを利用し、AOP機能を提供する。 | - | - |
| | ボイラープレートコード排除 | Lombokを利用し、オブジェクトのコンストラクタやGetter/Setter等のソースコードを自動生成し、ボイラープレートコードを排除する。 | - | - |
| | DynamoDB Local起動 | 開発端末での動作確認のため、AP起動時にDynamoDBのローカル実行が可能なFake DynamoDB Localを起動する機能を提供する。 | ○ | com.example.fw.common.dynamodb |
| | S3 Local起動 | 開発端末での動作確認のため、APをローカル起動可能とするようファイルシステムアクセスに差し替えたFakeやS3互換のFakeのサーバ（MinIO、s3ver）に接続する機能を提供する。 | ○ | com.example.fw.common.objectstorage |

## プロジェクトの説明
- オンライン処理、バッチ処理用のソフトウェアフレームワークを提供している。
- sample-fwフォルダ配下が、Mavenの階層型のマルチモジュールプロジェクトになっている。

|     フォルダ        |            説明             |
|--------------------|-----------------------------|
|  sample-fw         |   Mavenの親プロジェクト      |
|  sample-fw-common  |   オンライン・バッチ処理共通のフレームワーク機能のプロジェクト      |
|  sample-fw-web     |   オンライン処理のフレームワーク機能のプロジェクト      |
|  sample-fw-batch   |   バッチ処理のフレームワーク機能のプロジェクト      |

- sample-fw-parentフォルダは、フレームワークを使用するAPのpom.xmlでしているparentプロジェクトになっている。

|     フォルダ        |             説明            |
|--------------------|-----------------------------|
|  app-parent  |  フレームワークを使用するAP向けのMavenの親プロジェクト   |

## CodeArtifactでの管理
- ソフトウェアフレームワークのjarをCodeArtifactをMavenリポジトリとして使用して管理し、アプリケーションのpomで、依存関係を設定することが想定される
- cfnフォルダに、CodePipeline、CodeBuildでCI実行し、Mavenビルド、CodeArtifactへMavenデプロイするためのCloudFormationのサンプルを提供する。

### 1. IAMの作成
```sh
cd cfn

aws cloudformation validate-template --template-body file://cfn-iam.yaml
aws cloudformation create-stack --stack-name Demo-IAM-Stack --template-body file://cfn-iam.yaml --capabilities CAPABILITY_NAMED_IAM
```

### 2. CodeArtifactの作成
```sh
aws cloudformation validate-template --template-body file://cfn-codeartifact.yaml
aws cloudformation create-stack --stack-name Demo-CodeArtifact-Stack --template-body file://cfn-codeartifact.yaml
```

### 3. CodeBuildの作成
```sh
aws cloudformation validate-template --template-body file://cfn-codebuild.yaml
aws cloudformation create-stack --stack-name FW-CodeBuild-Stack --template-body file://cfn-codebuild.yaml
```

* 取得したMavenリポジトリをS3にキャッシュする。キャッシュ用のS3のパス（バケット名/プレフィックス）を変えるには、それぞれのcfnスタック作成時のコマンドでパラメータを指定する
    * 「--parameters ParameterKey=CacheS3Location,ParameterValue=(パス名)」

### 4. CodePipelineの作成
```sh
aws cloudformation validate-template --template-body file://cfn-codepipeline.yaml
aws cloudformation create-stack --stack-name FW-CodePipeline-Stack --template-body file://cfn-codepipeline.yaml
```
* Artifact用のS3バケット名を変えるには、それぞれのcfnスタック作成時のコマンドでパラメータを指定する
    * 「--parameters ParameterKey=ArtifactS3BucketName,ParameterValue=(バケット名)」

### 5. CodePipelineでのCI実行
* CodePipeline構築後、自動でCI実行され、ビルド、CodeArtifactへのjarデプロイが実施される。
* 以降、CodeCommitのmainブランチへソースコードが変更されるとCIが自動実行れる

## （参考）マルチモジュールプロジェクトの作り方
- 参考
    - https://www.baeldung.com/maven-multi-module

- 親プロジェクト作成
```sh
mvn archetype:generate -DgroupId=com.example -DartifactId=sample-fw
```

- 親プロジェクトのpackagingをpomとして追加

```xml
<packaging>pom</packaging>
```

- 子プロジェクト作成

```sh
cd sample-fw
mvn archetype:generate -DgroupId=com.example -DartifactId=sample-fw-common
mvn archetype:generate -DgroupId=com.example -DartifactId=sample-fw-web
mvn archetype:generate -DgroupId=com.example -DartifactId=sample-fw-batch
```