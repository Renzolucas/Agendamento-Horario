package com.lucas.agendamento_horario.services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        servico = agendamento.getServicos();
        LocalDateTime horaAgendada = agendamento.getDataHoraAgendamento();

        LocalDateTime limiteInicio = horaAgendada.minusMinutes(59);
        LocalDateTime limiteFim = horaAgendada.plusHours(1);

        boolean horarioOcupado = agendamentoRepository.existsByServicosAndDataHoraAgendamentoBetween(servico, limiteInicio, limiteFim);

        if(horarioOcupado){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horario Indisponivel!!");
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
    //ALTERAR DADOS DO CLIENTE
    public AgendamentoEntity alterarAgendamento(AgendamentoEntity agendamento, String cliente, LocalDateTime horaAgendada){
        AgendamentoEntity agenda = agendamentoRepository.findByAndClienteAndDataHoraAgendamento(cliente, horaAgendada);

        if(agenda == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado!!");
        }
        agendamento.setId(agenda.getId());
        
        return agendamentoRepository.save(agendamento);
    }
}

