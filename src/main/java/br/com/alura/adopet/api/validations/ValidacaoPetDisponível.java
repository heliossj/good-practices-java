package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //poderia ser a classe @Service tbm
public class ValidacaoPetDisponível implements ValidacaoSolicitacaoAdocao{
    @Autowired
    private PetRepository petRepository;

    public void validar (SolicitacaoAdocaoDto dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());
        if (pet.getAdotado() == true) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }


}
