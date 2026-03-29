package com.example.fw.batch.jobflow.sfn.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * Step Functionsのタスク実行結果を表すクラス
 */
@Data
@Builder
public class SfnTaskResult {
    // ジョブインスタンスID
    private long jobInstanceId;
    // Step Functionsに送信するタスク実行結果（JSON形式の文字列）
    private String taskResult;
    // 作成日時
    private LocalDateTime createdAt;
}
