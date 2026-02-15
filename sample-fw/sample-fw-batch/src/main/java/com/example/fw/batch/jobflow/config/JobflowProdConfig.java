package com.example.fw.batch.jobflow.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.fw.batch.core.config.SpringBatchConfigurationProperties;
import com.example.fw.batch.jobflow.sfn.DefaultSfnTaskResultSender;
import com.example.fw.batch.jobflow.sfn.SfnTaskResultSender;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;

/**
 * 
 * ジョブフローによる起動用の設定クラス（本番用）
 *
 */
@Profile("!dev")
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JobflowConfigurationProperties.class)
// コマンドライン実行が有効な場合この設定も有効化する
@ConditionalOnProperty(prefix = SpringBatchConfigurationProperties.PROPERTY_PREFIX, name = "type", havingValue = "commandline")
public class JobflowProdConfig {
    private final JobflowConfigurationProperties jobflowConfigurationProperties;

    /**
     * StepFunctionsクライアント
     */
    @Bean
    SfnClient sfnClient() {
        Region region = Region.of(jobflowConfigurationProperties.getRegion());
        return SfnClient.builder().httpClientBuilder((ApacheHttpClient.builder()))//
                .region(region)//
                .build();
    }

    /**
     * StepFunctionsでのジョブ間の結果受け渡し用のクラス
     */
    @Bean
    SfnTaskResultSender sfnTaskResultSender(ObjectMapper objectMapper) {
        return new DefaultSfnTaskResultSender(objectMapper, sfnClient());
    }

}
