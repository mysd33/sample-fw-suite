/* StepFunctionsへ送信する処理結果データのテーブル */
CREATE TABLE IF NOT EXISTS sfn_send_task_result (
    job_instance_id BIGINT PRIMARY KEY,
    task_result VARCHAR(262144) NOT NULL,
    created_at TIMESTAMP NOT NULL
);