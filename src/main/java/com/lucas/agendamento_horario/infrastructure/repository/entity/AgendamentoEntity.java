package com.lucas.agendamento_horario.infrastructure.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "agendamento")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    
    private String telefoneCliente;

    private String profissional;

    @Enumerated(EnumType.STRING)
    private ServicosSalao servicos;

    private LocalDateTime dataHoraAgendamento;

    private LocalDateTime dataHoraInsercao = LocalDateTime.now();
}
