package com.example.fw.common.dynamodb.config;

import com.example.fw.common.constants.FrameworkConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/// DynamoDBのプロパティクラス
@Data
@ConfigurationProperties(prefix = DynamoDBConfigurationProperties.PROPERTY_PREFIX)
public class DynamoDBConfigurationProperties {

    // DynamoDBの設定を保持するプロパティのプレフィックス
    static final String PROPERTY_PREFIX = FrameworkConstants.PROPERTY_BASE_NAME + "dynamodb";
    // リージョン（デフォルト: ap-northeast-1）
    private String region = "ap-northeast-1";
    // HTTPコネクションプールの最大接続数（AWS SDKのデフォルト値50）
    // https://github.com/aws/aws-sdk-java-v2/blob/master/http-client-spi/src/main/java/software/amazon/awssdk/http/SdkHttpConfigurationOption.java#L151
    private int maxConnections = 50;
    // HTTPコネクション確立時のタイムアウト（ミリ秒。AWS SDKのデフォルト値2秒 = 2000ミリ秒）
    // https://github.com/aws/aws-sdk-java-v2/blob/master/http-client-spi/src/main/java/software/amazon/awssdk/http/SdkHttpConfigurationOption.java#L142
    private int connectionTimeout = 2000;

    // ローカルDynamoDBの設定
    private DynamoDBLocalProperties dynamodblocal;

    @Data
    public static class DynamoDBLocalProperties {

        private int port = 8000;
    }
}
