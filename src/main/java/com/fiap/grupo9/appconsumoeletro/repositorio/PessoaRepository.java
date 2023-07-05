package com.fiap.grupo9.appconsumoeletro.repositorio;

import com.fiap.grupo9.appconsumoeletro.dominio.Pessoa;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class PessoaRepository {

        static private Set<Pessoa> pessoa;

        static {

            pessoas = new LinkedHashSet<>();

            Pessoa p1 = new Pessoa();
            p1.setcpf("132132132131").setNome("Jhonny Santos").setNascimento(LocalDate.of(1995, 6, 24));

            Pessoa dep1 = new Pessoa();
            dep1.setcpf("132154354").setNome("Juriscleiton Moisés").setNascimento(LocalDate.of(2000, 12, 24));

            p1.addDependente(dep1);

            save(p1);

            save(dep1);
        }

        public Collection<Pessoa> findAll() {
            return pessoa;
        }

        public Optional<Pessoa> findById(UUID id) {
            return pessoa.stream().filter(p -> p.getId().equals(id)).findFirst();
        }

        public static Pessoa save(Pessoa p) {
            p.setId(pessoa.size() + 1L);
            pessoa.add(p);
            return p;
        }
        public Optional<Pessoa> update(Pessoa pessoaFisica) {
            Optional<Pessoa> pessoaASerBuscada = this.findById(pessoaFisica.getId());

            if(pessoaASerBuscada.isPresent()) {
                Pessoa pessoa = pessoaASerBuscada.get();
                pessoa.setCpf(pessoa.getCpf());
                pessoa.setNome(pessoa.getNome());
                pessoa.setDataNascimento(pessoa.dataNascimento());

                return Optional.of(pessoa);
            }

            return Optional.empty();
        }

        public void delete(UUID id) {
            pessoa.removeIf(p -> p.getId().equals(id));
        }

    }
