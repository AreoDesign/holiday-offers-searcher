package com.home.ans.holidays.repository;

import com.home.ans.holidays.entity.Requestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestorRepository extends JpaRepository<Requestor, UUID> {
}
