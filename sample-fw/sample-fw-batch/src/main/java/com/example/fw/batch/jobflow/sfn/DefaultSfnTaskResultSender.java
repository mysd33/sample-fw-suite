package com.example.fw.batch.jobflow.sfn;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sfn.SfnClient;

/**
 * StepFunctionsのタスクの実行結果を送信するためのデフォルト実装
 */
@RequiredArgsConstructor
public class DefaultSfnTaskResultSender implements SfnTaskResultSender {
    private final SfnClient sfnClient;

    @Override
    public void sendTaskSuccess(String taskToken, String output) {
        sfnClient.sendTaskSuccess(builder -> builder.taskToken(taskToken).output(output));
    }

}
