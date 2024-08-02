package com.test.rpinfotest.controllers;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.rpinfotest.entity.OrdemDeServico;
import com.test.rpinfotest.entity.Anotacao;
import com.test.rpinfotest.enums.OrdemDeServicoStatus;
import com.test.rpinfotest.repository.OrdemDeServicoRepository;

@RestController
public class OrdemDeServicoController {

    private final OrdemDeServicoRepository repository;

    public OrdemDeServicoController(OrdemDeServicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/ordensDeServico")
    public List<OrdemDeServico> listAll() {
        return this.repository.findAll();
    }

    @GetMapping("/ordensDeServico/pendentes")
    public List<OrdemDeServico> buscarOrdemsDeServicoPendentes() {
        return this.repository.buscarOrdemsDeServicoPendentes();
    }

    @GetMapping("/ordemDeServico/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        var ordemDeServico = this.repository.findById(id);

        if (ordemDeServico.isPresent()) {
            return new ResponseEntity<>(ordemDeServico.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Ordem de serviço não encontrada.", HttpStatus.OK);
    }

    @PostMapping("/ordemDeServico")
    public OrdemDeServico novaOrdemDeServico(@RequestBody OrdemDeServico novaOrdemDeServico) {
        return repository.save(novaOrdemDeServico);
    }

    @PutMapping("/ordemDeServico/{id}/iniciarManutencao")
    public ResponseEntity<Object> iniciarManutencao(@PathVariable Long id) {
        var resultadoPesquisa = repository.findById(id);

        if (resultadoPesquisa.isPresent()) {
            var ordemDeServico = resultadoPesquisa.get();
            
            if (ordemDeServico.getStatus() == OrdemDeServicoStatus.PENDENTE.getDescricao()) {
                
                ordemDeServico.setStatus(OrdemDeServicoStatus.EM_ANDAMENTO);
                ordemDeServico.setInicioDaManutencao(Instant.now());

                return new ResponseEntity<>(this.repository.save(ordemDeServico), HttpStatus.OK);
                
            } else {
                return new ResponseEntity<>("Essa ordem de serviço não pode ser iniciada pois não possui o status: \"Pendente\"", HttpStatus.OK);
            }

        }
        return new ResponseEntity<>("Ordem de serviço não encontrada.", HttpStatus.OK);
    }

    @PutMapping("/ordemDeServico/{id}/adicionarAnotacao")
    public ResponseEntity<Object> adicionarAnotacao(@RequestBody Anotacao anotacao, @PathVariable Long id) {
        var resultadoDaPesquisa = repository.findById(id);

        if (resultadoDaPesquisa.isPresent()) {
            var ordemDeServico = resultadoDaPesquisa.get();
            anotacao.setData(Instant.now());

            ordemDeServico.getAnotacoes().add(anotacao);

            return new ResponseEntity<>(this.repository.save(ordemDeServico), HttpStatus.OK);
        }

        return new ResponseEntity<>("Ordem de serviço não encontrada.", HttpStatus.OK);
    }

    @PutMapping("/ordemDeServico/{id}/finalizarManutencao")
    public ResponseEntity<Object> finalizarManutencao(@RequestBody Anotacao anotacao, @PathVariable Long id) {
        var resultado = repository.findById(id);

        if (resultado.isPresent()) {
            var ordemDeServico = resultado.get();
            ordemDeServico.setStatus(OrdemDeServicoStatus.CONCLUIDO);
            ordemDeServico.setFimDaManutencao(Instant.now());
  
            anotacao.setData(Instant.now());

            ordemDeServico.getAnotacoes().add(anotacao);
            
            return new ResponseEntity<>(this.repository.save(ordemDeServico), HttpStatus.OK);
        }

       return new ResponseEntity<>("Ordem de serviço não encontrada.", HttpStatus.OK);
    }

    @DeleteMapping("/ordemDeServico/{id}")
    void deletarOrdemDeServico(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
