
USE dominion;

SET @game = 1;
SET @player = 1;

SELECT * FROM games;

SELECT * FROM players;

SELECT * FROM gamecards ;

SELECT * FROM playercards;

# All cards that belong to players in the game
SELECT * FROM playercards
	INNER JOIN players ON player = players.id
	INNER JOIN games ON game = games.id
	INNER JOIN gamecards ON playercards.card = gamecards.id
	WHERE games.id = @game
	ORDER BY player, pile, `order`,playercards.id ;

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
select * from gamecards
	inner join games on game = games.id 
	where games.id = @game 
	order by deck;

# Move a card to the bottom, doesn't matter the order has a gap with a certain stacktype, 
# the minimum is the lowest of all 
# Test moves card 5 to the bottom (subquery didn't work)
set @minOrder = (SELECT MIN(`order`) from playercards where player = @player) - 1; 
select @minOrder;
UPDATE playercards SET `order` = @minOrder WHERE id = 8;

# SELECT all cards in the game that do not belong to a player
SELECT * from gamecards where game = @game;

# TEST if no more province cards
SELECT `count` = 0 from gamecards where game = @game and card = "province";

# TEST if 3 empty stacks
select count(*) = 3 from gamecards where game = @game and `count` = 0;

# TEST if no more victory cards OR 3 emtpy stacks 
# size of resultset = 3 OR resultset contains "province"
SELECT * FROM gamecards WHERE game = @game AND `count` = 0;

# TEST if no more victory cards OR 3 emtpy stacks 
SELECT MAX(counter) as counter FROM 
(
	SELECT `count` = 0 AS counter FROM gamecards WHERE game = @game AND card = "province" 
	UNION
	SELECT COUNT(*) >= 3 AS counter FROM gamecards WHERE game = @game AND `count` = 0
) AS Q;

# UPDATE make 3 stacks empty
