package com.example.fw.batch.jobflow.sfn;

import com.example.fw.batch.message.BatchFrameworkMessageIds;
import com.example.fw.common.exception.SystemException;
import com.example.fw.common.logging.ApplicationLogger;
import com.example.fw.common.logging.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SfnTaskResultSenderのスタブ実装クラス<br>
 * タスクの実行結果を送信する代わりに、ログに出力する。
 */
@Slf4j
@RequiredArgsConstructor
public class SfnTaskResultSenderStub implements SfnTaskResultSender {
    private static final ApplicationLogger appLogger = LoggerFactory.getApplicationLogger(log);
    private final ObjectMapper objectMapper;

    @Override
    public void sendTaskSuccess(String taskToken, Object output) {
        String outputJson;
        try {
            outputJson = objectMapper.writeValueAsString(output);
        } catch (JsonProcessingException e) {
            throw new SystemException(e, BatchFrameworkMessageIds.E_FW_JBFLW_9001, output.toString());
        }
        appLogger.info(BatchFrameworkMessageIds.I_FW_JBFLW_0001, taskToken, outputJson);
        // StepFunctionsには送信せず、ログ出力のみ
    }

}
