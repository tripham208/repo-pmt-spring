package vn.id.pmt.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import vn.id.pmt.spring.constants.ApiResponseResult;

import java.io.Serializable;

@Builder
@JsonPropertyOrder({"result","errorCode", "errorMessage", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestApiResponse<T>(
        @Nonnull ApiResponseResult result,
        @Nullable String errorCode,
        @Nullable String errorMessage,
        @Nullable T data
) implements Serializable {
}
