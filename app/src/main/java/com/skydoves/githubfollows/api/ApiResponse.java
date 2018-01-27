package com.skydoves.githubfollows.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.skydoves.githubfollows.models.Envelope;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;
import timber.log.Timber;

/**
 * Common class used by API responses.
 * @param <T>
 */
public class ApiResponse<T> {
    private static final Pattern LINK_PATTERN = Pattern
            .compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");
    private static final Pattern PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)");
    private static final String NEXT_LINK = "next";
    public final int code;
    @Nullable
    public final T body;
    @NonNull
    public final Map<String, String> links;
    @NonNull
    private final Gson gson;
    @Nullable
    public final Envelope envelope;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        gson = new Gson();
        links = Collections.emptyMap();
        envelope = null;
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        gson = new Gson();
        if(response.isSuccessful()) {
            body = response.body();
            envelope = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Timber.e(ignored, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            envelope = gson.fromJson(message, Envelope.class);
            body = null;
        }
        String linkHeader = response.headers().get("link");
        if (linkHeader == null) {
            links = Collections.emptyMap();
        } else {
            links = new ArrayMap<>();
            Matcher matcher = LINK_PATTERN.matcher(linkHeader);

            while (matcher.find()) {
                int count = matcher.groupCount();
                if (count == 2) {
                    links.put(matcher.group(2), matcher.group(1));
                }
            }
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public Integer getNextPage() {
        String next = links.get(NEXT_LINK);
        if (next == null) {
            return null;
        }
        Matcher matcher = PAGE_PATTERN.matcher(next);
        if (!matcher.find() || matcher.groupCount() != 1) {
            return null;
        }
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException ex) {
            Timber.w("cannot parse next page from %s", next);
            return null;
        }
    }
}