package com.signum.smartcontect.JpaRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.signum.smartcontect.Model.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact,Integer>{

    

}

