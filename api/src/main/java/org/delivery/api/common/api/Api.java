package org.delivery.api.common.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCodeIfs;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    T body;



    public static <T> Api<T> ok(T data){
        Api<T> api = new Api<>();
        api.result = Result.OK();
        api.body = data;

        return api;
    }

    public static Api<Object> ERROR(Result result){
        Api<Object> api = new Api<>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs){
        Api<Object> api = new Api<>();
        api.result = Result.ERROR(errorCodeIfs);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx){
        Api<Object> api = new Api<>();
        api.result = Result.ERROR(errorCodeIfs, tx);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs ifs, String description){
        Api<Object> api = new Api<>();
        api.result = Result.ERROR(ifs, description);
        return api;
    }
}
