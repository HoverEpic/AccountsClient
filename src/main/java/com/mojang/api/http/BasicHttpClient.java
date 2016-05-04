package com.mojang.api.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class BasicHttpClient implements HttpClient
{

    private static BasicHttpClient instance;

    public static BasicHttpClient getInstance()
    {
        if (instance == null)
        {
            instance = new BasicHttpClient();
        }
        return instance;
    }

    public String post(URL url, HttpBody body, List headers) throws IOException
    {
        return this.post(url, (Proxy) null, body, headers);
    }

    public String post(URL url, Proxy proxy, HttpBody body, List headers) throws IOException
    {
        if (proxy == null)
        {
            proxy = Proxy.NO_PROXY;
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
        connection.setRequestMethod("POST");
        Iterator writer = headers.iterator();
        while (writer.hasNext())
        {
            HttpHeader reader = (HttpHeader) writer.next();
            connection.setRequestProperty(reader.getName(), reader.getValue());
        }
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        DataOutputStream writer1 = new DataOutputStream(connection.getOutputStream());
        writer1.write(body.getBytes());
        writer1.flush();
        writer1.close();
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer response = new StringBuffer();
        String line;
        while ((line = reader1.readLine()) != null)
        {
            response.append(line);
            response.append('\r');
        }
        reader1.close();
        return response.toString();
    }
}
