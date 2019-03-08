package poker;

/**
 * Represents a round of the game (sometimes known as a hand), i.e. from an initial deal, through
 * bets, until the round ends with a showdown or uncalled bet.
 * 
 * @author ed
 *
 */

public class Round {
	private Player[] seatedPlayers;
	private Card[] boardCards;
	private Deck deck;
	
	
	public Card[] getBoardCards() {
		return boardCards;
	}

}
