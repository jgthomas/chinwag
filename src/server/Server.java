package server;

import database.Database;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Listens for incoming clients and spins off a new thread for each one.
 *
 * Each client is handled by a class implementing the MessageHandler interface.
 *
 */
public class Server {
	private static final int MAX_THREADS = 3;
	private final int port;
	private final ExecutorService threadPool;
	private final ChatContext global;
	private static Map<String, Integer> failedAttempts = new HashMap<>();
	private static Map<String, Date> lockedAccounts = new HashMap<>();

	public Server(int port) {
		this.port = port;
		threadPool = Executors.newFixedThreadPool(MAX_THREADS);
		global = new ChatSession("global");
	}
	
	public static synchronized Map<String, Integer> getFailedAttempts() {
		return failedAttempts;
	}

	public static synchronized Map<String, Date> getLockedAccounts() {
		return lockedAccounts;
	}

	public void runServer() {
		Socket clientSocket;
		System.out.println("Server available...");
		Database.makeConnection();
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				System.out.println("Waiting for connection...");
				clientSocket = serverSocket.accept();
				MessageHandler messageHandler = new ClientHandler(clientSocket, global);
				threadPool.execute(messageHandler);
			}
		} catch (IOException ioException) {
			closeThreadPool();
			ioException.printStackTrace();
		}

		closeThreadPool();
		Database.closeConnection();
		System.out.println("Server terminating...");
	}

	private void closeThreadPool() {
		threadPool.shutdown();

		try {
			if (!threadPool.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				threadPool.shutdownNow();
			}
		} catch (InterruptedException e) {
			threadPool.shutdownNow();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(6000);
		server.runServer();
	}
}
