package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account register(Account account) {
        if (account.getUsername() != null && !account.getUsername().isBlank() &&
            account.getPassword() != null && account.getPassword().length() >= 4) {
            return accountDAO.register(account);
        }
        return null;
    }

    public Account login(String username, String password) {
        if (username != null && !username.isBlank() && password != null && !password.isBlank()) {
            return accountDAO.login(username, password);
        }
        return null;
    }
}
