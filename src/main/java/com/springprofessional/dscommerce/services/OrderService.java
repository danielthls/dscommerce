package com.springprofessional.dscommerce.services;

import com.springprofessional.dscommerce.dto.OrderDTO;
import com.springprofessional.dscommerce.dto.OrderItemDTO;
import com.springprofessional.dscommerce.entities.Order;
import com.springprofessional.dscommerce.entities.OrderItem;
import com.springprofessional.dscommerce.entities.OrderStatus;
import com.springprofessional.dscommerce.entities.Product;
import com.springprofessional.dscommerce.repositories.OrderItemRepository;
import com.springprofessional.dscommerce.repositories.OrderRepository;
import com.springprofessional.dscommerce.repositories.ProductRepository;
import com.springprofessional.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    /*
    @Transactional(readOnly = true)
    public Page<OrderMinDTO> findAll(String name, Pageable pageable) {
        return repository.searchByName(name, pageable).map(x -> new OrderMinDTO(x));
    }
    */

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setClient(userService.authenticated());

        for (OrderItemDTO itemDTO: dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }

    /*
    @Transactional
    public OrderDTO update(Long id, OrderDTO dto) {
        try {
            Order entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new OrderDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    /*
    private void copyDtoToEntity(OrderDTO dto, Order entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());
        entity.getCategories().clear();
        for (CategoryDTO cat: dto.getCategories()) {
            Category category = new Category();
            category.setId(cat.getId());
            entity.getCategories().add(category);
        }
    }

     */
}
