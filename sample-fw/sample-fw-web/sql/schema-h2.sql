/* トランザクショントークン */
CREATE TABLE IF NOT EXISTS transaction_token (
    token_name varchar(256) not null,
    token_key varchar(32) not null,
    token_value varchar(32) not null,
    session_id  varchar(256) not null,
    sequence bigint,
    constraint pk_transaction_token primary key (token_name, token_key, session_id)
);

CREATE INDEX IF NOT EXISTS transaction_token_index_delete_older on transaction_token(token_name, session_id);
CREATE INDEX IF NOT EXISTS transaction_token_index_delete_older_sequence on transaction_token(sequence);
CREATE INDEX IF NOT EXISTS transaction_token_index_clean on transaction_token(session_id);

CREATE SEQUENCE IF NOT EXISTS transaction_token_sequence;

/* Spring Security OAuth 2.0 Client(JdbcOAuth2AuthorizedClientService）用テーブル */
CREATE TABLE oauth2_authorized_client
(
    client_registration_id  varchar(100)                            NOT NULL,
    principal_name          varchar(200)                            NOT NULL,
    access_token_type       varchar(100)                            NOT NULL,
    access_token_value      bytea                                   NOT NULL,
    access_token_issued_at  timestamp                               NOT NULL,
    access_token_expires_at timestamp                               NOT NULL,
    access_token_scopes     varchar(1000) DEFAULT NULL,
    refresh_token_value     bytea         DEFAULT NULL,
    refresh_token_issued_at timestamp     DEFAULT NULL,
    created_at              timestamp     DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (client_registration_id, principal_name)
);
