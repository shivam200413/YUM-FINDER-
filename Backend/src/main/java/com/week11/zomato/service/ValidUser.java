package com.week11.zomato.service;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.week11.zomato.data.model.UserInfo;
import com.week11.zomato.data.repository.UserInfoRepo;

@Service
public class ValidUser {

    @Autowired
    private UserInfoRepo userInfoRepo;

    public boolean isPhoneNumberUnique(String phoneNumber) {

        Iterable<UserInfo> itr = userInfoRepo.findAll();

        Iterator<UserInfo> i = itr.iterator();

        while (i.hasNext()) {

            UserInfo userInfo = i.next();
            if (phoneNumber.equals(userInfo.getPhonenumber()))
                return false;

        }
        return true;

    }

    public boolean isPasswordValid(String phonenumber, String password) {

        Optional<UserInfo> userInfo = userInfoRepo.findByPhonenumber(phonenumber);
        if (userInfo.get().getPassword().equals(password)) {
            return true;
        }
        return false;

    }

}
