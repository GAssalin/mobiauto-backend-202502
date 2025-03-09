package br.com.mobiauto.pagamentos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mobiauto.pagamentos.dto.PagamentoDto;
import br.com.mobiauto.pagamentos.model.StatusPagamento;
import br.com.mobiauto.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<Page<PagamentoDto>> listarTodos(Pageable paginacao) {
        Page<PagamentoDto> pagamentos = pagamentoService.listarTodos(paginacao);
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> obterPorId(@PathVariable Long id) {
        PagamentoDto pagamento = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamento);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> criarPagamento(@RequestBody @Valid PagamentoDto dto) {
        PagamentoDto pagamentoCriado = pagamentoService.criarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizarPagamento(@PathVariable Long id, @RequestBody @Valid PagamentoDto dto) {
        PagamentoDto pagamentoAtualizado = pagamentoService.atualizarPagamento(id, dto);
        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPagamento(@PathVariable Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarPagamento(@PathVariable Long id) {
        pagamentoService.confirmarPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alteraStatus(@PathVariable Long id, @RequestParam StatusPagamento status) {
        pagamentoService.alteraStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
