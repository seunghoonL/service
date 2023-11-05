package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store-menu")
public class StoreMenuApiController {

    private final StoreMenuBusiness storeMenuBusiness;


    @GetMapping("/search2")
    public Api<StoreMenuResponse> searchById(@RequestParam Long id){
        var response = storeMenuBusiness.getStoreMenuWithThrow(id);
        return Api.ok(response);
    }

    @GetMapping("/search")
    public Api<List<StoreMenuResponse>> searchByStoreId(@RequestParam Long storeId){
        var response = storeMenuBusiness.getStoreMenusByStoreId(storeId);
        return Api.ok(response);
    }
}
