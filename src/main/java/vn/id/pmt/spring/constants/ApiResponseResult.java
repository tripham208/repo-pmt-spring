package vn.id.pmt.spring.constants;

import lombok.Getter;

@Getter
public enum ApiResponseResult {
    OK(1), ER(0);

    private final int status;

    ApiResponseResult(final int status) {
        this.status = status;
    }
}
