package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreBusiness {

    private final StoreService storeService;

    private final StoreConverter storeConverter;


    public StoreResponse register(StoreRegisterRequest request){

        var entity = storeConverter.toEntity(request);
        var gainEntity = storeService.register(entity);

        var response = storeConverter.toResponse(gainEntity);

        return response;
    }


    public List<StoreResponse> searchCategory(StoreCategory category){

        var storeCategories = storeService.searchByCategory(category);


        var response = storeCategories.stream()
                .map(it -> storeConverter.toResponse(it))
                .collect(Collectors.toList());

        return response;
    }






}
