package br.com.eduardonakai.gestao_vagas.modules.candidate.useCases;

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

import br.com.eduardonakai.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.eduardonakai.gestao_vagas.modules.candidate.dto.AuthCandidateReponseDTO;
import br.com.eduardonakai.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;

@Service
public class AuthCandidateUseCase {
    @Value("${security.token.secret.candidate}")
    private String secretKey;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateReponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException{
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(()->{
            throw new UsernameNotFoundException("Username/password incorrect");
        });

        var passwordMathes = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if(!passwordMathes){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create()
        .withIssuer("gestvagas")
        .withSubject(candidate.getId().toString())
        .withClaim("roles", Arrays.asList("CANDIDATE"))
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .sign(algorithm);

        var authCandidateResponse =AuthCandidateReponseDTO.builder().acess_token(token).build();
        return authCandidateResponse;
    }
}
