package com.lucas.agendamento_horario.services;

import org.springframework.stereotype.Service;

import com.lucas.agendamento_horario.infrastructure.repository.AgendaentoRepository;
import com.lucas.agendamento_horario.infrastructure.repository.entity.AgendamentoEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendaentoRepository agendamentoRepository;

    public AgendamentoEntity salvarAgendamento(AgendamentoEntity agendamento){
        
    }
}
