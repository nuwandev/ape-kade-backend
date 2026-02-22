package com.nuwandev.pos.service;

import com.nuwandev.pos.model.dto.request.ItemRequestDto;
import com.nuwandev.pos.model.dto.response.ItemResponseDto;
import com.nuwandev.pos.model.dto.response.PageResponse;

public interface ItemService {

    PageResponse<ItemResponseDto> getItems(int page, int size, String sortBy, String direction);

    ItemResponseDto createItem(ItemRequestDto itemRequest);

    ItemResponseDto updateItem(String id, ItemRequestDto itemRequest);

    void deleteItem(String id);
}
