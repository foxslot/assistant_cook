package assistant_cook.TelegramBots;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.sound.midi.Soundbank;
import java.util.List;

public class TelegramBotJavaHW extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());

            try {

                execute(message); // Call method to send the message

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return "@test_java_hw_bot";
    }

    @Override
    public String getBotToken() {
        return "5630830058:AAF_KyzlktNAYFWyfxyGwxrx2AvCz1XG3QY";
    }
    @Override
    public void onRegister() {
        super.onRegister();
    }


}
