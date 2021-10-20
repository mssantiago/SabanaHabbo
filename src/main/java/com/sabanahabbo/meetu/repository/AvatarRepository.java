package com.sabanahabbo.meetu.repository;

import com.sabanahabbo.meetu.model.Avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Long> {
 

    
}
