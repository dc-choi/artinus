package kr.co.artinus.infra.external;

import kr.co.artinus.global.common.message.FailHttpMessage;
import kr.co.artinus.global.exception.BusinessException;
import kr.co.artinus.infra.external.dto.GetRandomResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ExternalService {
    private final RestClient restClient = RestClient.create();

    @Value("${external.random.host}")
    private String randomApiHost;

    @Value("${external.random.path}")
    private String randomApiPath;

    public List<GetRandomResponse> getRandom() {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(randomApiHost)
                        .path(randomApiPath)
                        .queryParam("min", 0)
                        .queryParam("max", 1)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new BusinessException(FailHttpMessage.INTERNAL_SERVER_ERROR);
                })
                .body(new ParameterizedTypeReference<List<GetRandomResponse>>() {});
    }
}
