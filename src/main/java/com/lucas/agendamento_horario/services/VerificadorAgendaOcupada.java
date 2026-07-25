package com.lucas.agendamento_horario.services;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.lucas.agendamento_horario.infrastructure.repository.AgendaentoRepository;
import com.lucas.agendamento_horario.infrastructure.repository.entity.AgendamentoEntity;
import com.lucas.agendamento_horario.infrastructure.repository.entity.ServicosSalao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class VerificadorAgendaOcupada {
    private final AgendaentoRepository agendamentoRepository;

    public void verificarHorarioOcupado(AgendamentoEntity agendamento, ServicosSalao servico){
        System.out.println(agendamento.getId());
        if(agendamento.getId() != null) {
            AgendamentoEntity agendamentoExistente = agendamentoRepository.findById(agendamento.getId()).orElse(null);
            if (agendamentoExistente != null && agendamentoExistente.getDataHoraAgendamento().equals(agendamento.getDataHoraAgendamento())) {
                // Se o horário agendado for o mesmo do agendamento existente, não é necessário verificar a disponibilidade
                return;
            }
        }
        LocalDateTime horaAgendada = agendamento.getDataHoraAgendamento();

        //DETERMINAMOS UM LIMITE DE 1 HORA PARA CADA SERVIÇO, PARA VERIFICAR SE O HORÁRIO ESTÁ OCUPADO
        LocalDateTime limiteInicio = horaAgendada.minusMinutes(59);
        LocalDateTime limiteFim = horaAgendada.plusHours(1);

        //NA BUSCA, SE FOR TRUE(EXISTIR) ENTÃO O HORÁRIO ESTÁ OCUPADO, SE FOR FALSE, ENTÃO O HORÁRIO ESTÁ DISPONÍVEL
        boolean horarioOcupado = agendamentoRepository.existsByServicosAndDataHoraAgendamentoBetween(servico, limiteInicio, limiteFim);
        //CASO O HORARIO ESTEJA OCUPADO, LANÇAMOS UMA EXCEÇÃO COM STATUS 409(CONFLICT) E UMA MENSAGEM DE ERRO
        if(horarioOcupado){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horario Indisponivel!!");
        } 

    }
}

