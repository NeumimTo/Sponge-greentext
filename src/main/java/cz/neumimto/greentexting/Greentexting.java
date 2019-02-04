package cz.neumimto.greentexting;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.LiteralText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.transform.SimpleTextTemplateApplier;

@Plugin(
        id = "greentexting",
        name = "Greentexting",
        description = "The only chat plugin you will ever need.",
        authors = {
                "NeumimTo"
        }
)
public class Greentexting {

    private static final String arrow = ">";
    private static TextTemplate template;
    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.warn("\u001b[32m>Implying you can`t just greentext");
        template = TextTemplate.of(TextColors.GREEN, TextTemplate.arg("body"));
    }

    @Listener(order = Order.LATE)
    public void onChat(MessageChannelEvent.Chat event) {
        Text rawMessage = event.getRawMessage();
        String s = rawMessage.toPlain();
        if (s.startsWith(arrow)) {
            event.getFormatter().getBody().clear();
            LiteralText shipost = Text.builder(s).color(TextColors.GREEN).build();
            SimpleTextTemplateApplier fmt = new SimpleTextTemplateApplier();
            fmt.setTemplate(template);
            fmt.setParameter(MessageEvent.PARAM_MESSAGE_BODY, shipost);
            event.getFormatter().getBody().add(fmt);
        }
    }

}
