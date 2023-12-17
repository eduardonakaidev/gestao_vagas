package br.com.eduardonakai.gestao_vagas.exceptions;

public class UserFoundExcepetion extends RuntimeException{
    public UserFoundExcepetion(){
        super("Usuário já existe");
    }
}
