package com.fredtm.api.security;

public class RequestTemperingFilter {//implements Filter {

//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		final HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HeaderMapRequestWrapper wrapper = new HeaderMapRequestWrapper(httpRequest);
//		wrapper.addHeader("TrustedAccountId", "0");
//		chain.doFilter(wrapper, response);
//	}
//
//	@Override
//	public void destroy() {
//
//	}
//
//	public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
//		/**
//		 * construct a wrapper for this request
//		 * 
//		 * @param request
//		 */
//		public HeaderMapRequestWrapper(HttpServletRequest request) {
//			super(request);
//		}
//
//		private Map<String, String> headerMap = new HashMap<String, String>();
//
//		/**
//		 * add a header with given name and value
//		 * 
//		 * @param name
//		 * @param value
//		 */
//		public void addHeader(String name, String value) {
//			headerMap.put(name, value);
//		}
//
//		@Override
//		public String getHeader(String name) {
//			String headerValue = super.getHeader(name);
//			if (headerMap.containsKey(name)) {
//				headerValue = headerMap.get(name);
//			}
//			return headerValue;
//		}
//
//		/**
//		 * get the Header names
//		 */
//		@Override
//		public Enumeration<String> getHeaderNames() {
//			List<String> names = Collections.list(super.getHeaderNames());
//			for (String name : headerMap.keySet()) {
//				names.add(name);
//			}
//			return Collections.enumeration(names);
//		}
//
//		@Override
//		public Enumeration<String> getHeaders(String name) {
//			List<String> values = Collections.list(super.getHeaders(name));
//			if (headerMap.containsKey(name)) {
//				values.add(headerMap.get(name));
//			}
//			return Collections.enumeration(values);
//		}
//
//	}

}
