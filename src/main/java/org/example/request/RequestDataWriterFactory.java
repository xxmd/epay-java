package org.example.request;

public class RequestDataWriterFactory {
    public static RequestDataWriter getWriter(HttpRequest.ContentType contentType) {
        switch (contentType) {
            case FORM_DATA:
                return new FormDataWriter();
            case X_WWW_FORM_URLENCODED:
                return new FormUrlEncodeWriter();
        }
        return null;
    }
}
