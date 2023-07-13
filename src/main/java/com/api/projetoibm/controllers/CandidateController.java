package com.api.projetoibm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.projetoibm.dto.CandidateDTO;
import com.api.projetoibm.models.Candidate;
import com.api.projetoibm.services.CandidateService;


@RestController
@RequestMapping("api/v1/hiring/")
@CrossOrigin(value = "*")
public class CandidateController {
    @Autowired
    private CandidateService service;

    @GetMapping("/approved")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Candidate> getApprovedList() {
        List<Candidate> approvedList = service.gApprovedList();
        return approvedList;
    }

    @GetMapping("/candidates")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Candidate> getCandidatesList() {
        List<Candidate> candidatesList = service.gCandidatesList();
        return candidatesList;
    }

    @GetMapping("/status/candidate/{id}")
    @ResponseStatus (code = HttpStatus.OK)
    public ResponseEntity<String> getCandidateStatus(@PathVariable Long id) {
        String candidateStatus = service.gStatus(id);
        String candidateName = service.gCandidateName(id);

        if (candidateStatus != null) {
            return ResponseEntity.ok().body("O status de " + candidateName + " é: " + candidateStatus);
        } else {
            return ResponseEntity.notFound().build();
        }

        //return "O status de " + candidateName + " é: " + candidateStatus;
    }

    @PostMapping("/schedule")
    @ResponseStatus(code = HttpStatus.OK)
    public void postSchedule(@RequestBody CandidateDTO req) {
        service.pSchedule(req.codCandidato());
    } 

    @PostMapping("/disqualify")
    @ResponseStatus(code = HttpStatus.OK)
    public void disqualifyCandidate(@RequestBody CandidateDTO req) {
        service.dCandidate(req.codCandidato());
    }

    @PostMapping("/start")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long registerCandidate(@RequestBody CandidateDTO req) {
        Long candidateId = service.pNewCandidate(req.nome());

        return candidateId;
    }

    @PostMapping("/approve")
    @ResponseStatus(code = HttpStatus.OK)
    public void postApprove(@RequestBody CandidateDTO req) {
        service.pApprove(req.codCandidato());
    }
}
