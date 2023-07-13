package com.api.projetoibm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.projetoibm.models.Candidate;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByStatus(String status);
    List<Candidate> findByName(String name);
}