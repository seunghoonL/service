package org.delivery.storeadmin.domain.user.business;


import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreUserBusiness {

    private final StoreUserService storeUserService;

    private final StoreUserConverter storeUserConverter;

    private final StoreRepository storeRepository;

    public StoreUserResponse register(StoreUserRegisterRequest request){

        var storeName = request.getStoreName();
        var storeEntity = storeRepository
                .findFirstByNameAndStatusOrderByIdDesc(storeName, StoreStatus.REGISTERED)
                .orElseThrow(() -> new RuntimeException("Not Found StoreEntity"));

        var entity = storeUserConverter.toEntity(request, storeEntity);
        var gainEntity = storeUserService.register(entity);

        var response = storeUserConverter.toResponse(gainEntity, storeEntity);

        return response;
    }
}
