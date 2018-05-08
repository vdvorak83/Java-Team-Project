-- User roles: Admin, Moderator, Normal user etc.
create table if not exists user_roles
(
	id serial not null
		constraint roles_pkey
			primary key,
	name varchar(15)
)
;

-- User status: (un)confirmed, banned etc.
create table if not exists user_states
(
	id smallserial not null
		constraint states_pkey
			primary key,
	name varchar(12)
)
;

-- Здесь хранятся адреса кошельков, и тех на которые надо кидать деньги,
-- и те, на которые происходят вывод средств. Всё это для того, чтобы не дублировать адреса.
create table if not exists wallets
(
	id bigserial not null
		constraint wallets_pkey
			primary key,
	address varchar(100) not null,
	balance numeric(15,10) default 0 not null,
	our boolean default false not null
)
;

-- Files.
create table if not exists storage
(
	id bigserial not null
		constraint storage_pkey
			primary key,
	file_directory varchar(255),
	file_extension varchar(255),
	file_name varchar(255)
)
;

-- Users.
create table if not exists users
(
	id serial not null
		constraint users_pkey
		primary key,
	login varchar(50) not null,
	password varchar(60) not null,
	email varchar(254),
	role_id smallint not null
		constraint users_roles_id_fk
		references user_roles
		on update cascade,
	state smallint not null
		constraint users_states_id_fk
		references user_states
		on update cascade,
	money numeric(15,10) default 0,
	reg_date timestamp not null,
	wallet bigint
		constraint users_wallets_id_fk
		references wallets,
	image_id bigint
		constraint image_storage_id_fk
		references storage,
	state_id integer,
	temp_password varchar(255),
	uuid varchar(255)
		constraint uk_6km2m9i3vjuy36rnvkgj1l61s
		unique
)
;

create table if not exists transaction_states
(
	id smallserial not null
		constraint transaction_states_pkey
		primary key,
	name varchar(15)
)
;

create unique index if not exists transaction_states_id_uindex
	on transaction_states (id)
;


-- История ввода и вывода денег.
create table if not exists transactions
(
	id bigserial not null
		constraint transactions_pkey
			primary key,
	user_id bigint not null
		constraint transactions_users_id_fk
			references users,
	amount numeric(15,10) not null,
	from_wallet bigint not null
		constraint transactions_wallets_id_fk
			references wallets,
	to_wallet bigint not null
		constraint transactions_wallets_id_fk_2
			references wallets,
	date timestamp,
	state smallint
		constraint transactions_transaction_states_id_fk
			references transaction_states
				on update cascade,
	fee numeric(15,10),
	txid varchar(64)
)
;

-- Статус цели: в процессе достижения, достигнута, не достигнута, сохранена в черновиках.
create table if not exists goal_states
(
	id smallserial not null
		constraint goal_states_pkey
			primary key,
	name varchar(15) not null
)
;

-- Goals.
create table if not exists goals
(
	id bigserial not null
		constraint goals_pkey
		primary key,
	user_id bigserial not null
		constraint fkb1mp6ulyqkpcw6bc1a2mr7v1g
		references users
		constraint goals_users_id_fk
		references users,
	name varchar(64) not null,
	description varchar(1500),
	money numeric(15,10),
	state smallint not null
		constraint goals_goal_states_id_fk
		references goal_states
		on update cascade,
	date_end timestamp,
	date_start date,
	price numeric(19,2),
	title varchar(255),
	image bigint
		constraint goals_storage_id_fk
		references storage
)
;

-- Список транзакций с внутренним счётом пользователя. Сюда входят история отпарвки
-- денег на цель, возврат денег после выполнения цели и отправка денег
-- другим пользователям (если будет реализовано).
-- direction = true - деньги получены после выполнения цели
-- direction = false - деньги отправлены на цель
create table if not exists money_history
(
	id bigserial not null
		constraint money_history_pkey
		primary key,
	user_id bigint not null
		constraint money_history_users_id_fk
		references users
		on update cascade,
	goal bigint not null
		constraint money_history_goals_id_fk
		references goals
		on update cascade,
	direction smallint not null,
	amount numeric(15,10) not null,
	date timestamp
)
;

create table if not exists checkpoints
(
	id bigserial not null
		constraint checkpoints_pkey
		primary key,
	amount integer,
	completed integer,
	description varchar(255),
	name varchar(255),
	goal_id bigint
		constraint checkpoint_goal_id_fk
		references goals
)
;

create table if not exists goal_followers
(
	id bigserial not null
		constraint goal_followers_pkey
		primary key
)
;

create table if not exists users_followings
(
	id bigserial not null
		constraint users_followings_pkey
		primary key,
	following_id integer
		constraint follower_id_fk
		references users,
	user_id integer
		constraint user_id_fk
		references users
)
;

create table if not exists persistent_logins (
	username varchar(64) not null,
	series varchar(64) not null,
	token varchar(64) not null,
	last_used timestamp not null,
	PRIMARY KEY (series)
);

INSERT INTO user_roles (id, name)
  SELECT 0, 'Empty'
  WHERE NOT EXISTS (SELECT name FROM user_roles WHERE name = 'Empty');

INSERT INTO user_roles (id, name)
  SELECT 1, 'Admin'
  WHERE NOT EXISTS (SELECT name FROM user_roles WHERE name = 'Admin');

INSERT INTO user_roles (id, name)
  SELECT 2, 'Moderator'
  WHERE NOT EXISTS (SELECT name FROM user_roles WHERE name = 'Moderator');

INSERT INTO user_roles (id, name)
  SELECT 3, 'User'
  WHERE NOT EXISTS (SELECT name FROM user_roles WHERE name = 'User');

INSERT INTO user_states (id, name)
  SELECT 0, 'Active'
  WHERE NOT EXISTS (SELECT name FROM user_states WHERE name = 'Active');

INSERT INTO user_states (id, name)
  SELECT 1, 'Banned'
  WHERE NOT EXISTS (SELECT name FROM user_states WHERE name = 'Banned');

INSERT INTO user_states (id, name)
  SELECT 2, 'Not active'
  WHERE NOT EXISTS (SELECT name FROM user_states WHERE name = 'Not active');

INSERT INTO goal_states (id, name)
  SELECT 0, 'In progress'
  WHERE NOT EXISTS (SELECT name FROM goal_states WHERE name = 'In progress');

INSERT INTO goal_states (id, name)
  SELECT 1, 'Completed'
  WHERE NOT EXISTS (SELECT name FROM goal_states WHERE name = 'Completed');

INSERT INTO goal_states (id, name)
  SELECT 2, 'Failed'
  WHERE NOT EXISTS (SELECT name FROM goal_states WHERE name = 'Failed');

INSERT INTO goal_states (id, name)
  SELECT 3, 'Draft'
  WHERE NOT EXISTS (SELECT name FROM goal_states WHERE name = 'Draft');

INSERT INTO transaction_states (id, name)
  SELECT 0, 'Sent'
  WHERE NOT EXISTS (SELECT name FROM transaction_states WHERE name = 'Sent');

INSERT INTO transaction_states (id, name)
  SELECT 1, 'Received'
  WHERE NOT EXISTS (SELECT name FROM transaction_states WHERE name = 'Sent');

INSERT INTO transaction_states (id, name)
  SELECT 2, 'Send failed'
  WHERE NOT EXISTS (SELECT name FROM transaction_states WHERE name = 'Sent');

INSERT INTO transaction_states (id, name)
  SELECT 3, 'Receive failed'
  WHERE NOT EXISTS (SELECT name FROM transaction_states WHERE name = 'Sent');