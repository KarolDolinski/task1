package com.kd.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class Transaction {
    Map<String, String> transactionDb;
}
