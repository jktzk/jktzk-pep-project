package Controller;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerUser);
        app.post("/login", this::loginUser);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    private void registerUser(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account newAccount = accountService.register(account);
        if (newAccount != null) {
            ctx.status(200).json(newAccount);
        } else {
            ctx.status(400).result("");
        }
    }

    private void loginUser(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loggedInAccount != null) {
            ctx.status(200).json(loggedInAccount);
        } else {
            ctx.status(401).result("");
        }
    }

    private void createMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message newMessage = messageService.createMessage(message);
        if (newMessage != null) {
            ctx.status(200).json(newMessage);
        } else {
            ctx.status(400).result("");
        }
    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200).result("");
        }
    }

    private void deleteMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);
        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        } else {
            ctx.status(200).result("");
        }
    }

    private void updateMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ctx.bodyAsClass(Message.class);
        Message updatedMessage = messageService.updateMessage(messageId, message.getMessage_text());
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400).result("");
        }
    }

    private void getMessagesByUser(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(accountId);
        ctx.json(messages);
    }
}
