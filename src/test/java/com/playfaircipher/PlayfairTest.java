package com.playfaircipher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayfairTest {
    @Test
    void encrypt() {
        assertEquals("BMODZBXDNABEKUDMUIXMMOUVIF", Playfair.encrypt("Hide the gold in the tree stump", "playfair example"));
    }

    @Test
    void decrypt() {
        assertEquals("HIDETHEGOLDINTHETREXESTUMP", Playfair.decrypt("BMODZBXDNABEKUDMUIXMMOUVIF", "playfair example"));
    }
}