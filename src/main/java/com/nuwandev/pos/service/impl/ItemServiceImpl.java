package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.exception.CategoryNotFoundException;
import com.nuwandev.pos.exception.ItemNotFoundException;
import com.nuwandev.pos.mapper.CategoryMapper;
import com.nuwandev.pos.mapper.ItemMapper;
import com.nuwandev.pos.model.Category;
import com.nuwandev.pos.model.Item;
import com.nuwandev.pos.model.dto.request.ItemRequestDto;
import com.nuwandev.pos.model.dto.response.ItemResponseDto;
import com.nuwandev.pos.model.dto.response.PageResponse;
import com.nuwandev.pos.repository.CategoryRepository;
import com.nuwandev.pos.repository.ItemRepository;
import com.nuwandev.pos.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public PageResponse<ItemResponseDto> getItems(int page, int size, String sortBy, String direction) {
        List<Item> items = itemRepository.findAll(page, size, sortBy, direction);
        long totalElements = itemRepository.countItems();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        List<ItemResponseDto> content = itemMapper.toDtoList(items);
        return new PageResponse<>(content, page, size, totalElements, totalPages);
    }

    @Override
    public ItemResponseDto createItem(ItemRequestDto itemRequest) {
        Category category = categoryRepository.findById(itemRequest.getCategoryId()).orElseThrow(() ->
                new CategoryNotFoundException("Category not found with id " + itemRequest.getCategoryId()));

        Item item = itemMapper.toEntity(itemRequest);
        item.setId(UUID.randomUUID());
        item.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        item.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        item.setCategoryId(itemRequest.getCategoryId());
        Item savedItem = itemRepository.save(item);

        ItemResponseDto response = itemMapper.toDto(savedItem);
        response.setCategory(categoryMapper.toDto(category));
        return response;
    }

    @Override
    public ItemResponseDto updateItem(String id, ItemRequestDto itemRequest) {
        Item item = itemRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id " + id));

        Item updated = itemMapper.toEntity(itemRequest);
        updated.setId(item.getId());
        updated.setCreatedAt(item.getCreatedAt());
        updated.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        updated.setCategoryId(itemRequest.getCategoryId());

        Category category = categoryRepository.findById(updated.getCategoryId()).orElseThrow(() ->
                new CategoryNotFoundException("Category not found with id " + updated.getCategoryId()));

        Item saved = itemRepository.update(updated);

        ItemResponseDto response = itemMapper.toDto(saved);
        response.setCategory(categoryMapper.toDto(category));
        return response;
    }

    @Override
    public void deleteItem(String id) {
        UUID uuid = UUID.fromString(id);
        itemRepository.deleteById(uuid);
    }
}
