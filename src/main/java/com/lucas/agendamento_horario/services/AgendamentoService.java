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
    private final VerificadorAgendaOcupada verificador;
    

    //SALVAR AGENDAMENTO
    public AgendamentoEntity salvarAgendamento(AgendamentoEntity agendamento, ServicosSalao servico){
        
        /*PARA SETAMOS O SERVIÇO, POIS ELE É UM ENUM, SETAMOS AGORA NA LOGICA DA CRIAÇÃO POR ELE SER UM OBJETO, 
        POIS O SPRING NÃO CONSEGUE CONVERTER ELE DIRETAMENTE NO JSON, ENTÃO SETAMOS ELE AQUI NA LOGICA DE SALVAR AGENDAMENTO*/
        servico = agendamento.getServicos();

        //PASSAMOS OS PARAMETROS PARA A CLASSE COM METODO DE VERIFICAÇÃO DE HORARIO OCUPADO, SE O HORARIO ESTIVER OCUPADO, LANÇAMOS UMA EXCEÇÃO COM STATUS 409(CONFLICT) E UMA MENSAGEM DE ERRO
        verificador.verificarHorarioOcupado(agendamento, servico);

        //SE PASSAR POR TUDO, SALVAMOS NO BANCO DE DADOS E RETORNAMOS O AGENDAMENTO SALVO COM STATUS 201(CREATED)
        return agendamentoRepository.save(agendamento);
        
    }
    //DELETAR AGENDAMENTO COM BASE NO NOME E HORA
    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente){

        //BUSCAMOS CRIANDO UM NOVO METODO NO REPOSITORY COM BASE NO NOME DO CLIENTE E HORA AGENDADA, SE NÃO ENCONTRAR LANÇA UMA EXCEÇÃO COM STATUS 404(NOT FOUND) E UMA MENSAGEM DE ERRO
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

    }
    //BUSCAR TODOS OS AGENDAMENTOS COM BASE NA DATA, MES E ANO, RETORNANDO UMA LISTA DE AGENDAMENTOS
    public AgendamentoEntity buscarAgendamento(LocalDate data){

        //CRIAMOS 2 VARIAVEIS PARA O FILTRO, UMA PARA O INICIO DO DIA, 00:00:00 E OUTRA PARA O FINAL DO DIA, 23:59:59
        LocalDateTime horaInicial = data.atStartOfDay();
        LocalDateTime horaFimDia = data.atTime(23,59,59);

        //BUSCAMOS NO REPOSITORIO COM BASE NAS VARIAVEIS DE FILTRO, RETORNANDO UMA LISTA DE AGENDAMENTOS(BETWEEN SIGNIFICA ENTRE, OU SEJA, BUSCA TODOS OS AGENDAMENTOS ENTRE O INICIO E O FIM DO DIA)
        return agendamentoRepository.findByDataHoraAgendamentoBetween(horaInicial, horaFimDia);
    }
    //ALTERAR DADOS DO CLIENTE
    public AgendamentoEntity alterarAgendamento(AgendamentoEntity agendamento, String cliente, LocalDateTime horaAgendada){
        //BUSCA NO REPOSITORY COM BASE NO NOME DO CLIENTE E HORA AGENDADA, SE NÃO ENCONTRAR LANÇA UMA EXCEÇÃO COM STATUS 404(NOT FOUND) E UMA MENSAGEM DE ERRO
        AgendamentoEntity agenda = agendamentoRepository.findByAndClienteAndDataHoraAgendamento(cliente, horaAgendada);

        //VERIFICA SE O AGENDAMENTO EXISTE, SE NAO LANÇA UMA EXCEÇÃO COM STATUS 404(NOT FOUND) E UMA MENSAGEM DE ERRO
        if(agenda == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado!!");
        }

        //SETAMOS O ID DO AGENDAMENTO COMPATIVEL DE ACORDO COM A BUSCA, PARA QUE O JPA POSSA ATUALIZAR O REGISTRO CORRETAMENTE
        agendamento.setId(agenda.getId());

        //SOLUÇÃO PARA A VERIFICAÇÃO DE HORARIO OCUPADO FUNCIONAR
        ServicosSalao servico = agendamento.getServicos();

        //VERIFICAMOS SE O HORARIO ESTÁ OCUPADO, SE ESTIVER LANÇAMOS UMA EXCEÇÃO COM STATUS 409(CONFLICT) E UMA MENSAGEM DE ERRO
        verificador.verificarHorarioOcupado(agendamento, servico);

        //SE PASSAR POR TUDO ISSO SETAMOS A DATA ATUAL A INSERÇÃO E SALVAMOS O AGENDAMENTO NO REPOSITORY, RETORNANDO O AGENDAMENTO ATUALIZADO
        agendamento.setDataHoraInsercao(LocalDateTime.now());
        return agendamentoRepository.save(agendamento);
    }
}

