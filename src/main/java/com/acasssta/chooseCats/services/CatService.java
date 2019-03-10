package com.acasssta.chooseCats.services;

import com.acasssta.chooseCats.entities.Cat;
import com.acasssta.chooseCats.entities.User;
import com.acasssta.chooseCats.repositories.CatRepository;
import com.acasssta.chooseCats.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class CatService {

    private CatRepository catRepository;
    private UserRepository userRepository;

    @Autowired
    public void setCatRepository(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Cat> getAllCats(){
        return (List<Cat>)catRepository.findAll();
    }

    public List<Cat> getTwoCats(String username){
        User currentUser = userRepository.findOneByUserName(username);
        List<Cat> usersCats = currentUser.getUserCat();
        if (usersCats.size()!=0) {
            Random random = new Random();
            Integer num1 = random.nextInt(usersCats.size());
            Integer num2 = num1.intValue();
            while (num1.equals(num2)) {
                num2 = random.nextInt(usersCats.size());
            }
            Cat cat1 = usersCats.get(num1);
            Cat cat2 = usersCats.get(num2);
            List<Cat> result = Arrays.asList(cat1, cat2);
            usersCats.removeAll(result);
            userRepository.save(currentUser);
            return result;
        } else if (usersCats.size()==1){
            return usersCats;
        } else {
            return usersCats;
        }
    }

    public List<Cat> sortCats(){
        return catRepository.findAllByOrderByRatingDesc();
    }

    public void IncreaseRating(Cat cat, String username){
        cat.setRating(cat.getRating()+1);
        catRepository.save(cat);
        User currentUser = userRepository.findOneByUserName(username);
        List<Cat> usersCats = currentUser.getUserCat();
//        usersCats.removeAll(cats);
//        userRepository.save(currentUser);
    }

}
