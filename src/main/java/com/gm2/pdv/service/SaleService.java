package com.gm2.pdv.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gm2.pdv.dto.ProductDTO;
import com.gm2.pdv.dto.ProductInfoDTO;
import com.gm2.pdv.dto.SaleDTO;
import com.gm2.pdv.dto.SaleInfoDTO;
import com.gm2.pdv.entity.ItemSale;
import com.gm2.pdv.entity.Product;
import com.gm2.pdv.entity.Sale;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.exceptions.InvalidOperationException;
import com.gm2.pdv.exceptions.NoItemException;
import com.gm2.pdv.repository.ItemSaleRepository;
import com.gm2.pdv.repository.ProductRepository;
import com.gm2.pdv.repository.SaleRepository;
import com.gm2.pdv.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final SaleRepository saleRepository;
	private final ItemSaleRepository itemSaleRepository;

	@Transactional
	public long save( SaleDTO sale ) {

//		busca usuário se não encontrar lança erro
		User user = userRepository.findById(sale.getUserId()).orElseThrow(() -> new NoItemException("Usuário não encontrado"));
//		Optional<User> op = userRepository.findById(sale.getUserId());
//		if( !op.isPresent() ) {
//			throw new NoItemException("Usuário não encontrado");
//		}
//		User user = op.get();

		Sale newSale = new Sale();
		newSale.setUser(user);
		newSale.setDate(LocalDate.now());
//		newSale.setItens(getItemSale(sale.getItems()));

		List<ItemSale> items = getItemSale(sale.getItems());
		if( items.isEmpty() ) {
			throw new NoItemException("Não pode realizar vendas sem produtos");
		}

		newSale = saleRepository.save(newSale);

		saveItemsSale(newSale, items);

		return newSale.getId();
	}

	private void saveItemsSale(Sale newSale, List<ItemSale> items) {

		for( ItemSale item : items ) {
			item.setSale(newSale);
			itemSaleRepository.save(item);
		}
	}

	private List<ItemSale> getItemSale(List<ProductDTO> products){
		List<ItemSale> list = new ArrayList<>();
		for( ProductDTO item : products ) {
			ItemSale itemSale = new ItemSale();

			Optional<Product> op = productRepository.findById(item.getId());
			if( !op.isPresent() ) {
				throw new NoItemException("Produto não encontrado");
			}
			Product product = op.get();
			itemSale.setProduct( product  );

			if( product.getQuantity() == 0 ) {
				throw new InvalidOperationException("Produto sem estoque");
			}

			int estoque = product.getQuantity() - item.getQuantity();
			if( estoque < 0 ) {
//				throw new IllegalArgumentException();
				throw new NoItemException(String.format("Quantidade (%s) em estoque insuficiente, para o produto: (%s)", 
						product.getQuantity(), itemSale.getProduct().getDescription()));
			}
			itemSale.setQuantity(item.getQuantity());

			product.setQuantity(estoque);
			productRepository.save(product);

			list.add(itemSale);
		}

		return list;

//		ou isso
//		return products.stream().map( item -> {
//			ItemSale itemSale = new ItemSale();
//			itemSale.setProduct( productRepository.getReferenceById(item.getProductId()) );
//			itemSale.setQuantity(item.getQuantity());
//
//			return itemSale;
//		} ).collect(Collectors.toList());
	}

	public List<SaleInfoDTO> getAll(){
		List<SaleInfoDTO> salesInfo = new ArrayList<>();

		List<Sale> sales = saleRepository.findAll();

		for( Sale sale : sales ) {
			salesInfo.add(getSaleInfo(sale));
		}

		return salesInfo;
	}

	public SaleInfoDTO getById( long id ){

		Sale sale = saleRepository.findById(id).orElseThrow(() -> new NoItemException("Venda não encontrada"));

		return getSaleInfo(sale);
	}

	private SaleInfoDTO getSaleInfo(Sale sale) {
		SaleInfoDTO saleInfo = new SaleInfoDTO();
		saleInfo.setId(sale.getId());
		saleInfo.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		saleInfo.setUser(sale.getUser().getName());

		List<ProductInfoDTO> products = new ArrayList<>();
		for( ItemSale item : sale.getItens() ) {
			BigDecimal tot = (item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
			ProductInfoDTO productInfo = new ProductInfoDTO(item.getId(), item.getProduct().getDescription(), 
					item.getQuantity(), item.getProduct().getPrice(), tot);
			products.add(productInfo);
		}
		saleInfo.setProducts(products);
		return saleInfo;
	}
}
