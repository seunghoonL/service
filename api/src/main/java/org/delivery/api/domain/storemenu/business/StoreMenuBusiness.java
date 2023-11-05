package org.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;

    private final StoreMenuConverter storeMenuConverter;

    public StoreMenuResponse register(StoreMenuRegisterRequest registerRequest){
        var entity = storeMenuConverter.toEntity(registerRequest);
        var gainEntity = storeMenuService.register(entity);

        var response = storeMenuConverter.toResponse(gainEntity);

        return response;
    }



    public StoreMenuResponse getStoreMenuWithThrow(Long id){
        return Optional.ofNullable(id)
                .map(it -> {
                    var entity = storeMenuService.getStoreMenuWithThrow(id);
                    var response = storeMenuConverter.toResponse(entity);

                    return response;
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


    public List<StoreMenuResponse> getStoreMenusByStoreId(Long storeId){
            if (storeId != null){
                var entityList = storeMenuService.getStoreMenusByStoreId(storeId);

                List<StoreMenuResponse> responses = entityList.stream()
                        .map(entity -> storeMenuConverter.toResponse(entity))
                        .collect(Collectors.toList());
                return responses;

            }else throw new ApiException(ErrorCode.NULL_POINT);
    }

}
