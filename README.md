# SpringBootの拡張ソフトウェアフレームワークのサンプルプロジェクト

## プロジェクトの説明

## CodeArtifactでの管理

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