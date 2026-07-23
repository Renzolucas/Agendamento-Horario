package com.lucas.agendamento_horario.services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.lucas.agendamento_horario.infrastructure.repository.AgendaentoRepository;
import com.lucas.agendamento_horario.infrastructure.repository.entity.AgendamentoEntity;
import com.lucas.agendamento_horario.infrastructure.repository.entity.ServicosSalao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendaentoRepository agendamentoRepository;

    //SALVAR AGENDAMENTO
    public AgendamentoEntity salvarAgendamento(AgendamentoEntity agendamento, ServicosSalao servico){
        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = horaAgendamento.plusHours(1);

        agendamento.setServicos(servico);
        
        AgendamentoEntity agendados = agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(servico, horaAgendamento, horaFim);

        if(agendados != null){
            throw new RuntimeException("HORARIO INDISPONIVEL");
        }

        return agendamentoRepository.save(agendamento);
        
    }
    //DELETAR AGENDAMENTO COM BASE NO NOME E HORA
    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente){
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

    }
        //BUSCAR TODOS OS AGENDAMENTOS
    public AgendamentoEntity buscarAgendamento(LocalDate data){
        LocalDateTime horaInicial = data.atStartOfDay();
        LocalDateTime horaFimDia = data.atTime(23,59,59);

        return agendamentoRepository.findByDataHoraAgendamentoBetween(horaInicial, horaFimDia);
    }
    
}

