package com.ebitg.jetty;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ClassPathUtils;
import org.apache.commons.lang3.ClassUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.Ignore;
import org.junit.Test;

public class JettyTest {
	@Test
	@Ignore
	public void test01() throws Exception {
		Server server = new Server(9191);
		Handler handler = new AbstractHandler() {
			@Override
			public void handle(String str, Request request, HttpServletRequest req, HttpServletResponse resp)
					throws IOException, ServletException {
				System.out.println("OK");
				System.out.println(str);
				System.out.println(request);
				System.out.println(req);
				PrintWriter writer = resp.getWriter();
				writer.write("OK");
				writer.print("print content");
				writer.flush();
				writer.close();
			}
		};
		server.setHandler(handler);
		server.start();
		server.join();
	}

	@Test
	@Ignore
	public void test02() throws Exception {
		ServletContextHandler servletHandler = new ServletContextHandler();
		servletHandler.addServlet(Servlet1.class, "/a");
		servletHandler.addServlet(Servlet2.class, "/b");
		Server server = new Server(9191);
		server.setHandler(servletHandler);
		server.start();
		server.join();
	}

	@Test
	public void test03() {
		String packageName = ClassUtils.getPackageName(this.getClass());
		System.out.println(packageName);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		System.out.println("this.getClass().getClassLoader().getResource(\".\")"
				+ this.getClass().getClassLoader().getResource("."));
		System.out.println("this.getClass().getResource(\".\")" + this.getClass().getResource("."));
		System.out.println("this.getClass().getClassLoader()" + this.getClass().getClassLoader());
		System.out.println(
				"Thread.currentThread().getContextClassLoader()" + Thread.currentThread().getContextClassLoader());
	}
}
