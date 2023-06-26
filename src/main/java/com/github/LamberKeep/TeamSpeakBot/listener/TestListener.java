package com.github.LamberKeep.TeamSpeakBot.listener;

import static com.github.LamberKeep.TeamSpeakBot.Main.getBotClient;

import com.github.manevolent.ts3j.command.CommandException;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TestListener implements TS3Listener {

  public void onTextMessage(TextMessageEvent textMessageEvent) {

    if (textMessageEvent.getInvokerId() == getBotClient().getClientId()) {
      return;
    }

    // Global chat example
    try {
      getBotClient().sendServerMessage("Echo!");
    } catch (IOException | CommandException | InterruptedException | TimeoutException e) {
      throw new RuntimeException(e);
    }

    // Channel chat example
    try {
      getBotClient().sendChannelMessage(textMessageEvent.getTargetClientId(), "Echo!");
    } catch (IOException | CommandException | InterruptedException | TimeoutException e) {
      throw new RuntimeException(e);
    }

    // PM to the sender
    try {
      getBotClient().sendPrivateMessage(textMessageEvent.getInvokerId(), "Echo!");
    } catch (IOException | TimeoutException | InterruptedException | CommandException e) {
      throw new RuntimeException(e);
    }
  }
}
