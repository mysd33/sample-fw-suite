package com.example.fw.batch.jobflow.sfn.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.fw.batch.jobflow.sfn.model.SfnTaskResult;

/**
 * Step Functionsのタスク実行結果を管理するリポジトリインターフェース
 */
@Mapper
public interface SfnTaskResultRepository {
    /**
     * タスク実行結果を保存する<br>
     * 既に同じジョブインスタンスIDのレコードが存在する場合は上書き保存する
     * 
     * @param taskResult タスク実行結果
     */
    void upsert(SfnTaskResult taskResult);

    /**
     * ジョブインスタンスIDに基づいてタスク実行結果を取得する
     * 
     * @param jobInstanceId ジョブインスタンスID
     * @return タスク実行結果
     */
    SfnTaskResult findById(long jobInstanceId);

}
