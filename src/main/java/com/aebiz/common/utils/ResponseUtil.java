package com.aebiz.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class ResponseUtil {
	private static final String CHARSET = "UTF-8";
	public static final Logger logger = Logger.getLogger(ResponseUtil.class);

	public static void write(HttpServletResponse response, String[] list) {
		write(response, null, "UTF-8", list);
	}

	public static void write(HttpServletResponse response, Map<String, String> header, String charSet, String[] list) {
		if ((null != header) && (header.isEmpty())) {
			for (Map.Entry et : header.entrySet())
				response.setHeader((String) et.getKey(), (String) et.getValue());
		} else {
			response.setHeader("content-type", "text/html;charset=UTF-8");
		}

		if (StringUtils.isEmpty(charSet)) {
			charSet = "UTF-8";
		}

		response.setCharacterEncoding(charSet);

		PrintWriter out = null;
		try {
			out = response.getWriter();

			if (null != list)
				for (String str : list)
					out.println(str);
		} catch (IOException e) {
			logger.error("write error", e);
		} finally {
		}
	}
}