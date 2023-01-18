package cn.vinsonws.tools.geoserver.connector.args;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author Vinsonws
 */
public interface WithBody {
    /**
     * Customized
     */
    HttpRequest.BodyPublisher getBodyPublisher();

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

    interface Empty extends WithBody {
        @Override
        default HttpRequest.BodyPublisher getBodyPublisher() {
            return HttpRequest.BodyPublishers.noBody();
        }
    }
}
