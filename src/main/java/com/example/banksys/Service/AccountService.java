package com.example.banksys.Service;

import com.example.banksys.Api.ApiException;
import com.example.banksys.Model.Account;
import com.example.banksys.Model.Customer;
import com.example.banksys.Model.User;
import com.example.banksys.Repository.AccountRepository;
import com.example.banksys.Repository.CustomerRepository;
import com.example.banksys.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    // Get all accounts
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    // Add a new account if the customer exists
    public void addAccount(Integer customerId, Account account) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        account.setIsActive(false);
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    // Update an account if the customer exists
    public void updateAccount(Integer customerId, Account account) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    // Delete an account if the customer exists
    public void deleteAccount(Integer customerId, Integer accountId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null || !account.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("Account not found or does not belong to the customer");
        }
        accountRepository.delete(account);
    }
    public void activateAccount(Integer customerId, Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer = customerRepository.findCustomerById(customerId);

        if (account.getCustomer().getId().equals(customer.getId())) {
            account.setIsActive(true);
            accountRepository.save(account);
        } else {
            throw new RuntimeException("Account does not belong to the authenticated customer");
        }
    }
    public Account getAccountByCustomerId(Integer customerId, Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer = customerRepository.findCustomerById(customerId);

        if (account.getCustomer().getId().equals(customer.getId())) {
            return account;
        } else {
            throw new RuntimeException("You are not authorized to view this account");
        }
    }
    //list accounts
    // Get accounts by user ID
    public List<Account> findAccountsByUserId(Integer userId) {
        return accountRepository.findByCustomerUserId(userId);
    }

    // Deposit money
    public void deposit(Integer userId,Integer accountId, Double amount) {
        Account account = accountRepository.findAccountById(accountId);
        User user = userRepository.findUserById(userId);
        if(user == null) {
            throw new ApiException("User not found for this user id ");
        }

        if (account == null) {
            throw new ApiException("Account not found with this id ");
        }
        if (!account.getIsActive()){
            throw new ApiException("Account is not active");
        }

        if (amount <= 0) {
            throw new ApiException("Deposit amount must be greater than zero.");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }




    public void withdrawMoney(Integer userId, Integer accountId, Double amount) {
        // Fetch the account
        Account account = accountRepository.findAccountById(accountId);
        User user = userRepository.findUserById(userId);

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check if the account is active and not blocked
        if (account.getIsActive() == null || !account.getIsActive()) {
            throw new RuntimeException("Account is not active");
        }

        if (account.getIsBlocked() != null && account.getIsBlocked()) {
            throw new RuntimeException("Account is blocked");
        }

        // Check for sufficient balance
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Perform the withdrawal
        account.setBalance(account.getBalance() - amount);

        // Save the updated account
        accountRepository.save(account);
    }

    public void transferMoney(Integer userId, Integer fromAccountId, Integer toAccountId, double amount) {
        // Fetch the source and destination accounts
        User user = userRepository.findUserById(userId);

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check if the accounts are active and not blocked WORKS
        if (fromAccount.getIsActive() == null || !fromAccount.getIsActive()) {
            throw new RuntimeException("Source account is not active");
        }

        if (fromAccount.getIsBlocked() != null && fromAccount.getIsBlocked()) {
            throw new RuntimeException("Source account is blocked");
        }

        if (toAccount.getIsActive() == null || !toAccount.getIsActive()) {
            throw new RuntimeException("Destination account is not active");
        }

        if (toAccount.getIsBlocked() != null && toAccount.getIsBlocked()) {
            throw new RuntimeException("Destination account is blocked");
        }

        // Check for sufficient balance WORKS
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in source account");
        }

        // Perform the transfer - CHECK
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        // Save the updated accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    //BLOCK WORKS
    public void blockAccount(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setIsBlocked(true);
        accountRepository.save(account);
    }


}
