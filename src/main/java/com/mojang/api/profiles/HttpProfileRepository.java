package com.mojang.api.profiles;

import com.google.gson.Gson;
import com.mojang.api.http.BasicHttpClient;
import com.mojang.api.http.HttpBody;
import com.mojang.api.http.HttpClient;
import com.mojang.api.http.HttpHeader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpProfileRepository implements ProfileRepository
{

    private static final int MAX_PAGES_TO_CHECK = 100;
    private static Gson gson = new Gson();
    private HttpClient client;

    public HttpProfileRepository()
    {
        this(BasicHttpClient.getInstance());
    }

    public HttpProfileRepository(HttpClient client)
    {
        this.client = client;
    }

    public Profile[] findProfilesByCriteria(ProfileCriteria... criteria)
    {
        try
        {
            HttpBody e = new HttpBody(gson.toJson(criteria));
            ArrayList headers = new ArrayList();
            headers.add(new HttpHeader("Content-Type", "application/json"));
            ArrayList profiles = new ArrayList();
            for (int i = 1; i <= MAX_PAGES_TO_CHECK; ++i)
            {
                ProfileSearchResult result = this.post(new URL("http://127.0.0.1:8080/profiles/page/" + i), e, headers);
                if (result.getSize() == 0)
                {
                    break;
                }
                profiles.addAll(Arrays.asList(result.getProfiles()));
            }
            return (Profile[]) profiles.toArray(new Profile[profiles.size()]);
        }
        catch (Exception var7)
        {
            var7.printStackTrace();
            return new Profile[0];
        }
    }

    private ProfileSearchResult post(URL url, HttpBody body, List headers) throws IOException
    {
        String response = this.client.post(url, body, headers);
        return (ProfileSearchResult) gson.fromJson(response, ProfileSearchResult.class);
    }
}
