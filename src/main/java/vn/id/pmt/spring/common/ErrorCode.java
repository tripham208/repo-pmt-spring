package vn.id.pmt.spring.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@RequiredArgsConstructor
public enum ErrorCode {
    E500("500", "Internal Server Error");

    private final String code;

    private final String message;


}
