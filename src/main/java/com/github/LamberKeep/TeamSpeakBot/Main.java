package com.github.LamberKeep.TeamSpeakBot;

import com.github.LamberKeep.TeamSpeakBot.listener.TestListener;
import com.github.manevolent.ts3j.command.CommandException;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeoutException;

public class Main {

  private static final String NICKNAME = "TestBot";
  private static final String ADDRESS = "192.168.1.0";
  private static final int PORT = 9987; // UDP client port, Teamspeak3 client uses 9987
  private static final String PASSWORD = "";

  private static LocalTeamspeakClientSocket client;

  public static void main(String[] args)
      throws IOException, CommandException, InterruptedException, TimeoutException, GeneralSecurityException {
    client = new LocalTeamspeakClientSocket();

    // Set up client

    File file = new File("identity/identity.ini");
    LocalIdentity identity = file.exists() ? LocalIdentity.read(file) : null;

    if (identity == null) {
      System.out.println("Generating new identity.");
      identity = LocalIdentity.generateNew(10);
      file.getParentFile().mkdirs();
      identity.save(file);
    }

    client.setIdentity(identity);
    client.addListener(new TestListener());
    client.setNickname(NICKNAME);

    client.connect(new InetSocketAddress(InetAddress.getByName(ADDRESS), PORT), PASSWORD, 10000L);

    // Subscribe to all channels
    client.subscribeAll();
  }

  public static LocalTeamspeakClientSocket getBotClient() {
    return client;
  }
}
