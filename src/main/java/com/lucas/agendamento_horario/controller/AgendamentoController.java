package com.lucas.agendamento_horario.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.agendamento_horario.infrastructure.repository.entity.AgendamentoEntity;
import com.lucas.agendamento_horario.infrastructure.repository.entity.ServicosSalao;
import com.lucas.agendamento_horario.services.AgendamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoEntity> salvarAgendamento(@RequestBody AgendamentoEntity agendamento, ServicosSalao servico){
        return ResponseEntity.status(201).body(agendamentoService.salvarAgendamento(agendamento, servico));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(@RequestParam LocalDateTime dataHoraAgendamento, String cliente){
        agendamentoService.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<AgendamentoEntity> buscarAgendamento(@RequestParam LocalDate dataAgendamento){
        return ResponseEntity.status(200).body(agendamentoService.buscarAgendamento(dataAgendamento));
    }

    @PutMapping
    public ResponseEntity<AgendamentoEntity> alterarAgendamento(
        @RequestBody AgendamentoEntity agendamento,
        @RequestParam String cliente, LocalDateTime dataHoraAgendamento
    ){
        return ResponseEntity.status(200).body(agendamentoService.alterarAgendamento(agendamento, cliente, dataHoraAgendamento));
    }
}
