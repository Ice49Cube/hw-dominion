
package dominion.game;

import java.sql.Connection;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.sun.istack.internal.logging.Logger;

import dominion.commands.*;
import dominion.model.*;
import dominion.model.database.*;
import dominion.routing.*;
//NOTE: Because some result classes are partial generated with classes
//      in the dominion.model name space (that is secret to the client) 
//      the result classes are declared separately on the client side too.
//      It was either double declarations "both here" versus "here and there"...
import dominion.results.*;

public class GameEngine {
	private Database database;

	// Currently only one game, with sessions multiple :)
	private Game game;

	public GameEngine() throws Exception {
		database = new Database(new PooledMySQLConnectionProvider());
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	@RoutedCommand()
	public ResultBase betCoins(BetCoinsCommand cmd) throws Exception {
		try {
			this.validateGame(cmd.getGameId(), cmd.getPlayerId());
			if (cmd.getCancel()) {
				try (Connection con = database.getConnection()) {
					game.getCurrentPlayer().cancelBet(con);
				}
				return new StartBuyResult();
			} else {
				try (Connection con = database.getConnection()) {
					game.getCurrentPlayer().betCoins(con, cmd.getCoinCards());
				}
				return betCoins(cmd.getCoinCards());
			}
		} catch (Exception e) {
			return new ErrorResult(cmd.getMethod(), e);
		}
	}

	@RoutedCommand()
	public ResultBase buyCard(BuyCardCommand cmd) throws Exception {
		try {

			this.validateGame(cmd.getGameId(), cmd.getPlayerId());
			if (cmd.getCancel()) {
				return buyCard();
			} else {
				return buyCard(cmd.getCardId());
			}
		} catch (Exception e) {
			return new ErrorResult(cmd.getMethod(), e);
		}
	}

	// TODO: check/validate session
	@RoutedCommand()
	public ResultBase playAction(PlayActionCommand cmd) {
		try {
			this.validateGame(cmd.getGameId(), cmd.getPlayerId());
			Guard.validateEqual(this.game.getState(), "Action", "Invalid game state.");
			if (cmd.getCancel()) {
				return this.cancelActions();
			}
			return new ErrorResult(cmd.getMethod(), new Exception("TODO: GameEngine.playAction()"));// new
		} catch (Exception e) {
			Logger.getLogger(GameEngine.class).log(Level.SEVERE, e.toString());
			return new ErrorResult(cmd.getMethod(), e);
		}
	}

	// TODO: check/validate session
	@RoutedCommand()
	public ResultBase startGame(StartGameCommand cmd) {
		try {
			if (cmd.getPlayerNames() != null && cmd.getCardSet() != null) {
				Guard.validatePlayerNames(cmd.getPlayerNames());
				return startNewGame(cmd.getPlayerNames(), cmd.getCardSet());
			} else if (cmd.getCode() != null) {
				return continueGame(Integer.parseInt(cmd.getCode()));
			} else {
				throw new Exception("Invalid command data.");
			}
		} catch (Exception e) {
			return new ErrorResult(cmd.getMethod(), e);
		}
	}

	@RoutedCommand()
	public ViewHighScoresResult viewHighScores(EmptyCommand cmd) {
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
	public TestServerResult testServer(TestServerCommand cmd) {
		return new TestServerResult(cmd.getSuccess(), cmd.getCode(), cmd.getMethod());
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	private NextPlayerResult buyCard() throws Exception {
		try (Connection con = database.getConnection()) {
			game.getCurrentPlayer().cancelBuy(con);
			NextPlayerResult result = new NextPlayerResult();
			result.setPlayer(PlayerInfo.fromPlayer(game, game.getCurrentPlayer()));
			return result;
		}
	}
	
	private BuyCardResult buyCard(int cardId) throws Exception {
		try (Connection con = database.getConnection()) {
			Player player = game.getCurrentPlayer();
			GameCard gameCard = game.getCard(cardId);
	        if (gameCard == null) {
	            throw new IllegalStateException("Card not in game. - GameEngine.buyCard");
	        }
			PlayerCard playerCard = player.buyCard(con, gameCard);
			BuyCardResult result = new BuyCardResult();
			result.setGameCard(GameCardInfo.fromCard(gameCard));
			result.setPlayerCard(PlayerCardInfo.fromCard(playerCard));
			return result;
		}
	}

	private BetCoinsResult betCoins(int[] coins) {
		Player player = game.getCurrentPlayer();
		List<Integer> coinList = java.util.stream.IntStream.of(coins).boxed().collect(Collectors.toList());
		PlayerCard[] source = player.getCards(c -> coinList.contains(c.getId()));
		List<PlayerCardInfo> target = new ArrayList<>();
		for(PlayerCard card : source) {
			target.add(PlayerCardInfo.fromCard(card));
		}
		BetCoinsResult result = new BetCoinsResult ();
		result.setPlayerCards(target.toArray(new PlayerCardInfo[target.size()]));
		result.setCoins(player.getCoins());
		return result;
	}
	
	private StartBetResult cancelActions() throws Exception {
		try (Connection con = database.getConnection()) {
			game.getCurrentPlayer().cancelActions(con);
		}
		return new StartBetResult(game); // new StartBuyResult();
	}

	private StartGameResult continueGame(Integer gameId) throws Exception {
		// TODO: load game from session or restore game and save in session...
		try (Connection con = database.getConnection()) {
			this.game = new Game(con, gameId);
			return createStartGameResult();
		}
	}

	private StartGameResult createStartGameResult() {
		return new StartGameResult(this.game);
	}

	private StartGameResult startNewGame(String[] playerNames, String cardSet) throws Exception {
		// TODO: add game to session
		try (Connection con = database.getConnection()) {
			this.game = new Game(con, playerNames, cardSet);
			return createStartGameResult();
		}
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	/* Multi-player theory.
	 * Todo: Provide access to the session container in GameEngine,
	 *       validate user from session, 
	 *       save player and game state into session on game creation,
	 *       store loaded and creaed games in HashMap (and clear out older games zombies from HashMap)
	 *       retrieve game from Session/HashMap in RoutedCommands and pass as argument... 
	 */
	private void validateGame(int gameId, int playerId) throws Exception {
		Guard.validateNotNull(game, "game");
		Guard.validateEqual(gameId, game.getId(), "Game id is invalid.");
		Guard.validateEqual(playerId, game.getCurrentPlayerId(), "Player id is invalid.");
	}

}
