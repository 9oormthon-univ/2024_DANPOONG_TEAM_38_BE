package boosters.fundboost.global.response.code.status;

import boosters.fundboost.global.response.code.BaseErrorCode;
import boosters.fundboost.global.response.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 기본 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    INVALID_REQUEST_INFO(HttpStatus.BAD_REQUEST, "COMMON404", "요청된 정보가 올바르지 않습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON405", "유효성 검증에 실패했습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON405", "유효하지 않은 파라미터입니다."),
    //TOKEN
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "TOKEN400", "토큰을 찾을 수 없습니다."),
    NOT_EQUAL_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN401","토큰이 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN402","유효하지 않은 토큰입니다."),
    //USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER400", "유저를 찾을 수 없습니다."),
    // PROJECT
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT400", "프로젝트를 찾을 수 없습니다."),
    PROJECT_ALREADY_EXISTS(HttpStatus.CONFLICT, "PROJECT401", "이미 존재하는 프로젝트입니다."),
    PROJECT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "PROJECT402", "프로젝트에 대한 접근이 금지되었습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "PROJECT403", "해당 프로젝트를 수정할 권한이 없습니다.");
  ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder().message(message).code(code).isSuccess(false).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
