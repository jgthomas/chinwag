package poker;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * 
 * Has hardcoded minimum & maximum player counts of 2 and 10, respectively
 * 
 * Need the blinds to increment every N rounds.
 * 
 * @author ed
 *
 */
public class TournamentGame {
	private Player[] seatedPlayers;
	private int startingChips;
	private int smallBlind;
	private int bigBlind;
	private int blindIncrementRate;
	
	public TournamentGame(ArrayList<Player> players, int startingChips, int smallBlind, int blindIncrementRate) {
		
		int playerCount = players.size();
		assert (playerCount > 1 && playerCount < 11); //Throw custom exception?
		seatedPlayers = new Player[playerCount];
		
		//seat players in random order
		SecureRandom random = new SecureRandom();
		int seatIndex = 0;
		while (playerCount > 0){
			Player player = players.get(random.nextInt(playerCount));
			seatedPlayers[seatIndex] = player;
			players.remove(player);
			playerCount--;
			seatIndex++;
		}
		
		this.startingChips = startingChips;
		this.smallBlind = smallBlind;
		this.bigBlind = smallBlind * 2;
		this.blindIncrementRate = blindIncrementRate;
		
	}

}
