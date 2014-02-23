/*
 * Copyright 2014 Joonas Lehtonen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pennyfive.finnkino.util;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class HttpClient {
    private final OkHttpClient client = new OkHttpClient();

    public HttpClient() {

    }

    public String get(String url) throws IOException {
        HttpURLConnection connection = client.open(new URL(url));
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("ResponseCode=" + connection.getResponseCode());
        }
        InputStream in = connection.getInputStream();
        try {
            return Streams.readAsString(in);
        } finally {
            Streams.closeQuietly(in);
            connection.disconnect();
        }
    }

}
