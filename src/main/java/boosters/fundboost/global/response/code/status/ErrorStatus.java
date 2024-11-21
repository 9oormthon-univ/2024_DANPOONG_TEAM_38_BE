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
    NOT_EQUAL_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN401", "토큰이 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN402", "유효하지 않은 토큰입니다."),
    //USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER400", "유저를 찾을 수 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "USER401", "인증되지 않은 유저입니다."),
    SELF_PROFILE_ACCESS_NOT_ALLOWED(HttpStatus.FORBIDDEN, "USER402", "해당 API로 본인의 마이페이지는 조회할 수 없습니다."),
    // PROJECT
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT400", "프로젝트를 찾을 수 없습니다."),
    PROJECT_ALREADY_EXISTS(HttpStatus.CONFLICT, "PROJECT401", "이미 존재하는 프로젝트입니다."),
    PROJECT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "PROJECT402", "프로젝트에 대한 접근이 금지되었습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "PROJECT403", "해당 프로젝트를 수정할 권한이 없습니다."),
    //FOLLOW
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLLOW400", "팔로우를 기록을 찾을 수 없습니다."),
    FOLLOW_YOURSELF(HttpStatus.BAD_REQUEST, "FOLLOW401", "자신과 팔로우 관계를 가질 수 없습니다."),
    // REVIEW
    REVIEW_PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW400", "유효하지 않은 프로젝트 ID입니다."),
    REVIEW_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW401", "유효하지 않은 사용자입니다."),
    REVIEW_BOOST_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW402", "해당 프로젝트에 대한 사용자의 후원 기록이 없습니다."),
    REVIEW_UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "REVIEW403", "마감후기는 프로젝트 등록자만 작성할 수 있습니다."),
    // S3
    INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "S3400", "지원하지 않는 확장자입니다."),
    // COMPANY
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMPANY400", "기업을 찾을 수 없습니다."),
    // PROPOSAL
    PROPOSAL_NOT_FOUND(HttpStatus.NOT_FOUND, "PROPOSAL400", "제안서를 찾을 수 없습니다."),
    // FILE
    FILE_CONVERT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE400", "파일링크를 JSON형식으로 변환하는 중 에러가 발생했습니다."),
    // SEARCH
    KEYWORD_LENGTH_ERROR(HttpStatus.BAD_REQUEST, "SEARCH400", "검색어는 2글자 이상이어야 합니다."),
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
