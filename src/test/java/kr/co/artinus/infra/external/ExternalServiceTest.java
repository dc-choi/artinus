package kr.co.artinus.infra.external;

import kr.co.artinus.infra.external.dto.GetRandomResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExternalServiceTest {
    @Autowired ExternalService externalService;

    @Test
    void randomTest() {
        // when
        List<GetRandomResponse> getRandomResponse = externalService.getRandom();

        // then
        assertThat(getRandomResponse).isNotEmpty();
        assertThat(getRandomResponse.size()).isEqualTo(1);
        assertThat(getRandomResponse.getFirst().status()).isEqualTo("success");
        assertThat(getRandomResponse.getFirst().min()).isEqualTo(0);
        assertThat(getRandomResponse.getFirst().max()).isEqualTo(1);
    }
}