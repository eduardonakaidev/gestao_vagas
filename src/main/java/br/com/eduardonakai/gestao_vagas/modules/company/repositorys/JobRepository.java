package br.com.eduardonakai.gestao_vagas.modules.company.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eduardonakai.gestao_vagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity,UUID>{
    
}
