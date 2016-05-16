
package dominion.game;

import java.sql.Connection;
import java.util.*;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import dominion.commands.*;
import dominion.model.*;
import dominion.model.database.*;
import dominion.routing.*;
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
			System.out.println("Game id: " + command.getGameId());
			System.out.println("Game id: " + game.getId());
			this.validateGame(command.getGameId(), command.getPlayerId());
			if (command.getCancel()) {
				try (Connection con = database.getConnection()) {
					game.cancelActions(con);
				}
				return new StartBuyResult();
			}
			return null;// new PlayActionResult();
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
	public ResultBase testIets(EmptyCommand command) {
		return new ResultBase("playMilitia") {

			public String beetjeTekst = "hello";

		};
	}

	@RoutedCommand()
	public ViewHighScoresResult viewHighScores(EmptyCommand command) {
		ViewHighScoresResult result = new ViewHighScoresResult();
		List<HighScoreResult> scores = new ArrayList<HighScoreResult>();
		scores.add(new HighScoreResult("Jaan", 9003));
		scores.add(new HighScoreResult("Wout", 9002));
		scores.add(new HighScoreResult("Maysam", 9001));
		scores.add(new HighScoreResult("Michaël", 9000));
		for (Integer i = 99; i > 0; i--) {
			scores.add(new HighScoreResult("Player " + i, i));
		}
		result.setScores(scores.toArray(new HighScoreResult[scores.size()]));
		return result;
	}

	@RoutedCommand()
	public TestServerResult testServer(TestServerCommand command) {
		return new TestServerResult(command.getSuccess(), command.getCode(), command.getMethod());
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

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
