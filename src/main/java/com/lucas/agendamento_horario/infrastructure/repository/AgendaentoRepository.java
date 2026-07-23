package com.lucas.agendamento_horario.infrastructure.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.agendamento_horario.infrastructure.repository.entity.AgendamentoEntity;
import com.lucas.agendamento_horario.infrastructure.repository.entity.ServicosSalao;

public interface AgendaentoRepository extends JpaRepository<AgendamentoEntity, Long>{
    AgendamentoEntity findByServicoAndDataHoraAgendamentoBetween(ServicosSalao servico, LocalDateTime dataInicio, LocalDateTime dataFim);
}
