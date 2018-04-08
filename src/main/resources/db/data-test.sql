-- Some values to play with.

INSERT INTO user_roles (id, name)
  SELECT 0, 'User'
  WHERE NOT EXISTS (SELECT name FROM user_roles WHERE name = 'User');

INSERT INTO user_roles (id, name)
  SELECT 1, 'Moderator'
  WHERE NOT EXISTS (SELECT name FROM user_roles WHERE name = 'Moderator');

INSERT INTO user_roles (id, name)
  SELECT 2, 'Admin'
  WHERE NOT EXISTS (SELECT name FROM user_roles WHERE name = 'Admin');

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

INSERT INTO wallets (address)
  SELECT '36d6uhs6kVgpr9MPfJnL4ysLrPrKUAkHWF'
  WHERE NOT EXISTS (SELECT address FROM wallets WHERE address = '36d6uhs6kVgpr9MPfJnL4ysLrPrKUAkHWF');

INSERT INTO wallets (address)
  SELECT '36nzcr4BTF9ffaS7TQRA1EshRk3xq8HTVE'
  WHERE NOT EXISTS (SELECT address FROM wallets WHERE address = '36nzcr4BTF9ffaS7TQRA1EshRk3xq8HTVE');

INSERT INTO wallets (address)
  SELECT '1255GrigvW5WQxNA8o5FENaVSRnk8iB9a8'
  WHERE NOT EXISTS (SELECT address FROM wallets WHERE address = '1255GrigvW5WQxNA8o5FENaVSRnk8iB9a8');

INSERT INTO wallets (address)
  SELECT '36d7G44fNUWuRP45C81x2McgXSoKmPY1uQ'
  WHERE NOT EXISTS (SELECT address FROM wallets WHERE address = '36d7G44fNUWuRP45C81x2McgXSoKmPY1uQ');

INSERT INTO wallets (address)
  SELECT '1Krzq7nzTRcby8wT1W5A21afvoFahmFdFd'
  WHERE NOT EXISTS (SELECT address FROM wallets WHERE address = '1Krzq7nzTRcby8wT1W5A21afvoFahmFdFd');

INSERT INTO wallets (address)
  SELECT '171hSukgYfkAGVmMGiPueMjgfwpu5Z1yat'
  WHERE NOT EXISTS (SELECT address FROM wallets WHERE address = '171hSukgYfkAGVmMGiPueMjgfwpu5Z1yat');

-- PASSWORD: 'admeenimum', ROLE=ADMIN
INSERT INTO users (login, password, role, state, money, reg_date)
  SELECT 'admin', '$2a$10$GXk29DhBb8F62YM.GddDN.xcaEydhm5esfi2KLX3V4Bjke142mJ0S', 2, 0, 0, '2018-02-01 00:00:00.000000'
  WHERE NOT EXISTS (SELECT login FROM users WHERE login = 'admin');

-- PASSWORD: 'power-Hungry', ROLE=MODERATOR
INSERT INTO users (login, password, role, state, money, reg_date)
  SELECT 'firmoder', '$2a$10$hok/YqFOfPajhRN.dXLp9ut7CHQoOmRytF45kGnerQ2WSogUNZsZ2', 1, 0, 0, '2018-02-10 00:00:00.000000'
  WHERE NOT EXISTS (SELECT login FROM users WHERE login = 'firmoder');

-- PASSWORD: 'power-Corrupts', ROLE=MODERATOR
INSERT INTO users (login, password, role, state, money, reg_date)
  SELECT 'secmoder', '$2a$10$JoeaHKJNiXcjpA/oEWTcquRsRdIG2HwCCgm2j98/xf5vW22MoNIg2', 1, 0, 0, '2018-02-11 00:00:00.000000'
  WHERE NOT EXISTS (SELECT login FROM users WHERE login = 'secmoder');

-- PASSWORD: 'goodtime2sleep', ROLE=USER
INSERT INTO users (login, password, role, state, money, reg_date, wallet)
  SELECT 'procrastinator', '$2a$10$B4pxJwMO1XUvhZ6iQgFc3OU7WUP1l7bbGyJchj4RnEU2xV09r0CZC', 0, 0, 0.15, '2018-02-15 17:19:53.000000', 2
  WHERE NOT EXISTS (SELECT login FROM users WHERE login = 'procrastinator');

-- PASSWORD: 'sellUrself', ROLE=USER
INSERT INTO users (login, password, role, state, money, reg_date, wallet)
  SELECT 'richguy', '$2a$10$0St43ssRMmCDqXKC6EMXFeohOrf.nGDznuLRZi9ndNhwW7y3jEDai', 0, 0, 99, '2018-02-16 12:27:40.000000', 1
  WHERE NOT EXISTS (SELECT login FROM users WHERE login = 'richguy');

-- PASSWORD: 'MakeaGain', ROLE=USER
INSERT INTO users (login, password, role, state, money, reg_date, wallet)
  SELECT 'useagle', '$2a$10$rvd/NG4Y1l4nmbaY0iYTwOGz0DY8yhprVl7lyeEr2nLqX6hI44cge', 0, 0, 1.73, '2018-02-19 09:31:29.000000', 3
  WHERE NOT EXISTS (SELECT login FROM users WHERE login = 'useagle');

INSERT INTO goals (user_id, name, description, money, state, date_end)
  SELECT 4, 'Find a job', 'Find a job as a Java programmer', 0.5, 0, '2018-08-18 16:28:23.102000'
  WHERE NOT EXISTS (SELECT id FROM goals WHERE id = 1);

INSERT INTO goals (user_id, name, description, money, state, date_end)
  SELECT 4, 'Train', 'Be able to do at least 15 pull-ups', 0.75, 0, '2018-11-29 11:36:41.880000'
  WHERE NOT EXISTS (SELECT id FROM goals WHERE id = 2);

INSERT INTO goals (user_id, name, description, money, state, date_end)
  SELECT 5, 'Earn $1000000', 'Cause I am not stupid', 5, 1, '2018-03-03 11:35:06.806000'
  WHERE NOT EXISTS (SELECT id FROM goals WHERE id = 3);

INSERT INTO goals (user_id, name, description, money, state, date_end)
  SELECT 5, 'Start a YouTube channel', 'Start a channel about traveling to expensive places', 6, 2, '2018-01-11 11:39:42.425000'
  WHERE NOT EXISTS (SELECT id FROM goals WHERE id = 4);

INSERT INTO goals (user_id, name, description, money, state, date_end)
  SELECT 6, 'Learn to drive a car', 'Yeah', 3, 1, '2018-02-26 22:40:14.351000'
  WHERE NOT EXISTS (SELECT id FROM goals WHERE id = 5);

INSERT INTO goals (user_id, name, description, money, state, date_end)
  SELECT 6, 'Learn to draw', 'Very useful skill', 3, 2, '2018-03-12 11:29:32.415000'
  WHERE NOT EXISTS (SELECT id FROM goals WHERE id = 6);

INSERT INTO transactions (user_id, amount, from_wallet, to_wallet, date)
  SELECT 4, 1.42, 4, 2, '2018-01-01 12:13:46.840000'
  WHERE NOT EXISTS (SELECT id FROM transactions WHERE id = 1);

INSERT INTO transactions (user_id, amount, from_wallet, to_wallet, date)
  SELECT 5, 115, 5, 1, '2018-01-02 17:26:10.620000'
  WHERE NOT EXISTS (SELECT id FROM transactions WHERE id = 2);

INSERT INTO transactions (user_id, amount, from_wallet, to_wallet, date)
  SELECT 5, 9.8, 1, 5, '2018-03-13 18:23:17.500000'
  WHERE NOT EXISTS (SELECT id FROM transactions WHERE id = 3);

INSERT INTO transactions (user_id, amount, from_wallet, to_wallet, date)
  SELECT 6, 8, 6, 3, '2018-03-17 09:03:26.200000'
  WHERE NOT EXISTS (SELECT id FROM transactions WHERE id = 4);

INSERT INTO transactions (user_id, amount, from_wallet, to_wallet, date)
  SELECT 6,  3.25, 3, 6, '2018-03-21 13:46:24.100000'
  WHERE NOT EXISTS (SELECT id FROM transactions WHERE id = 5);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 4, 1, false, 0.2, '2018-02-10 23:32:43.500000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 1);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 4, 1, false, 0.23, '2018-02-20 12:23:12.200000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 2);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 4, 2, false, 0.75, '2018-02-14 08:37:10.210000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 3);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 5, 3, false, 5, '2018-01-27 11:22:33.440000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 4);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 5, 3, true, 5, '2018-03-03 11:35:06.806000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 5);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 5, 4, false, 6, '2018-01-01 19:27:20.200000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 6);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 6, 5, false, 3, '2018-01-01 19:27:20.200000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 7);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 6, 5, true, 3, '2018-02-26 22:40:14.351000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 8);

INSERT INTO money_history (user_id, goal, direction, amount, date)
  SELECT 6, 6, false, 6, '2018-02-04 12:13:314.500000'
  WHERE NOT EXISTS (SELECT id FROM money_history WHERE id = 9);