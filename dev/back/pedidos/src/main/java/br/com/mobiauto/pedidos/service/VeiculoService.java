package br.com.mobiauto.pedidos.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mobiauto.pedidos.dto.VeiculoDTO;
import br.com.mobiauto.pedidos.model.Veiculo;
import br.com.mobiauto.pedidos.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class VeiculoService {

	@Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<VeiculoDTO> listarTodos(Pageable paginacao) {
        return veiculoRepository.findAll(paginacao).map(v -> modelMapper.map(v, VeiculoDTO.class));
    }

    public VeiculoDTO buscarPorId(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
        return modelMapper.map(veiculo, VeiculoDTO.class);
    }

    @Transactional
    public VeiculoDTO criarVeiculo(VeiculoDTO dto) {
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);
        veiculo = veiculoRepository.save(veiculo);
        return modelMapper.map(veiculo, VeiculoDTO.class);
    }

    @Transactional
    public VeiculoDTO atualizarVeiculo(Long id, VeiculoDTO dto) {
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);
        veiculo.setId(id);
        veiculo = veiculoRepository.save(veiculo);
        return modelMapper.map(veiculo, VeiculoDTO.class);
    }

    @Transactional
    public void excluirVeiculo(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new EntityNotFoundException("Veículo não encontrado");
        }
        veiculoRepository.deleteById(id);
    }
	
}
