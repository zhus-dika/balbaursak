package com.balbaursak.webapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.balbaursak.webapp.web.rest.TestUtil;

public class PurchaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Purchase.class);
        Purchase purchase1 = new Purchase();
        purchase1.setId(1L);
        Purchase purchase2 = new Purchase();
        purchase2.setId(purchase1.getId());
        assertThat(purchase1).isEqualTo(purchase2);
        purchase2.setId(2L);
        assertThat(purchase1).isNotEqualTo(purchase2);
        purchase1.setId(null);
        assertThat(purchase1).isNotEqualTo(purchase2);
    }
}
