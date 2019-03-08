package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;


/**
 * Distinguish between:
 * (1) HOLE CARDS: The two cards belonging exclusively to each player
 * (2) AVAILABLE CARDS: The (up to) 7 cards out of which each player can compose their best hand, i.e. the two
 * HOLE CARDS and up to 5 communal cards on the board.
 * (3) BEST HAND: The player's best available hand (up to 5 cards).
 * 
 * We REPR
 * 
 * @author ed
 *
 */
public class Hand implements Comparable {
	private Card[] holeCards;
	private Round round;

	public Hand(Card[] holeCards, Round round) {
		this.holeCards = holeCards;
	}

	public Card[] getHoleCards() {
		return holeCards;
	}
	
	/**
	 * To find a player's best hand, we descend through the hierarchy of hands, finding the first
	 * hand type (e.g. Full House) that they can make with the available cards, and return the
	 * 5 card hand (with best tiebreakers).
	 * 
	 * Hands are only ever evaluated after the river, i.e. when all 5 communal cards are on the
	 * board
	 * 
	 * @return Player's best possible hand, given their HOLE CARDS and the BOARD
	 */
	//Call getPossibleCards once, then pass it as an argument to all the methods e.g. getFlush?
	public Card[] getBestHand() {
		return new Card[5]; //PLACEHOLDER
	}
	
	/**
	 * Ordered by rank
	 * @return
	 */
	public Card[] getPossibleCards() {
		ArrayList<Card> list = new ArrayList<Card>();
		for (Card card: getHoleCards()) {
			list.add(card);
		}
		for (Card card: round.getBoardCards()) {
			list.add(card);
		}
		
		Collections.sort(list);
		Card[] possibles = ((Card[]) list.toArray());
		return possibles;
	}
	

	
	/**
	 * Returns best straight (cards in ascending order). Since we want the best
	 * straight, we iterate backwards over the possible cards.
	 * 
	 * @return best possible straight for the hand. If no straight possible, array
	 *         is all nulls
	 */
	public Card[] getStraight() {
		//iterate over all the cards, not just first 3 cards only, to catch Ace-low straights
		Card[] possibles = getPossibleCards();
		for (int i = 6; i >= 0; i--) {
			Card[] output = new Card[5];
			for (int j = 0; j < 5; j++) {
				if (!isItConsecutive(possibles[i-j-1], possibles[i-j])) {
					break;
				}
				output[j] = possibles[i-j];
				if (j == 4) {
					return possibles; //should only see this code if the j loop completes.
				}
			}
		}
		return new Card[5]; //Return a null array
	}
	
	/**
	 * Helper method for getStraight
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static boolean isItConsecutive(Card a, Card b) {
		//Allows Ace to count as a 1, for the straight Ace,2,3,4,5
		//Allows "wraparound" straights e.g. King,Ace,2,3,4
		if (a.getRank() ==14 && b.getRank() == 2) {
			return true;
		}
		return a.getRank() + 1 == b.getRank();
	}
	
	/**
	 * Returns best possible flush, in descending order
	 * @return
	 */
	public Card[] getFlush() {
		HashMap<Suit, ArrayList<Card>> suitCollections = new HashMap<>();
		Card[] possibles = getPossibleCards();
		for (int i = 6; i >=0; i--) {
			Suit suit = possibles[i].getSuit();
			if (suitCollections.containsKey(possibles[i].getSuit())){
				suitCollections.get(suit).add(possibles[i]);
					if (suitCollections.get(suit).size() == 5) {
						return ((Card[]) suitCollections.get(suit).toArray());
					}
			}
			else { //make new entry for the suit, and put the card into it
				suitCollections.put(suit, new ArrayList<>());
				suitCollections.get(suit).add(possibles[i]);
			}
		}
		return new Card[5];
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
	
