package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TutorService {
    private TutorRepository tutorRepository;

    public void cadastrar(CadastroTutorDto dto){
        boolean jaCadastrado = tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email());
        if (jaCadastrado){
            throw new ValidacaoException("Dados já cadastrado para outro tutor!");
        }
    }

    public void atualizar(AtualizacaoTutorDto dto){
        Tutor tutor = tutorRepository.getReferenceById(dto.id());
        tutor.atualizarDados(dto);
    }


}
