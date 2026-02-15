package com.example.fw.batch.jobflow.sfn;

import com.example.fw.common.logging.ApplicationLogger;
import com.example.fw.common.logging.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * SfnTaskResultSenderのスタブ実装クラス<br>
 * タスクの実行結果を送信する代わりに、ログに出力する。
 */
@Slf4j
public class SfnTaskResultSenderStub implements SfnTaskResultSender {
    private static final ApplicationLogger appLogger = LoggerFactory.getApplicationLogger(log);

    @Override
    public void sendTaskSuccess(String taskToken, String output) {
        appLogger.info("SfnTaskResultSenderStub.sendTaskSuccess called. taskToken={}, output={}", taskToken, output);
    }

}
