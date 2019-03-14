package com.acasssta.chooseCats.services;

import com.acasssta.chooseCats.entities.Cat;

import java.util.List;

public interface CatService {

    List<Cat> getTwoCats (String username);;
    List<Cat> getAllCats();
    List<Cat> sortCats();
    void increaseRating(Cat cat, String username);

}
