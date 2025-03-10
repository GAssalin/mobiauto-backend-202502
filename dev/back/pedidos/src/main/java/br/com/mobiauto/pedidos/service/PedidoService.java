package br.com.mobiauto.pedidos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mobiauto.pedidos.dto.PedidoDTO;
import br.com.mobiauto.pedidos.dto.VeiculoDTO;
import br.com.mobiauto.pedidos.model.Pedido;
import br.com.mobiauto.pedidos.model.PedidoVeiculo;
import br.com.mobiauto.pedidos.model.StatusPedido;
import br.com.mobiauto.pedidos.model.Veiculo;
import br.com.mobiauto.pedidos.record.AtualizarPedidoRecord;
import br.com.mobiauto.pedidos.record.CriarPedidoRecord;
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
		Page<PedidoDTO> lista = pedidoRepository.findAll(paginacao).map(p -> modelMapper.map(p, PedidoDTO.class));

		lista.forEach(pedidoDTO -> {
			List<VeiculoDTO> veiculosAtualizados = pedidoDTO.getVeiculos().stream().map(veiculoDTO -> {
				return veiculoRepository.findById(veiculoDTO.getId())
						.map(veiculo -> modelMapper.map(veiculo, VeiculoDTO.class)).orElse(veiculoDTO);
			}).collect(Collectors.toList());

			pedidoDTO.setVeiculos(veiculosAtualizados);
		});

		return lista;
	}

	public PedidoDTO obterPorId(Long id) {
		Pedido pedido = Optional.ofNullable(pedidoRepository.findPedidoComVeiculos(id))
		        .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
		
		PedidoDTO dto = modelMapper.map(pedido, PedidoDTO.class);
		List<VeiculoDTO> veiculoDTOs = new ArrayList<VeiculoDTO>();
		pedido.getPedidoVeiculos().forEach(pedidoVeiculo -> {
			veiculoDTOs.add(modelMapper.map(veiculoRepository.findById(pedidoVeiculo.getVeiculo().getId()), VeiculoDTO.class));
		});
		dto.setVeiculos(veiculoDTOs);
		return dto;
	}

	@Transactional
	public PedidoDTO criarPedido(CriarPedidoRecord dto) {
		Pedido pedido = modelMapper.map(dto, Pedido.class);
		pedido.setId(null);
		pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
		Pedido pedidoSalvo = pedidoRepository.save(pedido);

		List<PedidoVeiculo> pedidoVeiculos = dto.veiculosIds().stream().map(veiculoId -> {
			Veiculo veiculo = veiculoRepository.findById(veiculoId)
					.orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
			PedidoVeiculo pedidoVeiculo = new PedidoVeiculo();
			pedidoVeiculo.setPedido(pedidoSalvo);
			pedidoVeiculo.setVeiculo(veiculo);
			return pedidoVeiculo;
		}).collect(Collectors.toList());

		pedidoVeiculoRepository.saveAll(pedidoVeiculos);
		return modelMapper.map(pedidoSalvo, PedidoDTO.class);
	}

	@Transactional
	public PedidoDTO atualizarPedido(Long id, AtualizarPedidoRecord dto) {
		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

		pedido.setClienteId(Optional.ofNullable(dto.clienteId()).orElse(pedido.getClienteId()));
		pedido.setStatus(Optional.ofNullable(dto.status()).orElse(pedido.getStatus()));
		pedido.setTipo(Optional.ofNullable(dto.tipo()).orElse(pedido.getTipo()));

		Pedido pedidoFinal = pedidoRepository.save(pedido);

		List<VeiculoDTO> veiculosDTO = dto.veiculosIds().stream().map(veiculoId -> {
			Veiculo veiculo = veiculoRepository.findById(veiculoId)
					.orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + veiculoId));

			return modelMapper.map(veiculo, VeiculoDTO.class);
		}).collect(Collectors.toList());

		pedidoVeiculoRepository.deleteByPedidoId(pedidoFinal.getId());

		veiculosDTO.forEach(veiculoDTO -> {
			PedidoVeiculo pedidoVeiculo = new PedidoVeiculo();
			pedidoVeiculo.setPedido(pedidoFinal);
			pedidoVeiculo.setVeiculo(modelMapper.map(veiculoDTO, Veiculo.class));
			
			pedidoVeiculoRepository.save(pedidoVeiculo);
		});

		return obterPorId(pedidoFinal.getId());
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