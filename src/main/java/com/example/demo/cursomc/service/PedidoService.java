package com.example.demo.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cursomc.domain.Pedido;
import com.example.demo.cursomc.repository.PedidoRepository;
import com.example.demo.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository catRepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = catRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ". Tipo: " + Pedido.class.getName()));
	}
}
