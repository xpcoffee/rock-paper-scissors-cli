package com.xpcoffee.rock_paper_scissors;

import com.xpcoffee.rock_paper_scissors.store.GameStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.xpcoffee.rock_paper_scissors.engine.Engine;

@Configuration
public class AppConfig {
    @Bean
    public Engine getEngine(GameStore store) {
        return new Engine(store);
    }

    @Bean
    public GameStore getStore() {
        return new GameStore();
    }
}
