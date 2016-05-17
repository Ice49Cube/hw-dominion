
USE dominion;

SET @game = 1;
SET @player = 1;

SELECT * FROM games;

UPDATE players SET coins = coins + 100 WHERE id = 1;
SELECT * FROM players;

SELECT * FROM gamecards ;

SELECT * FROM playercards;

# All cards that belong to players in the game
SELECT * FROM playercards
	INNER JOIN players ON player = players.id
	INNER JOIN games ON game = games.id
	INNER JOIN gamecards ON playercards.card = gamecards.id
	WHERE games.id = @game
	ORDER BY game.player, pile, `order`,playercards.id ;

# All cards that belong to one player in the game
SELECT * FROM playercards
	INNER JOIN players ON playercards.player = players.id
	INNER JOIN games ON game = games.id
	INNER JOIN gamecards ON playercards.card
	WHERE playercards.player = @player
	ORDER BY pile, `order`;
SELECT * FROM gamecards;


SELECT playercards.id, players.name, gamecards.value, playercards.order , playercards.pile, games.player  AS "current", players.id AS "playerid" FROM playercards
	INNER JOIN players ON playercards.player = players.id
	INNER JOIN games ON game = games.id
	INNER JOIN gamecards ON playercards.card = gamecards.id ORDER BY playercards.id DESC, playercards.order DESC ;




# All cards that are in the game but do not belong to a player (some do, but that's stored in count)
SELECT * FROM gamecards
	INNER JOIN games ON game = games.id 
	WHERE games.id = @game 
	ORDER BY deck;

# Move a card to the bottom, doesn't matter the order has a gap with a certain stacktype, 
# the minimum is the lowest of all 
# Test moves card 5 to the bottom (subquery didn't work)
SET @minOrder = (SELECT MIN(`order`) FROM playercards WHERE player = @player) - 1; 
SELECT @minOrder;
UPDATE playercards SET `order` = @minOrder WHERE id = 8;

# SELECT all cards in the game that do not belong to a player
SELECT * FROM gamecards WHERE game = @game;

# TEST if no more province cards
SELECT `count` = 0 FROM gamecards WHERE game = @game AND card = "province";

# TEST if 3 empty stacks
SELECT COUNT(*) = 3 FROM gamecards WHERE game = @game AND `count` = 0;

# TEST if no more victory cards OR 3 emtpy stacks 
# size of resultset = 3 OR resultset contains "province"
SELECT * FROM gamecards WHERE game = @game AND `count` = 0;

# TEST if no more victory cards OR 3 emtpy stacks 
SELECT MAX(counter) AS counter FROM 
(
	SELECT `count` = 0 AS counter FROM gamecards WHERE game = @game AND card = "province" 
	UNION
	SELECT COUNT(*) >= 3 AS counter FROM gamecards WHERE game = @game AND `count` = 0
) AS Q;

# UPDATE make 3 stacks empty






