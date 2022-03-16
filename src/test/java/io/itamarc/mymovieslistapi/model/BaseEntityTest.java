package io.itamarc.mymovieslistapi.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BaseEntityTest {
    BaseEntity baseEntity1 = new BaseEntity(1L);
    BaseEntity baseEntity1b = new BaseEntity(1L);
    BaseEntity baseEntity2 = new BaseEntity(2L);
    BaseEntity baseEntityIdNull = new BaseEntity(null);
    BaseEntity baseEntityIdNull2 = new BaseEntity(null);

    @Test
    void equalsHappyPath() {
        assertTrue(baseEntity1.equals(baseEntity1));
        assertTrue(baseEntity1.equals(baseEntity1b));
        assertTrue(baseEntityIdNull.equals(baseEntityIdNull2));
    }

    @Test
    void equalsFailure() {
        assertFalse(baseEntity1.equals(null));
        assertFalse(baseEntity1.equals(baseEntity2));
        assertFalse(baseEntity1.equals(new Object()));
        assertFalse(baseEntity1.equals(baseEntityIdNull));
        assertFalse(baseEntityIdNull.equals(baseEntity1));
    }
}

// if (this == obj)
//     return true;
// if (obj == null)
//     return false;
// if (getClass() != obj.getClass())
//     return false;
// BaseEntity other = (BaseEntity) obj;
// if (id == null) {
//     if (other.id != null)
//         return false;
// } else if (!id.equals(other.id))
//     return false;
// return true;
