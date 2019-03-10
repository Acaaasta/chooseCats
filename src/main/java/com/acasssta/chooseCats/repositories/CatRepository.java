package com.acasssta.chooseCats.repositories;

import com.acasssta.chooseCats.entities.Cat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends CrudRepository<Cat,Long> {
    List<Cat> findAllByOrderByRatingDesc();
}
