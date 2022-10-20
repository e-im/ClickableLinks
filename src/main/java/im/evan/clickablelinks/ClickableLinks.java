package im.evan.clickablelinks;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Pattern;

public class ClickableLinks extends JavaPlugin implements Listener {
    private static final Pattern URL_REGEX = Pattern.compile("(https?://)?[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z0-9]{1,10})((/+)[^/ ]*)*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static final TextReplacementConfig REPLACER = TextReplacementConfig.builder()
            .match(URL_REGEX)
            .replacement((c) -> c.clickEvent(ClickEvent.openUrl(c.content().startsWith("http") ? c.content() : "https://" + c.content())))
            .build();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void on(final AsyncChatEvent event) {
        event.message(event.message().replaceText(REPLACER));
    }
}
