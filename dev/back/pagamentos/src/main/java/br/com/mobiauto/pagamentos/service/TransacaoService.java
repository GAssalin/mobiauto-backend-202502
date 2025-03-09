package br.com.mobiauto.pagamentos.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mobiauto.pagamentos.dto.TransacaoDto;
import br.com.mobiauto.pagamentos.model.Pagamento;
import br.com.mobiauto.pagamentos.model.Transacao;
import br.com.mobiauto.pagamentos.repository.PagamentoRepository;
import br.com.mobiauto.pagamentos.repository.TransacaoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<TransacaoDto> listarTodos(Pageable paginacao) {
        return transacaoRepository.findAll(paginacao).map(t -> modelMapper.map(t, TransacaoDto.class));
    }

    public TransacaoDto obterPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        return modelMapper.map(transacao, TransacaoDto.class);
    }

    @Transactional
    public TransacaoDto criarTransacao(TransacaoDto dto) {
        Pagamento pagamento = pagamentoRepository.findById(dto.getPagamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));

        Transacao transacao = modelMapper.map(dto, Transacao.class);
        transacao.setPagamento(pagamento);
        transacao.setCodigoTransacao(UUID.randomUUID().toString());
        
        transacao = transacaoRepository.save(transacao);

        return modelMapper.map(transacao, TransacaoDto.class);
    }

    @Transactional
    public TransacaoDto atualizarTransacao(Long id, TransacaoDto dto) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        transacao.setValor(dto.getValor());
        transacaoRepository.save(transacao);

        return modelMapper.map(transacao, TransacaoDto.class);
    }

    @Transactional
    public void excluirTransacao(Long id) {
        if (!transacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Transação não encontrada");
        }
        transacaoRepository.deleteById(id);
    }
    
}
