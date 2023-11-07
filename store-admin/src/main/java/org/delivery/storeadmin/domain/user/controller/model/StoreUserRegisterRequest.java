package org.delivery.storeadmin.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserRegisterRequest {

    @NotBlank
    private String storeName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private StoreUserRole role;


}
