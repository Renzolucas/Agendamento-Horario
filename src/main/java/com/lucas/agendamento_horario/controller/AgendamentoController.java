package com.lucas.agendamento_horario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.agendamento_horario.infrastructure.repository.entity.AgendamentoEntity;
import com.lucas.agendamento_horario.infrastructure.repository.entity.ServicosSalao;
import com.lucas.agendamento_horario.services.AgendamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoEntity> salvarAgendamento(@RequestBody AgendamentoEntity agendamento, ServicosSalao servico){
        return ResponseEntity.accepted().body(agendamentoService.salvarAgendamento(agendamento, servico));
    }

}
