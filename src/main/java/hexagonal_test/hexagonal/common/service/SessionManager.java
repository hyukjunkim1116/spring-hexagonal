package hexagonal_test.hexagonal.common.service;

import hexagonal_test.hexagonal.user.controller.request.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

    public static final String LOGIN_MEMBER = "loginMember";

    public void setSession(HttpServletRequest request, UserInfo loginUser) {
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_MEMBER, loginUser);
        session.setMaxInactiveInterval(1800);
    }

    public void invalidate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
