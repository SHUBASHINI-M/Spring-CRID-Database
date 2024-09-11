package com.example.webex.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer accountNumber;
	    private String name;
	    private Double balance;
	    private String email;

	    public Account() {}

	    public Account(String name, Double balance, String email) {
	        this.name = name;
	        this.balance = balance;
	        this.email = email;
	    }

	    
	    public Integer getAccountNumber() {
	        return accountNumber;
	    }

	    public void setAccountNumber(Integer accountNumber) {
	        this.accountNumber = accountNumber;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Double getBalance() {
	        return balance;
	    }

	    public void setBalance(Double balance) {
	        this.balance = balance;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }
	    @Override
	    public String toString() {
	        return "Account [accountNumber=" + accountNumber + ", name=" + name + ", balance=" + balance + ", email=" + email + "]";
	    }

	}


