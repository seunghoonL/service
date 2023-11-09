package org.delivery.api.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Transactional
@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    private static int sequence = 1;

    public StoreMenuEntity register(StoreMenuEntity storeMenuEntity){
        return Optional.ofNullable(storeMenuEntity).map(it -> {
                        storeMenuEntity.setStatus(StoreMenuStatus.REGISTERED);
                        storeMenuEntity.setSequence(sequence++);
                return  storeMenuRepository.save(storeMenuEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        var entity = storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
        return entity;
    }


    public List<StoreMenuEntity> getStoreMenusByStoreId(Long storeId){
        var storeMenus = Optional.ofNullable(storeId)
                .map(it -> storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId, StoreMenuStatus.REGISTERED))
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
        return storeMenus;
    }
}
