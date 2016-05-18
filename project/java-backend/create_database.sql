
## game
#
# cardset is the cardset of that game
#
# player is the current player
#
# player state is the state of the player
#
# winner is set when the game is over
#
# startdate is the date the game started
##

## PlayerCards - The cards that do belong to a player
#
# pile is the current location. 
#	The difference between Playing and table is 
#	a card that isn't completed in one turn.(Militia, Throne Room)
#
# order is the order of the cards, 
#	Maintained in the Player class (java model) as a minOrder and maxOrder members.
#
# 	For example when moving a card to the top all other cards maintain the order.
#
#	1 Table ; 2 Deck ; 3 Hand ; 4 Deck ; 5 Deck
#	2 Deck  ; 3 Hand ; 4 Deck ; 5 Deck ; 6 Table 
#
# state is to allow a card to save it's state 
## git


DROP DATABASE IF EXISTS dominion;

CREATE DATABASE dominion;

USE dominion;

CREATE TABLE games (
	id INT NOT NULL AUTO_INCREMENT,
	cardset VARCHAR(20) NOT NULL,
	player INT,
	state ENUM('Action', 'Bet', 'Buy', 'Over') DEFAULT "Action",
	winner INT DEFAULT -1,
	startdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_game PRIMARY KEY (id),
	CONSTRAINT fk1_game FOREIGN KEY (player) REFERENCES players(id),
	CONSTRAINT fk2_game FOREIGN KEY (winner) REFERENCES players(id)
);

CREATE TABLE gamecards (
	id INT NOT NULL AUTO_INCREMENT,
	game INT NOT NULL,
	`name` VARCHAR(20) NOT NULL,
	deck ENUM('Treasure', 'Victory', 'Kingdom') NOT NULL,
	`count` INT NOT NULL,
	cost INT NOT NULL,
	`value` INT NOT NULL,
	isaction BOOLEAN NOT NULL,
	iscoin BOOLEAN NOT NULL,
	CONSTRAINT pk_gamecard PRIMARY KEY (id),
	CONSTRAINT uk1_gamecard UNIQUE KEY (game, `name`),
	CONSTRAINT fk1_gamecard FOREIGN KEY(game) REFERENCES games(id)
);

CREATE TABLE players (
	id INT NOT NULL AUTO_INCREMENT,
	game INT NOT NULL,
	`name` VARCHAR(20) NOT NULL,
	actions INT NOT NULL DEFAULT 1,
	buys INT NOT NULL DEFAULT 1,
	coins INT NOT NULL DEFAULT 0,
	CONSTRAINT pk_player PRIMARY KEY (id),
	CONSTRAINT uk1_player UNIQUE KEY (game, `name`),
	CONSTRAINT fk1_player FOREIGN KEY(game) REFERENCES games(id)
);

CREATE TABLE playercards (
	id INT NOT NULL AUTO_INCREMENT,
	player INT NOT NULL,
	card INT NOT NULL,
	pile ENUM('Discard', 'Deck', 'Hand', 'Playing', 'Table') NOT NULL,
	`order` INT NOT NULL,
	state BLOB,
	CONSTRAINT pk_playercard PRIMARY KEY (id),
	CONSTRAINT fk1_playercard FOREIGN KEY(player) REFERENCES players(id),
	CONSTRAINT fk2_playercard FOREIGN KEY(card) REFERENCES gamecards(id)
);

CREATE TABLE trash (
	id INT NOT NULL AUTO_INCREMENT,
	game INT NOT NULL,
	card INT NOT NULL,
	player INT NOT NULL,
	CONSTRAINT pk_discard PRIMARY KEY (id),
	CONSTRAINT fk1_discard FOREIGN KEY(game)   REFERENCES games(id),
	CONSTRAINT fk2_discard FOREIGN KEY(card)   REFERENCES gamecards(id),
	CONSTRAINT fk3_discard FOREIGN KEY(player) REFERENCES players(id)
);
