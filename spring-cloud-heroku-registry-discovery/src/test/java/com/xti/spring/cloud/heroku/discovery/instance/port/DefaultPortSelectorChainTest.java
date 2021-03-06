package com.xti.spring.cloud.heroku.discovery.instance.port;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ClearSystemProperties;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultPortSelectorChainTest {

    @Rule
    public final ClearSystemProperties clearedServerPort = new ClearSystemProperties("server.port");

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Test
    public void springCloudHerokuPortEnvTest() throws ClusterPortNotFoundException {
        environmentVariables.set("SPRING_CLOUD_HEROKU_PORT", "1111");
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThat(portSelectorChain.getPort()).isEqualTo(1111);
    }

    @Test
    public void PortOrderPrioTest() throws ClusterPortNotFoundException {
        environmentVariables.set("SPRING_CLOUD_HEROKU_PORT", "1111");
        System.setProperty("server.port", "1112");
        environmentVariables.set("PORT", "1113");
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThat(portSelectorChain.getPort()).isEqualTo(1111);
    }

    @Test
    public void defaultPortTest() throws ClusterPortNotFoundException {
        PortSelectorChain portSelectorChain = new DefaultPortSelectorChain();
        assertThat(portSelectorChain.getPort()).isEqualTo(8080);
    }
}
