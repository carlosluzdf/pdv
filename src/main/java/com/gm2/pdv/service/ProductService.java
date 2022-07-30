package com.gm2.pdv.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gm2.pdv.dto.ProductDTO;
import com.gm2.pdv.dto.ProductInfoDTO;
import com.gm2.pdv.entity.Product;
import com.gm2.pdv.exceptions.NoItemException;
import com.gm2.pdv.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	private ModelMapper mapper = new ModelMapper();

	public List<ProductInfoDTO> findAll(){
		List<ProductInfoDTO> productInfoDTO = new ArrayList<>();

		List<Product> products = productRepository.findAll();
		for (Product product : products) {
			BigDecimal tot = (product.getPrice().multiply(new BigDecimal(product.getQuantity())));
			productInfoDTO.add( new ProductInfoDTO(product.getId(), product.getDescription(), product.getQuantity(), product.getPrice(), tot) );
		}

		return productInfoDTO;
	}

	public ProductInfoDTO findById(long id){

		Product product = productRepository.findById(id).orElseThrow(() -> new NoItemException("Procuto não encontrado"));
		BigDecimal tot = (product.getPrice().multiply(new BigDecimal(product.getQuantity()))); 

//		return new ProductInfoDTO(product.getId(), product.getDescription(), product.getQuantity(), product.getPrice(), tot);
		return ProductInfoDTO.builder()
				.id(product.getId())
				.description(product.getDescription())
				.quantity(product.getQuantity())
				.price(product.getPrice())
				.totalPrice(tot)
				.build();
	}

	public long insert(ProductDTO obj) {

//		if( product.getDescription() == null || product.getDescription().isEmpty() ) {
//			throw new NoItemException("Não é possível cadastrar um produto sem descrição");
//		}
//		if( product.getPrice() == null ) {
//			throw new NoItemException("Não é possível cadastrar um produto sem valor");
//		}

		Product product = mapper.map(obj, Product.class);

		return productRepository.save(product).getId();

	}

	public long update(ProductDTO obj) {

//		if( product.getDescription().isEmpty() ) {
//			throw new NoItemException("Não é possível cadastrar um produto sem descrição");
//		}
//		if( product.getPrice() == null ) {
//			throw new NoItemException("Não é possível cadastrar um produto sem valor");
//		}
		productRepository.findById(obj.getId()).orElseThrow(() -> new NoItemException("Produto não encontrado"));

		Product product = mapper.map(obj, Product.class);

		return productRepository.save(product).getId();
	}
}
