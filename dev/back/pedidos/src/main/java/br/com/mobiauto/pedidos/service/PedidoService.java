package br.com.mobiauto.pedidos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mobiauto.pedidos.dto.CriarPedidoDTO;
import br.com.mobiauto.pedidos.dto.PedidoDTO;
import br.com.mobiauto.pedidos.model.Pedido;
import br.com.mobiauto.pedidos.model.PedidoVeiculo;
import br.com.mobiauto.pedidos.model.StatusPedido;
import br.com.mobiauto.pedidos.model.Veiculo;
import br.com.mobiauto.pedidos.repository.PedidoRepository;
import br.com.mobiauto.pedidos.repository.PedidoVeiculoRepository;
import br.com.mobiauto.pedidos.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private VeiculoRepository veiculoRepository;
    
    @Autowired
    private PedidoVeiculoRepository pedidoVeiculoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PedidoDTO> listarTodos(Pageable paginacao) {
        return pedidoRepository.findAll(paginacao).map(p -> modelMapper.map(p, PedidoDTO.class));
    }

    public PedidoDTO obterPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Transactional
    public PedidoDTO criarPedido(CriarPedidoDTO dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        List<PedidoVeiculo> pedidoVeiculos = dto.getVeiculosIds().stream()
                .map(veiculoId -> {
                    Veiculo veiculo = veiculoRepository.findById(veiculoId)
                            .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
                    PedidoVeiculo pedidoVeiculo = new PedidoVeiculo();
                    pedidoVeiculo.setPedido(pedidoSalvo);
                    pedidoVeiculo.setVeiculo(veiculo);
                    return pedidoVeiculo;
                })
                .collect(Collectors.toList());

        pedidoVeiculoRepository.saveAll(pedidoVeiculos);
        return modelMapper.map(pedidoSalvo, PedidoDTO.class);
    }

    @Transactional
    public PedidoDTO atualizarPedido(Long id, PedidoDTO dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        pedido.setId(id);
        pedido = pedidoRepository.save(pedido);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Transactional
    public void excluirPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public void confirmarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        pedido.setStatus(StatusPedido.PAGO);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void alteraStatus(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        pedido.setStatus(status);
        pedidoRepository.save(pedido);
    }
    
}