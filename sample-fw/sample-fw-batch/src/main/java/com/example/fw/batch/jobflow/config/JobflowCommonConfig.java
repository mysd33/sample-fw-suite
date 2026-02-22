package com.example.fw.batch.jobflow.config;

import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.fw.batch.jobflow.converter.SfnJobParametersConverter;

import lombok.RequiredArgsConstructor;

/**
 * 
 * ジョブフローによる起動用の設定クラス
 *
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JobflowConfigurationProperties.class)
public class JobflowCommonConfig {

    /**
     * ジョブフローによる起動用のJobParametersConverterの定義
     */
    @Bean
    JobParametersConverter jobParametersConverter() {
        return new SfnJobParametersConverter();
    }
}
