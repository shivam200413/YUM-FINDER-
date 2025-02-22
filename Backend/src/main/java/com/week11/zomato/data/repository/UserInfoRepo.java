package com.week11.zomato.data.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.week11.zomato.data.model.UserInfo;

public interface UserInfoRepo extends CrudRepository<UserInfo, Integer> {
    public Optional<UserInfo> findByPhonenumber(String phonenumber);
}
