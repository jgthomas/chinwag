package poker;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Implements a deck throughout the course of a game, so that it will start with 52 cards and
 * deplete as they are randomly dealt to players and the flop.
 * 
 * Please note there is no shuffle method - each time we need to deal a card, one is randomly
 * selected, as opposed to initially randomizing into a fixed order then taking cards off the top.
 * 
 * @author Ed
 *
 */
public class Deck {
	private ArrayList<Card> remainingContents; //take things out of this as they are dealt
	
	/**
	 * Populates a fresh deck with all the cards.
	 */
	public Deck() {
		for (Suit suit: Suit.values() ) {
			for (CardRank rank: CardRank.values()) {
				remainingContents.add(new Card(rank, suit));
			}
		}
	}
	
	/**
	 * Randomly deal one card, removing it from the remaining contents
	 * @return
	 */
	public Card dealOneCard() {
		int deckSize = remainingContents.size();
		SecureRandom random = new SecureRandom();
		int selectedIndex = random.nextInt(deckSize);
		Card dealt = remainingContents.get(selectedIndex);
		remainingContents.remove(selectedIndex);
		return dealt;
	}
	
	//deal hole cards
	
	//deal flop
	
	//deal turn + river?
	

}
