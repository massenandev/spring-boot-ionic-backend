package com.example.demo.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cursomc.domain.Produto;
import com.example.demo.cursomc.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository prodRepository;
	
	public Produto find(Integer id){
		Optional<Produto> obj = prodRepository.findById(id);
		return obj.orElse(null);
	}
}
