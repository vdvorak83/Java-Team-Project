-- Script to destroy database data in case in if it's needed to start over.

DROP TABLE IF EXISTS money_history;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS goals;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS wallets;
DROP TABLE IF EXISTS goal_states;
DROP TABLE IF EXISTS user_states;
DROP TABLE IF EXISTS user_roles;

DROP SEQUENCE IF EXISTS money_history_id_seq;
DROP SEQUENCE IF EXISTS money_history_id_seq;
DROP SEQUENCE IF EXISTS transactions_id_seq;
DROP SEQUENCE IF EXISTS goal_states_id_seq;
DROP SEQUENCE IF EXISTS wallets_id_seq;
DROP SEQUENCE IF EXISTS user_states_id_seq;
DROP SEQUENCE IF EXISTS goal_states_id_seq;
DROP SEQUENCE IF EXISTS user_states_id_seq;
DROP SEQUENCE IF EXISTS user_roles_id_seq;