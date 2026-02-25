package com.project.dramatic_advice.domain.generator;

import java.util.Random;

public class TemplateGenerator {

    private final Random random = new Random();

    private final String[] templates = {
            "You wrote: '%s'. Historians will call this %s.",
            "Ah yes, '%s'. Even philosophers would %s.",
            "Today: '%s'. Tomorrow? %s."
    };

    private final String[] endings = {
            "a cosmic turning point",
            "weep uncontrollably",
            "the beginning of chaos"
    };

    public String generate(String input) {
        String template = templates[random.nextInt(templates.length)];
        String ending = endings[random.nextInt(endings.length)];
        return String.format(template, input, ending);
    }
}
