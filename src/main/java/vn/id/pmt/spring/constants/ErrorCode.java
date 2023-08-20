package vn.id.pmt.spring.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@RequiredArgsConstructor
public enum ErrorCode {
    E403("403", "Access denied!(%s)"),
    E404("404", "(%s) not found!"),
    E500("500", "Internal Server Error"),
    E1000("1000","Please upload a (%s) file!"),
    E1001("1001","Could not upload the file: (%s). Exception message: (%s)!"),
    E1002("1002","(%s) already exists!"),
    E1004("1004","No data found! (%s)"),

    ;

    private final String code;

    private final String message;


}
