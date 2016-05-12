USE dominion;

INSERT INTO games () VALUES ();
SET @game = LAST_INSERT_ID();

INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Treasure", "gold",  10, 6, 0, 1);
SET @gold = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Treasure", "silver",  10, 3, 0, 1);
SET @silver = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Treasure", "copper",  10, 0, 0, 1);
SET @copper = LAST_INSERT_ID();

INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "adventurer",  10, 6, 1, 0);
SET @adventurer = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "bureaucrat",  10, 4, 1, 0);
SET @bureaucrat = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "cellar",  10, 2, 1, 0);
SET @cellar = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "chancellor",  10, 3, 1, 0);
SET @chancellor = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "chapel",  10, 2, 1, 0);
SET @chapel = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "council_room",  10, 5, 1, 0);
SET @council_room = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "feast",  10, 4, 1, 0);
SET @feast = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "festival",  10, 5, 1, 0);
SET @festival = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "gardens",  10, 4, 0, 0);
SET @gardens = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Kingdom", "laboratory",  10, 5, 1, 0);
SET @laboratory = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Victory", "province",  10, 8, 0, 0);
SET @province = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Victory", "duchy",  10, 5, 0, 0);
SET @duchy = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Victory", "estate",  10, 2, 0, 0);
SET @estate = LAST_INSERT_ID();
INSERT INTO gamecards (game, deck, `name`, `count`, cost, isaction, iscoin) VALUES (@game, "Victory", "curse",  10, 0, 0, 0);
SET @curse = LAST_INSERT_ID();

INSERT INTO players (game, `name`) VALUES (@game, "michaël");
SET @michael = LAST_INSERT_ID();
INSERT INTO players (game, `name`) VALUES (@game, "maysam");
SET @maysam = LAST_INSERT_ID();


INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 1, "Hand", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 2, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 3, "Hand", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 4, "Hand", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 5, "Deck", @estate);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 6, "Hand", @estate);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 7, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 8, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 9, "Hand", @estate);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@michael, 10, "Deck", @copper);


INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 1, "Deck", @estate);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 2, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 3, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 4, "Deck", @estate);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 5, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 6, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 7, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 8, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 9, "Deck", @copper);
INSERT INTO playercards (player, `order`, pile, card) VALUES(@maysam, 10, "Deck", @estate);

UPDATE games SET currentplayer = @michael WHERE id = @game;

# All cards that belong to players in the game
SELECT * FROM playercards
	INNER JOIN players ON player = players.id
	INNER JOIN games ON game = games.id
	INNER JOIN gamecards ON playercards.card = gamecards.id
	WHERE games.id = @game;
