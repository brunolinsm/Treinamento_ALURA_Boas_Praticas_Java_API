package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Abrigo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {
    boolean existsByNomeOrTelefoneOrEmail(String nome, String telefone, String email);

    Optional<Abrigo> findByNome(String nome);
}
