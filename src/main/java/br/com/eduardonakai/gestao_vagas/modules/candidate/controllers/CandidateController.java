package br.com.eduardonakai.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardonakai.gestao_vagas.exceptions.UserFoundExcepetion;
import br.com.eduardonakai.gestao_vagas.modules.candidate.models.CandidateEntity;
import br.com.eduardonakai.gestao_vagas.modules.candidate.repositorys.CandidateRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @PostMapping("/")
    public CandidateEntity create(@Valid @RequestBody CandidateEntity candidateEntity){
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
        .ifPresent((user)->{
            throw new UserFoundExcepetion();
        });


        return this.candidateRepository.save(candidateEntity);
    }

}