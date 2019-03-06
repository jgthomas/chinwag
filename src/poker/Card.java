package poker;

public class Card {
	private CardRank rank;
	private String suit;
	private int value;
	
	public Card(CardRank rank, String suit) {
		this.rank = rank;
		this.suit = suit;
		
		switch (rank) {
			case TWO:
				value = 2;
				break;
			case THREE:
				value = 3;
				break;
			case FOUR:
				value = 4;
				break;
			case FIVE:
				value = 5;
				break;
			case SIX:
				value = 6;
				break;
			case SEVEN:
				value = 7;
				break;
			case EIGHT:
				value = 8;
				break;
			case NINE:
				value = 9;
				break;
			case TEN:
				value = 10;
				break;
			case JACK:
				value = 11;
				break;
			case QUEEN:
				value = 12;
				break;
			case KING:
				value = 13;
				break;
			case ACE:
				value = 14;
				break;
		}
	}
	

}
