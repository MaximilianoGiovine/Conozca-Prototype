// BaseTest.java - Colocar en: test/java/com/conozca/prototype/BaseTest.java
package com.conozca.prototype;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public abstract class BaseTest {
    // Configuración común para todos los tests
}