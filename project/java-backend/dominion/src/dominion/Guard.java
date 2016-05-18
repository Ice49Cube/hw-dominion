package dominion;

import java.util.regex.Pattern;

import javax.naming.NamingException;

import dominion.model.Game;
import dominion.model.Player;

class Guard {

	static final Pattern USERNAME_PATTERN = Pattern.compile("(?:[A-Za-z0-9_]{1,20}+)");

	static void forbidden() throws Exception {
		throw new Exception("Forbidden!");
	}
	
	static void validateGame(Game game, int gameId, int playerId) throws Exception {
        Guard.validateNotNull(game, "game");
        Guard.validateEqual(gameId, game.getId(), "Game id is invalid.");
        Guard.validateEqual(playerId, game.getCurrentPlayerId(), "Player id is invalid.");
    }	

	static void validateNotNull(Object o, String name) throws Exception {
		if (o == null)
			throw new Exception("Invalid, '" + name + "' is null.");
	}

	static void validateEqual(int i1, int i2, String message) throws Exception {
		if (i1 != i2) {
			throw new Exception(message);
		}
	}

	static void validateEqual(String s1, String s2, String message) throws Exception {
		validateEqual(s1, s2, false, message);
	}

	static void validateEqual(String s1, String s2, boolean ignoreCase, String message) throws Exception {
		if (ignoreCase) {
			if (!s1.equalsIgnoreCase(s2)) {
				throw new Exception(message);
			}
		} else {
			if (!s1.equals(s2)) {
				throw new Exception(message);
			}
		}
	}

	static void validatePlayerNames(String[] playerNames) throws Exception {
		if (playerNames.length < 2 || playerNames.length > 4)
			throw new IndexOutOfBoundsException("Invalid number of players. Minimum 2 and maximum 4.");
		for (String playerName : playerNames) {
			validatePlayerNameOccurences(playerNames, playerName);
			validatePlayerNameFormat(playerName);
		}
	}

	// Actually obsolete because the database shouldn't allow the same name more
	// than once in a game
	static void validatePlayerNameOccurences(String[] names, String name) throws Exception {
		int count = 0;
		for (int index = 0; index < names.length && count < 2; index++)
			if (names[index].equals(name))
				count++;
		if (count > 1)
			throw new NamingException("Using the same player name twice.");
	}

	static void validatePlayerNameFormat(String playerName) throws Exception {
		if (!USERNAME_PATTERN.matcher(playerName).matches())
			throw new NamingException("Invalid player name format.");
	}

	static void validatePlayerHasCardsIn(Game game, Player player, String pile, int[] usedCards) {
		// TODO Auto-generated method stub

	}

	static void validateTrue(boolean expression, String message) {
		if(!expression) {
			throw new IllegalStateException(message);
		}
	}
}
