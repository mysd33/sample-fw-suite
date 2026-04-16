package com.example.fw.batch.jobflow.sfn.service;

import org.springframework.batch.core.scope.context.JobContext;
import org.springframework.batch.core.scope.context.JobSynchronizationManager;

import com.example.fw.batch.jobflow.sfn.model.SfnTaskResult;
import com.example.fw.batch.jobflow.sfn.repository.SfnTaskResultRepository;
import com.example.fw.common.systemdate.SystemDate;

import lombok.RequiredArgsConstructor;

/**
 * Step Functionsのタスク実行結果を永続化するサービスのデフォルト実装
 */
@RequiredArgsConstructor
public class DefaultSfnTaskResultPersistService implements SfnTaskResultPersistService {
    private final SfnTaskResultRepository repository;
    private final SystemDate systemDate;

    @Override
    public void createTaskResult(String taskResult) {
        // 現在のジョブコンテキストからジョブインスタンスIDを取得する
        JobContext jobContext = JobSynchronizationManager.getContext();
        if (jobContext == null) {
            throw new IllegalStateException("JobContextを取得できません");
        }
        long jobInstanceId = jobContext.getJobExecution().getJobInstanceId();
        SfnTaskResult result = SfnTaskResult.builder()//
                .jobInstanceId(jobInstanceId)//
                .taskResult(taskResult)//
                .createdAt(systemDate.now()).build();

        repository.insert(result);
    }

    @Override
    public String findTaskResultById(long jobInstanceId) {
        SfnTaskResult result = repository.findById(jobInstanceId);
        return result != null ? result.getTaskResult() : null;
    }
}
