package database;

import java.io.*;
import java.util.*;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class UserGenerator {

	private static final String letters = "abcdefghijklmnopqrstuvwxyz";
	private static final int letterLength = 3;
	private static final String numbers = "0123456789";
	private static final int numberLength = 3;
	private static final String passwordChars = 
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int passwordLength = 10;
	
	public String generateUsername() {
		StringBuffer random = new StringBuffer();
		for(int i = 0; i < letterLength; i++) {
			int number = getRandomNumber(letters.length());
			char ch = letters.charAt(number);
			random.append(ch);
		}
		for(int i = 0; i < numberLength; i++) {
			int number = getRandomNumber(numbers.length());
			char ch = numbers.charAt(number);
			random.append(ch);
		}
		return random.toString();
	}
	
	public String generatePassword() {
		StringBuffer random = new StringBuffer();
		for (int i = 0; i < passwordLength; i++) {
			int number = getRandomNumber(passwordChars.length());
			char ch = passwordChars.charAt(number);
			random.append(ch);
		}
		return random.toString();
	}
	
	public int getRandomNumber(int n) {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(n);
		if(randomInt - 1 == -1) {
			return randomInt;
		}
		else {
			return randomInt - 1;
		}
	}
	
	public Set<User> generateUsers(int n) {
		Set<User> s = new HashSet<>();
		for (int i = 0; i < n; i++) {
			User u = new User(generateUsername(), generatePassword());
			s.add(u);
		}
		return s;
	}
	
	public void printToFile(Set<User> s) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("users"));
			Iterator<User> iter = s.iterator();
			while(iter.hasNext()) {
				User u = iter.next();
				out.write(u.getUsername() + " " + u.getPassword() + "\n");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		UserGenerator u = new UserGenerator();
		Set<User> s = u.generateUsers(300);
		u.printToFile(s);
	}

}
