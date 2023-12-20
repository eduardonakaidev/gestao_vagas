package br.com.eduardonakai.gestao_vagas.modules.candidate.repositorys;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eduardonakai.gestao_vagas.modules.candidate.models.CandidateEntity;



public interface CandidateRepository extends JpaRepository<CandidateEntity,UUID>{
    Optional<CandidateEntity> findByUsernameOrEmail(String username,String email);
}
