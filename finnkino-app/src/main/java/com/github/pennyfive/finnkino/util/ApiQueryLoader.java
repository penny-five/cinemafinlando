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

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.api.FinnkinoApi;

import java.io.IOException;

import javax.inject.Inject;

/**
 * For querying {@link FinnkinoApi} using Loader framework.
 */
public abstract class ApiQueryLoader<T> extends AsyncTaskLoader<T> {
    @Inject FinnkinoApi api;
    private T data;

    public ApiQueryLoader(Context context) {
        super(context);
        InjectUtils.inject(this);
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public T loadInBackground() {
        try {
            return loadInBackground(api);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract T loadInBackground(FinnkinoApi api) throws IOException;

    @Override
    public void deliverResult(T data) {
        this.data = data;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        data = null;
    }
}
