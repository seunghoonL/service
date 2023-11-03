package org.delivery.api.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class StoreService {


    private final StoreRepository storeRepository;


    // 유효한 스토어 가져오기
    public StoreEntity getStoreWithThrow(Long id){
        var entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


    // 스토어 등록
    public StoreEntity register(StoreEntity storeEntity){

        StoreEntity entity = Optional.ofNullable(storeEntity).map(it -> {

            it.setStar(0);
            it.setStatus(StoreStatus.REGISTERED);

            return storeRepository.save(it);
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

        return entity;
    }


    // 카테고리로 스토어 검색
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory){

        List<StoreEntity> storeEntities = Optional.ofNullable(storeCategory).map(it -> {

            return storeRepository.findAllByStatusAndCategoryOrderByStarDesc(StoreStatus.REGISTERED, storeCategory);
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

        return storeEntities;
    }



    // 전체 스토어 검색
    public List<StoreEntity> searchAll(){
        List<StoreEntity> storeEntities = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);

        return storeEntities;
    }


}
