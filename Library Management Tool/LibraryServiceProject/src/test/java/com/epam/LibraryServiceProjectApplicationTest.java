package com.epam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
 class LibraryServiceProjectApplicationTest {
    @Test
    void testMain(){
        LibraryServiceProjectApplication.main(new String[] {});
        assertTrue(true);
    }
}
