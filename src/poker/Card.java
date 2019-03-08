package poker;

public class Card implements Comparable {
	
	private final CardRank RANK;
	private final Suit SUIT;
	private int CARD_VALUE; //Handle ace being lower in other way
	/**
	 * //clubs are 1-13, Diamonds 14-26, Hearts 27-39, Spades 40-52
	 */
	private int deckPosition;
	
	public Card(CardRank rank, Suit suit) {
		this.RANK = rank;
		this.SUIT = suit;

		deckPosition = CARD_VALUE - 1;
		if (SUIT == Suit.DIAMONDS) {
			deckPosition += 13;
		} else if (SUIT == Suit.HEARTS) {
			deckPosition += 26;
		} else if (SUIT == Suit.SPADES) {
			deckPosition += 39;
		}
	}
	
	public int getRank() {
		return RANK.getCardValue();
	}
	
	public Suit getSuit() {
		return SUIT;
	}

	@Override
	public int compareTo(Object o) {
		return this.getRank() - ((Card) o).getRank();
	}

}

//switch (rank) {
//case TWO:
//	value = 2;
//	break;
//case THREE:
//	value = 3;
//	break;
//case FOUR:
//	value = 4;
//	break;
//case FIVE:
//	value = 5;
//	break;
//case SIX:
//	value = 6;
//	break;
//case SEVEN:
//	value = 7;
//	break;
//case EIGHT:
//	value = 8;
//	break;
//case NINE:
//	value = 9;
//	break;
//case TEN:
//	value = 10;
//	break;
//case JACK:
//	value = 11;
//	break;
//case QUEEN:
//	value = 12;
//	break;
//case KING:
//	value = 13;
//	break;
//case ACE:
//	value = 14;
//	break;

