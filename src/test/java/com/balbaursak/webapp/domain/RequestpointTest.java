package com.balbaursak.webapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.balbaursak.webapp.web.rest.TestUtil;

public class RequestpointTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requestpoint.class);
        Requestpoint requestpoint1 = new Requestpoint();
        requestpoint1.setId(1L);
        Requestpoint requestpoint2 = new Requestpoint();
        requestpoint2.setId(requestpoint1.getId());
        assertThat(requestpoint1).isEqualTo(requestpoint2);
        requestpoint2.setId(2L);
        assertThat(requestpoint1).isNotEqualTo(requestpoint2);
        requestpoint1.setId(null);
        assertThat(requestpoint1).isNotEqualTo(requestpoint2);
    }
}
