package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.exception.DuplicateResourceException;
import com.nuwandev.pos.exception.ResourceNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

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
    public PageResponse<ItemResponseDto> getItems(String q, int page, int size, String sortBy, String direction) {
        List<Item> items;
        long totalElements;

        if (q != null && !q.trim().isEmpty()) {
            String query = q.trim();
            items = itemRepository.searchItems(query, page, size, sortBy, direction);
            totalElements = itemRepository.countSearchItems(query);
        } else {
            items = itemRepository.findAll(page, size, sortBy, direction);
            totalElements = itemRepository.countItems();
        }

        return buildPageResponse(items, totalElements, page, size);
    }

    @Override
    @Transactional
    public ItemResponseDto createItem(ItemRequestDto itemRequest) {
        if (itemRepository.existsBySku(itemRequest.getSku())) {
            throw new DuplicateResourceException("Stock Alert: SKU '" + itemRequest.getSku() + "' already exists.");
        }

        Category category = categoryRepository.findById(itemRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Link Failure: Category ID " + itemRequest.getCategoryId() + " not found."));

        Item item = itemMapper.toEntity(itemRequest);
        item.setId(UUID.randomUUID());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        item.setCreatedAt(now);
        item.setUpdatedAt(now);

        Item savedItem = itemRepository.save(item);

        ItemResponseDto response = itemMapper.toDto(savedItem);
        response.setCategory(categoryMapper.toDto(category));
        return response;
    }

    @Override
    @Transactional
    public ItemResponseDto updateItem(String id, ItemRequestDto itemRequest) {
        UUID uuid = UUID.fromString(id);

        Item existingItem = itemRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Update Failed: Item " + id + " not found."));

        if (itemRepository.existsBySkuExcludeId(itemRequest.getSku(), uuid)) {
            throw new DuplicateResourceException("Conflict: SKU '" + itemRequest.getSku() + "' is assigned to another product.");
        }

        Category category = categoryRepository.findById(itemRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Link Failure: Target category not found."));

        Item updated = itemMapper.toEntity(itemRequest);
        updated.setId(uuid);
        updated.setCreatedAt(existingItem.getCreatedAt());
        updated.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Item saved = itemRepository.update(updated);

        ItemResponseDto response = itemMapper.toDto(saved);
        response.setCategory(categoryMapper.toDto(category));
        return response;
    }

    @Override
    public void deleteItem(String id) {
        UUID uuid = UUID.fromString(id);
        if (!itemRepository.existsById(uuid)) {
            throw new ResourceNotFoundException("Delete Failed: Item " + id + " does not exist.");
        }
        itemRepository.deleteById(uuid);
    }

    private PageResponse<ItemResponseDto> buildPageResponse(List<Item> content, long totalElements, int page, int size) {
        int totalPages = (size > 0) ? (int) Math.ceil((double) totalElements / size) : 0;
        List<ItemResponseDto> dtos = itemMapper.toDtoList(content);
        return new PageResponse<>(dtos, page, size, totalElements, totalPages);
    }
}