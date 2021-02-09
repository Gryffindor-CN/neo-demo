USE ds_0;

CREATE TABLE t_order_0 (
	order_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	status varchar(32) NOT NULL,
	PRIMARY KEY (order_id)
);

CREATE TABLE t_order_1 (
	order_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	status varchar(32) NOT NULL,
	PRIMARY KEY (order_id)
);

CREATE TABLE t_order_item_0 (
	item_id BIGINT NOT NULL,
	order_id BIGINT NOT NULL,
	status VARCHAR(32) NOT NULL,
	user_id	BIGINT NOT NULL,
	PRIMARY KEY (item_id)
);

CREATE TABLE t_order_item_1 (
	item_id BIGINT NOT NULL,
	order_id BIGINT NOT NULL,
	status VARCHAR(32) NOT NULL,
	user_id	BIGINT NOT NULL,
	PRIMARY KEY (item_id)
);

USE ds_1;

CREATE TABLE t_order_0 (
	order_id BIGINT	NOT NULL,
	user_id BIGINT NOT NULL,
	status varchar(32) NOT NULL,
	PRIMARY KEY (order_id)
);

CREATE TABLE t_order_1 (
	order_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	status varchar(32) NOT NULL,
	PRIMARY KEY (order_id)
);

CREATE TABLE t_order_item_0 (
	item_id BIGINT NOT NULL,
	order_id BIGINT NOT NULL,
	status VARCHAR(32) NOT NULL,
	user_id	BIGINT NOT NULL,
	PRIMARY KEY (item_id)
);

CREATE TABLE t_order_item_1 (
	item_id BIGINT NOT NULL,
	order_id BIGINT NOT NULL,
	status VARCHAR(32) NOT NULL,
	user_id	BIGINT NOT NULL,
	PRIMARY KEY (item_id)
);
