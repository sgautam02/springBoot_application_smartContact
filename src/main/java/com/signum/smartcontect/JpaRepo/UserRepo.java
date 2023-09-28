package com.signum.smartcontect.JpaRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.signum.smartcontect.Model.User;
@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

    //Get User by email Query
    @Query("Select u from User u where u.email= :email")
    public User getUserByEmail(@Param("email") String email);
}
