package com.nuwandev.pos.mapper;

import com.nuwandev.pos.model.Item;
import com.nuwandev.pos.model.dto.request.ItemRequestDto;
import com.nuwandev.pos.model.dto.response.ItemResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toEntity(ItemRequestDto requestDto);

    ItemResponseDto toDto(Item item);

    List<ItemResponseDto> toDtoList(List<Item> items);
}
