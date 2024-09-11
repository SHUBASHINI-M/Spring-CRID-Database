package com.example.webex.UserRepo;

import org.springframework.data.repository.CrudRepository;

import com.example.webex.Entities.Account;

public interface UserRepository extends CrudRepository<Account, Integer> {
}
	
