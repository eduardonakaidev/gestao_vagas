package br.com.eduardonakai.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.eduardonakai.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.eduardonakai.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.eduardonakai.gestao_vagas.modules.company.repositorys.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompanyRepository companyRepository;
    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(()->{
            throw new UsernameNotFoundException("Username/password incorrect");
        });

        var passwordMathes = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if(!passwordMathes){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expires_In = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create().withIssuer("gestvagas").withExpiresAt(Instant.now().plus(Duration.ofHours(2))).withSubject(company.getId().toString())
        .withClaim("roles",Arrays.asList("COMPANY")).sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder().acess_token(token).expires_in(expires_In.toEpochMilli()).build();
        return authCompanyResponseDTO;
    }
}
