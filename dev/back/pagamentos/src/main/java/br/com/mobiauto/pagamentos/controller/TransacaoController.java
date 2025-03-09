package br.com.mobiauto.pagamentos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mobiauto.pagamentos.dto.TransacaoDto;
import br.com.mobiauto.pagamentos.service.TransacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<Page<TransacaoDto>> listarTodos(Pageable paginacao) {
        Page<TransacaoDto> transacoes = transacaoService.listarTodos(paginacao);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDto> obterPorId(@PathVariable Long id) {
        TransacaoDto transacao = transacaoService.obterPorId(id);
        return ResponseEntity.ok(transacao);
    }

    @PostMapping
    public ResponseEntity<TransacaoDto> criarTransacao(@RequestBody @Valid TransacaoDto dto) {
        TransacaoDto transacaoCriada = transacaoService.criarTransacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoDto> atualizarTransacao(@PathVariable Long id, @RequestBody @Valid TransacaoDto dto) {
        TransacaoDto transacaoAtualizada = transacaoService.atualizarTransacao(id, dto);
        return ResponseEntity.ok(transacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTransacao(@PathVariable Long id) {
        transacaoService.excluirTransacao(id);
        return ResponseEntity.noContent().build();
    }
    
}