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

package com.github.pennyfive.finnkino.api;

import android.net.Uri;
import android.util.Log;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.util.HttpClient;

import org.simpleframework.xml.Serializer;

import java.io.IOException;
import java.util.Map.Entry;

import javax.inject.Inject;

/**
 *
 */
public class FinnkinoApi {
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "www.finnkino.fi";
    private static final String BASE_PATH = "XML";

    @Inject Serializer serializer;
    @Inject HttpClient http;

    @Inject
    public FinnkinoApi() {
        InjectUtils.injectMembers(this);
    }

    public <T> T execute(ApiCommand<T> command) throws IOException {
        String url = constructUrl(command);
        Log.d("FinnkinoApi", "GET: " + url);
        String response = http.get(url);
        Log.d("FinnkinoApi", "RESPONSE: " + response);
        try {
            return serializer.read(command.getTypeClass(), response);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    private static String constructUrl(ApiCommand<?> command) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME);
        builder.encodedAuthority(AUTHORITY);
        builder.appendPath(BASE_PATH);
        builder.appendPath(command.getPath());
        for (Entry<String, String> entry : command.getQueryParams().entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build().toString();
    }
}
