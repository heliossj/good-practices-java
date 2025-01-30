package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService abrigoService;
    @Autowired
    private PetService petService;


    @GetMapping
    public ResponseEntity<List<AbrigoDto>> listar() {
        List<AbrigoDto> abrigos = abrigoService.listarAbrigos();
        return ResponseEntity.ok(abrigos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarAbrigoDto dto) {
        try{
            abrigoService.cadastrar(dto);
            return ResponseEntity.ok().build();
        } catch (ValidationException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<PetDto>> listarPets(@PathVariable String idOuNome) {
        try{
            List<PetDto> petsDoAbrigo = abrigoService.listarPetsAbrigo(idOuNome);
            return ResponseEntity.ok(petsDoAbrigo);
        } catch (ValidacaoException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid CadastroPetDto dto) {
        try{
            Abrigo abrigo = abrigoService.carregarAbrigo(idOuNome);
            petService.cadastrarPet(abrigo, dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception){
            return ResponseEntity.notFound().build();
        }
    }

}
