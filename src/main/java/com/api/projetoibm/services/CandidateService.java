package com.api.projetoibm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.projetoibm.exceptions.AlreadyApproved;
import com.api.projetoibm.exceptions.AlreadyRegistered;
import com.api.projetoibm.exceptions.InvalidStatus;
import com.api.projetoibm.exceptions.NotFoundException;
import com.api.projetoibm.models.Candidate;
import com.api.projetoibm.repository.CandidateRepository;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository repository;
    
    public String gCandidateName(Long id) {
        return repository.findById(id).get().getName();
    }

    // GET Approved List - FUNCIONANDO
    public List<Candidate> gApprovedList() {
        List<Candidate> candidates = repository.findByStatus("Aprovado");
        return candidates;
    }

    // GET Candidate List - FUNCIONANDO
    public List<Candidate> gCandidatesList() {
        List<Candidate> candidates = repository.findAll();
        return candidates;
    }

    //GET Candidate Status - FUNCIONANDO
    public String gStatus(Long id) {
        Optional<Candidate> candidate = repository.findById(id);

        if (candidate.isPresent() == false) {
            throw new NotFoundException();
        }
    
        return candidate.get().getStatus();
    }
    
    // POST Register Candidate - FUNCIONANDO
    public Long pNewCandidate(String name) {
        if (repository.findByName(name).size() > 0) {
           throw new AlreadyRegistered(name);
        }
    
        return repository.save(new Candidate(name)).getId();
    }

    // POST-UPDATE Register Schedule and Alter Candidate Status - FUNCIONANDO
    public void pSchedule(Long id) {
        Optional<Candidate> candidate = repository.findById(id);

        if (candidate.isPresent() == false) {
            throw new NotFoundException();
        } 
        
        if (candidate.get().getStatus() != "Recebido") {
            throw new InvalidStatus();
        } 

        candidate.map(cand -> {
            cand.setStatus("Qualificado");
            return repository.save(cand);
        });
    }

    // POST-UPDATE Approve Candidate and Alter Candidate Status - FUNCIONANDO
    public void pApprove(Long id) {
        Optional<Candidate> candidate = repository.findById(id);

        if (candidate.isPresent() == false) {
            throw new NotFoundException();
        } 
        
        if (candidate.get().getStatus() == "Recebido") {
            throw new InvalidStatus();
        } 

        if (candidate.get().getStatus() == "Aprovado") {
            throw new AlreadyApproved(candidate.get().getName());
        }

        candidate.map(cand -> {
            cand.setStatus("Aprovado");
            return repository.save(cand);
        });
    }

    // POST-DELETE Register Candidate - FUNCIONANDO
    public void dCandidate(Long id) {
        if (repository.findById(id).isPresent() == false) {
            throw new NotFoundException();
        }
    
        repository.deleteById(id);
    }
}

