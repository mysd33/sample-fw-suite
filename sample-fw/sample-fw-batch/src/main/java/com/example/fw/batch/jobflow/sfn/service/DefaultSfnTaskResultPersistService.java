package com.example.fw.batch.jobflow.sfn.service;

import org.springframework.batch.core.scope.context.JobContext;
import org.springframework.batch.core.scope.context.JobSynchronizationManager;
import org.springframework.stereotype.Service;

import com.example.fw.batch.jobflow.sfn.model.SfnTaskResult;
import com.example.fw.batch.jobflow.sfn.repository.SfnTaskResultRepository;
import com.example.fw.common.systemdate.SystemDate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultSfnTaskResultPersistService implements SfnTaskResultPersistService {
    private final SfnTaskResultRepository repository;
    private final SystemDate systemDate;

    @Override
    public void createTaskResult(String taskResult) {
        // 現在のジョブコンテキストからジョブインスタンスIDを取得する
        JobContext jobContext = JobSynchronizationManager.getContext();
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
