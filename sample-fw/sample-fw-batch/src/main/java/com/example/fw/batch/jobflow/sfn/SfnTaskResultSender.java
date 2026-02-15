package com.example.fw.batch.jobflow.sfn;

/**
 * StepFunctionsのタスクの実行結果を送信するためのインターフェース<br>
 * StepFunctionsでのジョブ間の結果受け渡しに使用する。
 */
public interface SfnTaskResultSender {
    /**
     * タスクの実行成功を送信する
     * 
     * @param taskToken タスクトークン
     * @param output    タスクの実行結果
     */
    void sendTaskSuccess(String taskToken, Object output);
}
