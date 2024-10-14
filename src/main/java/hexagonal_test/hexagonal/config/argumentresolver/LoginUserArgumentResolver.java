package hexagonal_test.hexagonal.config.argumentresolver;

import hexagonal_test.hexagonal.user.dto.UserInfo;
import hexagonal_test.hexagonal.util.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        // 호출하는 메서드가 Login 어노테이션이 있는가?
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        // 파라미터의 타입이 UserInfo 클래스인가?
        boolean hasMemberType = UserInfo.class.isAssignableFrom(parameter.getParameterType());
        // Login 어노테이션이 있으면서 파라미터가 UserInfo이면 true를 반환 -> resolveArgument 메서드가 실행됨.
        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 요청정보 가져오기
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        // 세션 꺼내기
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        // 로그인시에 넣어놨던 유저 객체 꺼내기
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
