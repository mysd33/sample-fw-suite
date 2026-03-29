package com.example.fw.batch.jobflow.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.fw.batch.jobflow.converter.SfnJobParametersConverter;
import com.example.fw.batch.jobflow.sfn.repository.SfnRepositoryPackage;
import com.example.fw.batch.jobflow.sfn.service.SfnServicePackage;
import com.example.fw.common.systemdate.config.SystemDateConfig;

import lombok.RequiredArgsConstructor;

/**
 * 
 * ジョブフローによる起動用の設定クラス
 *
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JobflowConfigurationProperties.class)
@Import(SystemDateConfig.class)
@ComponentScan(basePackageClasses = SfnServicePackage.class)
@MapperScan(basePackageClasses = { SfnRepositoryPackage.class }, annotationClass = Mapper.class)
public class JobflowCommonConfig {

    /**
     * ジョブフローによる起動用のJobParametersConverterの定義
     */
    @Bean
    JobParametersConverter jobParametersConverter() {
        return new SfnJobParametersConverter();
    }
}
