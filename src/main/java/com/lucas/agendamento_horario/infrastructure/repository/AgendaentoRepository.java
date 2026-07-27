package com.lucas.agendamento_horario.infrastructure.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.agendamento_horario.infrastructure.repository.entity.AgendamentoEntity;
import com.lucas.agendamento_horario.infrastructure.repository.entity.ServicosSalao;

import jakarta.transaction.Transactional;

public interface AgendaentoRepository extends JpaRepository<AgendamentoEntity, Long>{
    //Verificador de existência de agendamento com base no serviço e nas data/hora incial e final. Possivel através do Between do JPA.
    //Retorna true se existir agendamento, false caso contrário.
    boolean existsByServicosAndDataHoraAgendamentoBetween(ServicosSalao servico, LocalDateTime dataInicio, LocalDateTime dataFim);

    //O @Transactional é necessário para que o spring boot consiga deletar o agendamento com base na data/hora e no cliente.
    @Transactional
    //ROTA DELETE: deleta o agendamento com base na data/hora e no cliente.
    void deleteByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento, String cliente);

    //ROTA GET: encontra o agendamento com base na data/hora
    AgendamentoEntity findByDataHoraAgendamentoBetween(LocalDateTime horaInicial, LocalDateTime horaFinal);

    //ROTA GET: encontra o agendamento com base no cliente e na data/hora
    AgendamentoEntity findByAndClienteAndDataHoraAgendamento(String cliente, LocalDateTime dataHoraAgendamento);
}
