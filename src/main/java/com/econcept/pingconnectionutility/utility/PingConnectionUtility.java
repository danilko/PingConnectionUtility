/**
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * 
 * Permission is hereby granted, free of charge, 
 * to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom 
 * the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * The above copyright notice and this permission notice 
 * shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY 
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS 
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */


package com.econcept.pingconnectionutility.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PingConnectionUtility {
	public static void main(String[] args) {
		try {
			//PingConnectionUtility.httpPingable("https://google.com");  // Working
			//PingConnectionUtility.httpPingable("https://api.soundcloud.com/oauth22"); // Fail
			
			PingConnectionUtility.httpPingable(args[0]);
		} catch (Exception exception) {
			System.out.println("Error in performing the request");
			System.out.println(exception.toString());

		} // catch
	}  // static void main

	private static void httpPingable(String targetURI) throws IOException, URISyntaxException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		httpGet.setURI(new URI(targetURI));
		CloseableHttpResponse response = httpClient.execute(httpGet);
		
		int currentCode = response.getStatusLine()
				.getStatusCode();
		try {
			
			if (currentCode >= 200 && currentCode < 300 ) {
				HttpEntity entity = response.getEntity();

				InputStream responseStream = entity.getContent();

				StringWriter writer = new StringWriter();

				IOUtils.copy(responseStream, writer, "UTF-8");
				
				System.out.println("Target Server are ok: " + currentCode);
				System.out.println(writer.toString());
				
				EntityUtils.consume(entity);
			} // if
			else 
			{
				System.out.println("Target Server are not ok: " + currentCode);
			} // else
		} // try
		finally {
			response.close();
		} // finally

	}  // httpPingable

}  // class PingConnectionUtility

