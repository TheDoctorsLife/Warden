package io.ll.warden.check;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.ll.warden.WardenPlugin;
import io.ll.warden.accounts.WardenAccountManager;
import io.ll.warden.check.checks.combat.*;
import io.ll.warden.check.checks.inventory.*;
import io.ll.warden.check.checks.movement.*;
import io.ll.warden.check.checks.world.*;
import io.ll.warden.commands.AuthAction;

/**
 * Creator: LordLambda
 * Date: 3/16/2015
 * Project: Warden
 * Usage: A check manager
 */
public class CheckManager implements Listener {

  private List<Check> checks;

  /**
   * Initalize everything we need to.
   */
  public CheckManager() {
    checks = new ArrayList<Check>();
    checks.add(new XCarryCheck());
    checks.add(new SprintCheck());
    checks.add(new ReachCheck());
    checks.add(new SpeedCheck());
    checks.add(new BlockReachCheck());
    checks.add(new WindowClickSpeedCheck());
    checks.add(new FightFrequencyCheck());
    checks.add(new SelfHitCheck());
    checks.add(new BlockPlaceSpeedCheck());
    checks.add(new GhostHitCheck());
    checks.add(new InvalidMovementCheck());
    checks.add(new NoFallCheck());
  }

  /**
   * Determine if a player is extempt from the check
   *
   * @param uuid The UUID of the player
   * @param c    The check
   * @return If the player based off the UUID should be checked by the passed check.
   */
  public boolean shouldCheckPlayerForCheck(UUID uuid, Check c) {
    if (WardenAccountManager.get().playerHasWardenAccount(uuid)) {
      AuthAction.AuthLevel level = WardenAccountManager.get().getAuthLevel(uuid);
      return level == AuthAction.AuthLevel.MODERATOR ||
             level == AuthAction.AuthLevel.ADMIN ||
             level == AuthAction.AuthLevel.OWNER;
    }
    return true;
  }

  /**
   * Register a listener for every single check.
   *
   * @param w  An instance of warden
   * @param pm An instance of the PluginManager
   */
  public void registerListeners(WardenPlugin w, PluginManager pm) {
    for (Check c : checks) {
      c.registerListeners(w, pm);
    }
  }

}
