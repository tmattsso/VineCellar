package com.thomas.winecellar.ui;

import javax.servlet.annotation.WebFilter;

import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 *
 */
@WebFilter(urlPatterns = "/VAADIN/*")
public class CachingAndGzippingFilter extends SimplePageCachingFilter {

}