package com.springprofessional.dscommerce.services;

import com.springprofessional.dscommerce.dto.OrderDTO;
import com.springprofessional.dscommerce.entities.Order;
import com.springprofessional.dscommerce.repositories.OrderRepository;
import com.springprofessional.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new OrderDTO(product);
    }

    /*
    @Transactional(readOnly = true)
    public Page<OrderMinDTO> findAll(String name, Pageable pageable) {
        return repository.searchByName(name, pageable).map(x -> new OrderMinDTO(x));
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order entity = new Order();
        copyDtoToEntity(dto, entity);

        entity = repository.save(entity);
        return new OrderDTO(entity);
    }

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
