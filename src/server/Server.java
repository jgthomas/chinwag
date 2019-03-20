package server;

import database.Database;
import database.ImageQueue;
import database.MessageQueue;

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
	private static final int MAX_THREADS = 10;
	private final int port;
	private final ExecutorService threadPool;
	private final ConnectedClients connectedClients;
	private final AllChatSessions allChatSessions;

	private static Map<String, Integer> failedAttempts = new HashMap<>();
	private static Map<String, Date> lockedAccounts = new HashMap<>();

	public Server(int port) {
		this.port = port;
		connectedClients = new ConnectedClients();
		threadPool = Executors.newFixedThreadPool(MAX_THREADS);
		allChatSessions = new AllChatSessions();
	}
	
	public static synchronized Map<String, Integer> getFailedAttempts() {
		return failedAttempts;
	}

	public static synchronized Map<String, Date> getLockedAccounts() {
		return lockedAccounts;
	}

	public void runServer() {
		Socket clientSocket;
		Database.makeConnection();
		MessageQueue messageQueue = new MessageQueue();
		ImageQueue imageQueue = new ImageQueue();
		threadPool.execute(messageQueue);
		threadPool.execute(imageQueue);
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				clientSocket = serverSocket.accept();
				String socketID = buildID(clientSocket);
				MessageHandler messageHandler = new ClientHandler(
						clientSocket,
						connectedClients,
						allChatSessions,
						socketID);
				connectedClients.addClientByID(socketID, messageHandler);
				threadPool.execute(messageHandler);
			}
		} catch (IOException ioException) {
			closeThreadPool();
			ioException.printStackTrace();
		}

		closeThreadPool();
		Database.closeConnection();
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

	private String buildID(Socket clientSocket) {
		return clientSocket.getInetAddress().getHostAddress() + "_" + clientSocket.getPort();
	}

	public static void main(String[] args) {
		Server server = new Server(6000);
		server.runServer();
	}
}
