/* トランザクショントークン */
CREATE TABLE IF NOT EXISTS transaction_token (
    token_name VARCHAR(256) NOT NULL,
    token_key VARCHAR(32) NOT NULL,
    token_value VARCHAR(32) NOT NULL,
    session_id  VARCHAR(256) NOT NULL,
    sequence BIGINT,
    CONSTRAINT pk_transaction_token PRIMARY KEY(token_name, token_key, session_id)
    );

CREATE INDEX IF NOT EXISTS transaction_token_index_delete_older ON transaction_token(token_name, session_id);
CREATE INDEX IF NOT EXISTS transaction_token_index_delete_older_sequence ON transaction_token(sequence);
CREATE INDEX IF NOT EXISTS transaction_token_index_clean ON transaction_token(session_id);

CREATE SEQUENCE IF NOT EXISTS transaction_token_sequence;
