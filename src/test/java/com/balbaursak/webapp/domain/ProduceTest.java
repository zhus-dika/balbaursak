package com.balbaursak.webapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.balbaursak.webapp.web.rest.TestUtil;

public class ProduceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produce.class);
        Produce produce1 = new Produce();
        produce1.setId(1L);
        Produce produce2 = new Produce();
        produce2.setId(produce1.getId());
        assertThat(produce1).isEqualTo(produce2);
        produce2.setId(2L);
        assertThat(produce1).isNotEqualTo(produce2);
        produce1.setId(null);
        assertThat(produce1).isNotEqualTo(produce2);
    }
}
