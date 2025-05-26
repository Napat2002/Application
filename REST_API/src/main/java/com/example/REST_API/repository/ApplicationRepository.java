package com.example.REST_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.REST_API.models.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
