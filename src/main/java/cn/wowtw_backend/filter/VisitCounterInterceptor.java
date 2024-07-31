package cn.wowtw_backend.filter;

import cn.wowtw_backend.service.VisitCountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class VisitCounterInterceptor implements HandlerInterceptor {

    private final VisitCountService visitCountService;

    @Autowired
    public VisitCounterInterceptor(VisitCountService visitCountService) {
        this.visitCountService = visitCountService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        visitCountService.incrementVisitCount();
        return true;
    }
}
