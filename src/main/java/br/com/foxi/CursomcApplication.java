package br.com.foxi;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.foxi.domain.Categoria;
import br.com.foxi.domain.Cidade;
import br.com.foxi.domain.Cliente;
import br.com.foxi.domain.Endereco;
import br.com.foxi.domain.Estado;
import br.com.foxi.domain.ItemPedido;
import br.com.foxi.domain.Pagamento;
import br.com.foxi.domain.PagamentoComBoleto;
import br.com.foxi.domain.PagamentoComCartao;
import br.com.foxi.domain.Pedido;
import br.com.foxi.domain.Produto;
import br.com.foxi.domain.enums.EstadoPagamento;
import br.com.foxi.domain.enums.TipoCliente;
import br.com.foxi.repositories.CategoriaRepository;
import br.com.foxi.repositories.CidadeRepository;
import br.com.foxi.repositories.ClienteRepository;
import br.com.foxi.repositories.EnderecoRepository;
import br.com.foxi.repositories.EstadoRepository;
import br.com.foxi.repositories.ItemPedidoRepository;
import br.com.foxi.repositories.PagamentoRepository;
import br.com.foxi.repositories.PedidoRepository;
import br.com.foxi.repositories.ProdutoRepository;

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
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cozinha");
		Categoria cat4 = new Categoria(null, "Sala");
		Categoria cat5 = new Categoria(null, "PET");
		Categoria cat6 = new Categoria(null, "Esportes");
		Categoria cat7 = new Categoria(null, "decoração");
		Categoria cat8 = new Categoria(null, "Perfumaria");
		Categoria cat9 = new Categoria(null, "Automotivos");
		Categoria cat10 = new Categoria(null, "Adulto");
		Categoria cat11 = new Categoria(null, "Infantil");
		Categoria cat12 = new Categoria(null, "GEEK");
		
		Produto p1 = new Produto(null, "computador",2000.00);
		Produto p2 = new Produto(null, "impressora",800.00);
		Produto p3 = new Produto(null, "mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8,cat9,cat10,cat11,cat12));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva","maria@gmail.com","14144435792",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27999266867","27999914223"));
		
		Endereco e1 = new Endereco(null, "rua flores", "300", "AP 201", "Jardins", "29780000", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida matos", "120", "sala 800", "centro", "29745000", cli1, c2);
		
		cli1.getEndereco().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/12/2021 19:40"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("31/12/2021 11:20"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("30/01/2022 19:40"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
		
	}
	
	
}
