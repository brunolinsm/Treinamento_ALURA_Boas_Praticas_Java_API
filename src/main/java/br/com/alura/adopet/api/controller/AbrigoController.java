package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetNoAbrigoDto;
import br.com.alura.adopet.api.dto.PetDto;
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
    	List<AbrigoDto> listaAbrigos = abrigoService.listarAbrigos();
        return ResponseEntity.ok(listaAbrigos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDto dto) {
    	try {
    		this.abrigoService.cadastrar(dto);
    		return ResponseEntity.ok().build();
    	} catch (ValidationException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());	
    	}
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<PetDto>> listarPets(@PathVariable String idOuNome) {
    	try {
    		List<PetDto> listaPetsAbrigo = abrigoService.listarPetsAbrigo(idOuNome);
            return ResponseEntity.ok(listaPetsAbrigo);
    	} catch (ValidationException e) {
            return ResponseEntity.notFound().build();
    	}
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid CadastroPetNoAbrigoDto dto) {
    	try {
    		Abrigo abrigo = abrigoService.carregarAbrigo(idOuNome);
    		petService.cadastrarPet(dto, abrigo);
            return ResponseEntity.ok().build();
    	} catch (ValidationException e) {
            return ResponseEntity.notFound().build();
    	}
    }
}
