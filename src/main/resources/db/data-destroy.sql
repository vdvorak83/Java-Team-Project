-- Script to destroy database data in case in if it's needed to start over.

DROP TABLE IF EXISTS money_history;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS transaction_states;
DROP TABLE IF EXISTS goal_followers;
DROP TABLE IF EXISTS checkpoints;
DROP TABLE IF EXISTS history;
DROP TABLE IF EXISTS goals;
DROP TABLE IF EXISTS users_followings;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS storage;
DROP TABLE IF EXISTS wallets;
DROP TABLE IF EXISTS goal_states;
DROP TABLE IF EXISTS user_states;
DROP TABLE IF EXISTS user_roles;

DROP SEQUENCE IF EXISTS checkpoints_id_seq;
DROP SEQUENCE IF EXISTS money_history_id_seq;
DROP SEQUENCE IF EXISTS money_history_id_seq;
DROP SEQUENCE IF EXISTS transactions_id_seq;
DROP SEQUENCE IF EXISTS transaction_states_id_seq;
DROP SEQUENCE IF EXISTS goal_states_id_seq;
DROP SEQUENCE IF EXISTS wallets_id_seq;
DROP SEQUENCE IF EXISTS users_followings_id_seq;
DROP SEQUENCE IF EXISTS storage_id_seq;
DROP SEQUENCE IF EXISTS user_states_id_seq;
DROP SEQUENCE IF EXISTS goal_states_id_seq;
DROP SEQUENCE IF EXISTS user_states_id_seq;
DROP SEQUENCE IF EXISTS user_roles_id_seq;