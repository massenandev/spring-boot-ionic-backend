package com.example.demo.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.cursomc.domain.Categoria;
import com.example.demo.cursomc.domain.Cidade;
import com.example.demo.cursomc.domain.Cliente;
import com.example.demo.cursomc.domain.Endereco;
import com.example.demo.cursomc.domain.Estado;
import com.example.demo.cursomc.domain.Pagamento;
import com.example.demo.cursomc.domain.PagamentoBoleto;
import com.example.demo.cursomc.domain.PagamentoCartao;
import com.example.demo.cursomc.domain.Pedido;
import com.example.demo.cursomc.domain.Produto;
import com.example.demo.cursomc.domain.enums.EstadoPagamento;
import com.example.demo.cursomc.domain.enums.TipoCliente;
import com.example.demo.cursomc.repository.CategoriaRepository;
import com.example.demo.cursomc.repository.CidadeRepository;
import com.example.demo.cursomc.repository.ClienteRepository;
import com.example.demo.cursomc.repository.EnderecoRepository;
import com.example.demo.cursomc.repository.EstadoRepository;
import com.example.demo.cursomc.repository.PagamentoRepository;
import com.example.demo.cursomc.repository.PedidoRepository;
import com.example.demo.cursomc.repository.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlância", e1);
		Cidade c2 = new Cidade(null, "São Paulo", e2);
		Cidade c3 = new Cidade(null, "Campinas", e2);

		e1.getCidades().addAll(Arrays.asList(c1));
		e2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(e1, e2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "123456789", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("2736323", "93838393"));

		Endereco end1 = new Endereco(null, "Rua flores", "300", "Apto 303", "Jardim", "3822034", cli1, c1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "3877012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");

		Pedido pe1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido pe2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2);

		Pagamento pag1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, pe1, 6);
		pe1.setPagamento(pag1);

		Pagamento pag2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, pe2, sdf.parse("20/10/2017 00:00"), null);
		pe2.setPagamento(pag2);

		cli1.getPedidos().addAll(Arrays.asList(pe1, pe2));
		
		pedidoRepository.saveAll(Arrays.asList(pe1, pe2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));

	}

}
