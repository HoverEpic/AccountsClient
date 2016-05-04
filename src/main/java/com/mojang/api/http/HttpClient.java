package com.mojang.api.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

public interface HttpClient
{

    String post(URL var1, HttpBody var2, List var3) throws IOException;

    String post(URL var1, Proxy var2, HttpBody var3, List var4) throws IOException;
}
