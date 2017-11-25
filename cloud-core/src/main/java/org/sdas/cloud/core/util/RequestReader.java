package org.sdas.cloud.core.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class RequestReader {

	public String getRequestContent(HttpServletRequest request) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line = reader.readLine();
		while (line != null){
			sb.append(line);
			line = reader.readLine();
		}
		return sb.toString();

	}
}
