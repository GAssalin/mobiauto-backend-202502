package br.com.mobiauto.pagamentos.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mobiauto.pagamentos.dto.PagamentoDto;
import br.com.mobiauto.pagamentos.model.Pagamento;
import br.com.mobiauto.pagamentos.model.StatusPagamento;
import br.com.mobiauto.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	public Page<PagamentoDto> listarTodos(Pageable paginacao) {
		return pagamentoRepository.findAll(paginacao).map(p -> modelMapper.map(p, PagamentoDto.class));
	}

	public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(pagamento, PagamentoDto.class);
    }


	@Transactional
	public PagamentoDto criarPagamento(PagamentoDto dto) {
		Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(StatusPagamento.PENDENTE);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);

	}

	@Transactional
	public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

	@Transactional
    public void excluirPagamento(Long id) {
		pagamentoRepository.deleteById(id);
    }

	@Transactional
    public void confirmarPagamento(Long id){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(StatusPagamento.APROVADO);
        pagamentoRepository.save(pagamento.get());
    }

	@Transactional
    public void alteraStatus(Long id, StatusPagamento status) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(status);
        pagamentoRepository.save(pagamento.get());
    }

}