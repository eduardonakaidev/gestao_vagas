package br.com.eduardonakai.gestao_vagas.modules.company.repositorys;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import br.com.eduardonakai.gestao_vagas.modules.company.entities.CompanyEntity;



public interface CompanyRepository extends JpaRepository<CompanyEntity,UUID> {
     Optional<CompanyEntity> findByUsernameOrEmail(String username,String email);
     Optional<CompanyEntity> findByUsername(String username);
}
