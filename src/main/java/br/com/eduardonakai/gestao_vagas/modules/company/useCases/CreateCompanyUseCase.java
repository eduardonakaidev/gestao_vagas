package br.com.eduardonakai.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.eduardonakai.gestao_vagas.exceptions.UserFoundExcepetion;
import br.com.eduardonakai.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.eduardonakai.gestao_vagas.modules.company.repositorys.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    public CompanyEntity execute(CompanyEntity companyEntity){
        
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((user)->{
            throw new UserFoundExcepetion();
        });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        
        return this.companyRepository.save(companyEntity);
    }
}
