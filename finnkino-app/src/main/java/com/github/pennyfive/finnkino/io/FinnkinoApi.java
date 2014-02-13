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

package com.github.pennyfive.finnkino.io;

import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.Map;

/**
 *
 */
public class FinnkinoApi {
    private static final String BASE_URL = "http://www.finnkino.fi/xml/";

    private static <T> T get(Class<T> clazz, String path, Map<String, String> queryParams) throws IOException {
        HttpClient http = new HttpClient();
        String response = http.get(BASE_URL + path);
        try {
            return new Persister().read(clazz, response);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

}
