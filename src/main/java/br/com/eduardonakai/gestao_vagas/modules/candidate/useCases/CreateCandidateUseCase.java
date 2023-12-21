package br.com.eduardonakai.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.eduardonakai.gestao_vagas.exceptions.UserFoundExcepetion;
import br.com.eduardonakai.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.eduardonakai.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
        .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundExcepetion();
                });
                var password = passwordEncoder.encode(candidateEntity.getPassword());
                candidateEntity.setPassword(password);
        return this.candidateRepository.save(candidateEntity);
    }
}
