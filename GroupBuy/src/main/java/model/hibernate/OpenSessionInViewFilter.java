package model.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//@WebFilter(
//		urlPatterns={"/*"}
//)
public class OpenSessionInViewFilter implements Filter {
	@Override
	public void destroy() {
		
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();			//pre-processing
			chain.doFilter(req, resp);
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();	//post-processing
		} catch (Exception e) {
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			chain.doFilter(req, resp);
		}
	}
	
	private FilterConfig fileterConfig;
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.fileterConfig = config;
	}
}
