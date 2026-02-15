package com.example.fw.batch.jobflow.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.fw.batch.core.config.SpringBatchConfigurationProperties;
import com.example.fw.batch.jobflow.sfn.SfnTaskResultSender;
import com.example.fw.batch.jobflow.sfn.SfnTaskResultSenderStub;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * 
 * ジョブフローによる起動用の設定クラス（開発時）
 *
 */
@Profile("dev")
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JobflowConfigurationProperties.class)
// コマンドライン実行が有効な場合この設定も有効化する
@ConditionalOnProperty(prefix = SpringBatchConfigurationProperties.PROPERTY_PREFIX, name = "type", havingValue = "commandline")
public class JobflowLocalConfig {
    /**
     * 開発時はSfnClientの代わりにSfnTaskResultSenderStubを使用する
     */
    @Bean
    SfnTaskResultSender sfnTaskResultSenderStub(ObjectMapper objectMapper) {
        return new SfnTaskResultSenderStub(objectMapper);
    }
}
