package com.example.fw.batch.jobflow.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.fw.batch.jobflow.sfn.repository.SfnRepositoryPackage;
import com.example.fw.batch.jobflow.sfn.repository.SfnTaskResultRepository;
import com.example.fw.batch.jobflow.sfn.service.DefaultSfnTaskResultPersistService;
import com.example.fw.batch.jobflow.sfn.service.SfnTaskResultPersistService;
import com.example.fw.common.systemdate.SystemDate;
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
@MapperScan(basePackageClasses = { SfnRepositoryPackage.class }, annotationClass = Mapper.class)
public class JobflowCommonConfig {

    /**
     * ジョブフローのタスク結果を永続化するサービスのBean定義
     */
    @Bean
    SfnTaskResultPersistService sfnTaskResultPersistService(SfnTaskResultRepository repository, SystemDate systemDate) {
        return new DefaultSfnTaskResultPersistService(repository, systemDate);
    }

}
