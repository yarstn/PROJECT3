package com.example.banksys.Controller;

import com.example.banksys.Model.Account;
import com.example.banksys.Model.User;
import com.example.banksys.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@RestController
public class AccountController {
    private final AccountService accountService;

    // GET all accounts (possibly filtered by user if needed)
    @GetMapping("/get")
    public ResponseEntity getAllAccounts(@AuthenticationPrincipal User user) {
            List<Account> accounts = accountService.findAll();
            return ResponseEntity.ok(accounts);
    }

    // Add a new account using the authenticated user
    @PostMapping("/add")
    public ResponseEntity<String> addAccount(@AuthenticationPrincipal User user, @RequestBody Account account) {
        accountService.addAccount(user.getCustomer().getId(), account);
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }

    // Update an existing account using the authenticated user
    @PutMapping("/update")
    public ResponseEntity<String> updateAccount(@AuthenticationPrincipal User user, @RequestBody Account account) {
        accountService.updateAccount(user.getCustomer().getId(), account);
        return new ResponseEntity<>("Account updated successfully", HttpStatus.OK);
    }

    // Delete an account using the authenticated user
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<String> deleteAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.deleteAccount(user.getCustomer().getId(), accountId);
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
    }
    @PutMapping("/activate/{accountId}")
    public ResponseEntity<String> activateAccount(@AuthenticationPrincipal User authenticatedUser, @PathVariable Integer accountId) {
        accountService.activateAccount(authenticatedUser.getId(), accountId);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@AuthenticationPrincipal User authenticatedUser, @PathVariable Integer accountId) {
        Account account = accountService.getAccountByCustomerId(authenticatedUser.getId(), accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    // GET /api/v1/account/my-accounts
    //works
    @GetMapping("/my-accounts")
    public ResponseEntity getMyAccounts(@AuthenticationPrincipal User user) {
        List<Account> accounts = accountService.findAccountsByUserId(user.getId());
        return ResponseEntity.ok(accounts);
    }
    // Deposit money into an account
    //WORKS
    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity depositMoney(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.deposit(user.getId(),accountId, amount);
        return ResponseEntity.status(200).body("Deposit successfully");
    }


//withdraw
    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity withdrawMoney(@PathVariable Integer accountId, @PathVariable double amount, @AuthenticationPrincipal User user) {
        accountService.withdrawMoney(user.getId(),accountId,amount);
        return new ResponseEntity("Withdrawal successful", HttpStatus.OK);
    }

    //trnsfer WORKS
    @PutMapping("/transfer/{fromAccountId}/{toAccountId}/{amount}")
    public ResponseEntity transferMoney(@PathVariable Integer fromAccountId, @PathVariable Integer toAccountId, @PathVariable double amount,@AuthenticationPrincipal User user) {
            accountService.transferMoney(user.getId(),fromAccountId, toAccountId, amount);
            return ResponseEntity.ok("Transfer successful");

    }
    //BLOCK WORKS
    @PutMapping("/block/{accountId}")
    public ResponseEntity  blockAccount(@PathVariable Integer accountId) {
            accountService.blockAccount(accountId);
            return ResponseEntity.ok("Account blocked successfully");

    }


}
