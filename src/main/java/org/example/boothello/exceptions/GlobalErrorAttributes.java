package org.example.boothello.exceptions;

import org.example.boothello.bean.ErrorResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(
                request, options);
        Throwable error = getError(request);
        if (error instanceof DuplicateException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            map.put("error", new ErrorResponse(status, error.getMessage()));
        }
        else if (error instanceof NotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            map.put("error", new ErrorResponse(status, error.getMessage()));
            map.put("status", status.value());
        }
        return map;
    }
}
