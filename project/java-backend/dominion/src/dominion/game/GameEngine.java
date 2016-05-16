
package dominion.game;

import java.sql.Connection;
import java.util.*;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import dominion.commands.*;
import dominion.model.*;
import dominion.model.database.*;
import dominion.routing.*;
//NOTE: Because some result classes are partial generated with classes
//      in the dominion.model name space (that is secret to the client) 
//      the result classes are declared separately on the client side too.
//      It was either double declarations both here versus here and there...
import dominion.results.*; 

public class GameEngine {
	private Database database;

	// Currently only one game, with sessions multiple :)
	private Game game;

	// TODO: provide session container to constructor
	public GameEngine() throws Exception {
		database = new Database(new PooledMySQLConnectionProvider());
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	@RoutedCommand()
	public ResultBase buyCard(BuyCardCommand command) throws Exception {
		try {
			
			this.validateGame(command.getGameId(), command.getPlayerId());
			Player player = game.getCurrentPlayer();
			
			GameCard card = game.getCard(command.getCardId());
			Guard.validateNotNull(card, "card");
			int[] usedCards = command.getUsedPlayerCardId();
			Guard.validatePlayerHasCardsIn(game, player, "Hand", usedCards);
			
			
			if (player.getCoins() >= card.getCost() && card.getCount() > 0) {
				return buyCard(game, player, card);
			} else {
				Guard.forbidden();
			}
		} catch (Exception e) {
			return new ErrorResult(command.getMethod(), e);
		}
		return null; // Never gets here, Guard.forbidden() throws an exception.
	}

	// TODO: check/validate session
	@RoutedCommand()
	public ResultBase playAction(PlayActionCommand command) {
		try {
			this.validateGame(command.getGameId(), command.getPlayerId());
			Guard.validateEqual(this.game.getState(), "Action", "Invalid game state.");
			if (command.getCancel()) {
				return this.cancelActions();
			}			
			return new ErrorResult(command.getMethod(), new Exception("TODO: return this.playAction()"));// new PlayActionResult();
		} catch (Exception e) {
			Logger.getLogger(GameEngine.class).log(Level.SEVERE, e.toString());
			return new ErrorResult(command.getMethod(), e);
		}
	}
	
	// TODO: check/validate session
	@RoutedCommand()
	public ResultBase startGame(StartGameCommand command) {
		try {
			if (command.getPlayerNames() != null && command.getCardSet() != null) {
				Guard.validatePlayerNames(command.getPlayerNames());
				return startNewGame(command.getPlayerNames(), command.getCardSet());
			} else if (command.getCode() != null) {
				return continueGame(Integer.parseInt(command.getCode()));
			} else {
				throw new Exception("Invalid command data.");
			}
		} catch (Exception e) {
			return new ErrorResult(command.getMethod(), e);
		}
	}

	@RoutedCommand()
	public ViewHighScoresResult viewHighScores(EmptyCommand command) {
		ViewHighScoresResult result = new ViewHighScoresResult();
		List<HighScoreInfo> scores = new ArrayList<HighScoreInfo>();
		scores.add(new HighScoreInfo("Jaan", 9003));
		scores.add(new HighScoreInfo("Wout", 9002));
		scores.add(new HighScoreInfo("Maysam", 9001));
		scores.add(new HighScoreInfo("Michaël", 9000));
		for (Integer i = 99; i > 0; i--) {
			scores.add(new HighScoreInfo("Player " + i, i));
		}
		result.setScores(scores.toArray(new HighScoreInfo[scores.size()]));
		return result;
	}

	@RoutedCommand()
	public TestServerResult testServer(TestServerCommand command) {
		return new TestServerResult(command.getSuccess(), command.getCode(), command.getMethod());
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	private StartBetResult cancelActions() throws Exception {
		try (Connection con = database.getConnection()) {
			game.cancelActions(con);
		}
		return new StartBetResult(); //new StartBuyResult();
	}

	private StartGameResult startNewGame(String[] playerNames, String cardSet) throws Exception {
		// TODO: add game to session
		try (Connection con = database.getConnection()) {
			this.game = new Game(con, playerNames, cardSet);
			return createStartGameResult();
		}
	}

	private StartGameResult continueGame(Integer gameId) throws Exception {
		// TODO: load game from session?
		try (Connection con = database.getConnection()) {
			this.game = new Game(con, gameId);
			return createStartGameResult();
		}
	}

	private StartGameResult createStartGameResult() {
		return new StartGameResult(this.game);
	}

	private BuyCardResult buyCard(Game game, Player player, GameCard card) {
		int maxOrder = player.getMaxOrder();
		return null;

	}

	private void validateGame(int gameId, int playerId) throws Exception {
		Guard.validateNotNull(game, "game");
		Guard.validateEqual(gameId, game.getId(), "Game id is invalid.");
		Guard.validateEqual(playerId, game.getCurrentPlayerId(), "Player id is invalid.");
	}

}
