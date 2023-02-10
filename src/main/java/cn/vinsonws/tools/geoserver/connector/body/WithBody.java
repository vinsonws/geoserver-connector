package cn.vinsonws.tools.geoserver.connector.body;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Vinsonws
 */
public interface WithBody {
    HttpRequest.BodyPublisher getBodyPublisher();

    void validate();

    String getContentType();

    interface Json extends WithBody {
        @Override
        default HttpRequest.BodyPublisher getBodyPublisher() {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(getRequestBody()),
                    StandardCharsets.UTF_8);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        Object getRequestBody();
    }

    class JsonBody implements Json {
        private final Object o;

        protected JsonBody(Object o) {
            this.o = o;
        }

        @Override
        public Object getRequestBody() {
            return o;
        }

        @Override
        public void validate() {
        }

        @Override
        public String getContentType() {
            return "application/json";
        }
    }


    interface Empty extends WithBody {
        @Override
        default HttpRequest.BodyPublisher getBodyPublisher() {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

//    abstract class Builder<B extends Builder<B, A>, A extends WithBody> {
//        protected List<Consumer<A>> operations = new ArrayList<>();
//
//        @SuppressWarnings("unchecked")
//        private A newInstance() {
//            try {
//                for (Constructor<?> constructor :
//                    this.getClass().getEnclosingClass().getDeclaredConstructors()) {
//                    if (constructor.getParameterCount() == 0) {
//                        return (A) constructor.newInstance();
//                    }
//                }
//                throw new RuntimeException(
//                    this.getClass().getEnclosingClass() + " must have no argument constructor");
//            } catch (InstantiationException
//                     | IllegalAccessException
//                     | InvocationTargetException
//                     | SecurityException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        public A build() throws IllegalArgumentException {
//            A body = newInstance();
//            operations.forEach(operation -> operation.accept(body));
//            body.validate();
//            return body;
//        }
//    }
}
