package com.pure.service.region;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RegionIdGetterFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(RegionIdGetterFilter.class);

    private static final String REGION_ID_HEADER = "X-RegionId";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String regionIdHeader = request.getHeader(REGION_ID_HEADER);

        log.debug("The user region id is {}", regionIdHeader);

        if (!StringUtils.isEmpty(regionIdHeader)) {
            RegionIdStorage.setRegionIdContext(regionIdHeader);
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
