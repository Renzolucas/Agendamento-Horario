package com.lucas.agendamento_horario.infrastructure.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.agendamento_horario.infrastructure.repository.entity.AgendamentoEntity;
import com.lucas.agendamento_horario.infrastructure.repository.entity.ServicosSalao;

import jakarta.transaction.Transactional;

public interface AgendaentoRepository extends JpaRepository<AgendamentoEntity, Long>{
    boolean existsByServicosAndDataHoraAgendamentoBetween(ServicosSalao servico, LocalDateTime dataInicio, LocalDateTime dataFim);

    @Transactional
    void deleteByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento, String cliente);

    AgendamentoEntity findByDataHoraAgendamentoBetween(LocalDateTime horaInicial, LocalDateTime horaFinal);

    AgendamentoEntity findByAndClienteAndDataHoraAgendamento(String cliente, LocalDateTime dataHoraAgendamento);
}
