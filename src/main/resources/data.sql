-- Роли пользователей: модератор, обычный юзер etc.
create table if not exists user_roles
(
	id serial not null
		constraint roles_pkey
			primary key,
	name varchar(15)
)
;

-- Статус пользователя: (не)подтверждён, забанен.
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
	address varchar(100) not null
)
;

-- Пользователи.
create table if not exists users
(
	id serial not null
		constraint users_pkey
			primary key,
	login varchar(50) not null,
	password varchar(60) not null,
	email varchar(254),
	role smallint not null
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
			references wallets
)
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
	date timestamp
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

-- Цели.
create table if not exists goals
(
	id bigserial not null
		constraint goals_pkey
			primary key,
	user_id bigserial not null
		constraint goals_users_id_fk
			references users,
	name varchar(64) not null,
	description varchar(1500),
	money numeric(15,10),
	state smallint not null
		constraint goals_goal_states_id_fk
			references goal_states
				on update cascade,
	date_end timestamp
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
	direction boolean not null,
	amount numeric(15,10) not null,
	date timestamp
)
;
-- CREATE TABLE persistent_logins (
-- 	username varchar(64) not null,
-- 	series varchar(64) not null,
-- 	token varchar(64) not null,
-- 	last_used timestamp not null,
-- 	PRIMARY KEY (series)
-- );
