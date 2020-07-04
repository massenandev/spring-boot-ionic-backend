package com.example.demo.cursomc.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.cursomc.domain.Cliente;
import com.example.demo.cursomc.dto.CategoriaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.cursomc.domain.Categoria;
import com.example.demo.cursomc.repository.CategoriaRepository;
import com.example.demo.cursomc.service.exceptions.DataIntegrityException;
import com.example.demo.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository catRepository;
	
	public Categoria find(Integer id){
		Optional<Categoria> obj = catRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ". Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return catRepository.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return catRepository.save(newObj);
	}

	public void deleteById(Integer id) {
		find(id);
		try {
			catRepository.deleteById(id);
		} catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
			
		}
	}
	
	public List<Categoria> findAll(){
		return catRepository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return catRepository.findAll(pageRequest);
	}

	public Categoria fromDto(CategoriaDto objDto){
		return new Categoria(objDto.getId(), objDto.getNome());
	}

	private void updateData(Categoria newObj, Categoria obj){
		newObj.setNome(obj.getNome());
	}
}
