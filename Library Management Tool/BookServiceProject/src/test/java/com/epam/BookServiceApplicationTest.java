package com.epam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
 class BookServiceApplicationTest {
        @Test
        void testMain(){
            BookServiceApplication.main(new String[] {});
            assertTrue(true);
        }
    }
