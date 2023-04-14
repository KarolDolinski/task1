package com.kd.assignment.controller;

import com.kd.assignment.repository.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction() {
        transactionService.begin();
    }

    @PostMapping("/commit")
    @ResponseStatus(HttpStatus.OK)
    public void commit() {
        transactionService.commit();
    }

    @PostMapping("/rollback")
    @ResponseStatus(HttpStatus.OK)
    public void rollback() {
        transactionService.rollback();
    }
}
