package telegram_bot;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class HinataBot extends TelegramLongPollingBot {

	private String nome;
	private String sobrenome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getBotUsername() {
		return "HinataBot";
	}

	public String getBotToken() {
		return "1743022277:AAFkVL6e951PlIldR67vvtNQXZyvhclpGJ4";
	}

	public void onUpdateReceived(Update update) {

		String command = update.getMessage().getText();

		setNome(update.getMessage().getFrom().getFirstName());
		setSobrenome(update.getMessage().getFrom().getLastName());

		SendMessage message = new SendMessage();

		message.setText("");

		if (command.toLowerCase().equals("oi")) {
			message.setText("oi");
		}

		Pattern pattern = Pattern.compile("tudo bem");
		Matcher matcher = pattern.matcher(command);
		if (matcher.find()) {
			message.setText(message.getText() + " tudo bem e voce?");
		}

		if (command.equals("/saudacao")) {
			Calendar cal = Calendar.getInstance();
			int hora = cal.get(Calendar.HOUR_OF_DAY);

			String retorno = "";
			if (hora < 12) {
				retorno = "Bom dia ";
			} else if (hora >= 12 && hora < 18) {
				retorno = "Boa Tarde ";
			} else {
				retorno = "Boa Noite ";
			}
			message.setText(retorno + getNomeCompleto());
		}

		if (command.equals("/meunome")) {
			message.setText(update.getMessage().getFrom().getFirstName());
		}

		if (command.equals("/meusobrenome")) {
			message.setText(update.getMessage().getFrom().getLastName());
		}

		if (command.equals("/meunomecompleto")) {
			message.setText(getNomeCompleto());
		}

		message.setChatId(update.getMessage().getChatId());

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	private String getNomeCompleto() {
		String nomeCompleto = getNome();
		if (getSobrenome() != null) {
			nomeCompleto += " " + getSobrenome();
		}
		return nomeCompleto;
	}

}
