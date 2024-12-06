package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text() != null && !message.getMessage_text().isBlank() &&
            message.getMessage_text().length() <= 255 && message.getPosted_by() > 0) {
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        return messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessage(int messageId, String newText) {
        if (newText != null && !newText.isBlank() && newText.length() <= 255) {
            return messageDAO.updateMessage(messageId, newText);
        }
        return null;
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageDAO.getMessagesByUser(accountId);
    }
}
