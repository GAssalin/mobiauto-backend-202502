package br.com.mobiauto.pedidos.controller;

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

import br.com.mobiauto.pedidos.dto.PedidoDTO;
import br.com.mobiauto.pedidos.model.StatusPedido;
import br.com.mobiauto.pedidos.record.AtualizarPedidoRecord;
import br.com.mobiauto.pedidos.record.CriarPedidoRecord;
import br.com.mobiauto.pedidos.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<PedidoDTO>> listarTodos(Pageable paginacao) {
        return ResponseEntity.ok(pedidoService.listarTodos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody CriarPedidoRecord dto) {
        PedidoDTO novoPedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable Long id, @RequestBody AtualizarPedidoRecord dto) {
        return ResponseEntity.ok(pedidoService.atualizarPedido(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        pedidoService.excluirPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alteraStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        pedidoService.alteraStatus(id, status);
        return ResponseEntity.noContent().build();
    }

}
