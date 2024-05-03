package de.cristelknight.doapi.common.registry;

import de.cristelknight.doapi.DoApi;
import de.cristelknight.doapi.DoApiRL;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class DoApiSoundEventRegistry {

    private static final Registrar<SoundEvent> SOUND_EVENTS = DeferredRegister.create(DoApi.MOD_ID, Registries.SOUND_EVENT).getRegistrar();
    public static final RegistrySupplier<SoundEvent> COOKING_POT_HIT = create("cooking_pot_hit");
    public static final RegistrySupplier<SoundEvent> DRAWER_OPEN = create("drawer_open");
    public static final RegistrySupplier<SoundEvent> DRAWER_CLOSE = create("drawer_close");
    public static final RegistrySupplier<SoundEvent> CABINET_OPEN = create("cabinet_open");
    public static final RegistrySupplier<SoundEvent> CABINET_CLOSE = create("cabinet_close");
    public static final RegistrySupplier<SoundEvent> CAKE_CUT = create("cake_cut");
    public static final RegistrySupplier<SoundEvent> COOKING_POT_BOILING = create("cooking_pot_boiling");
    public static final RegistrySupplier<SoundEvent> ROASTER_COOKING = create("roaster_cooking");
    public static final RegistrySupplier<SoundEvent> STOVE_CRACKLING = create("stove_crackling");
    public static final RegistrySupplier<SoundEvent> CRAFTING_BOWL_STIRRING = create("crafting_bowl_stirring");
    public static final RegistrySupplier<SoundEvent> MINCER_CRANKING = create("mincer_cranking");
    public static final RegistrySupplier<SoundEvent> WATER_SPRINKLER = create("water_sprinkler");
    public static final RegistrySupplier<SoundEvent> CART_MOVING = create("cart_moving");
    public static final RegistrySupplier<SoundEvent> DINNER_BELL = create("dinner_bell");
    public static final RegistrySupplier<SoundEvent> TYPEWRITER = create("typewriter");
    public static final RegistrySupplier<SoundEvent> BREATH = create("breath");
    public static final RegistrySupplier<SoundEvent> BEER_ELEMENTAL_AMBIENT = create("beer_elemental_ambient");
    public static final RegistrySupplier<SoundEvent> BEER_ELEMENTAL_HURT = create("beer_elemental_hurt");
    public static final RegistrySupplier<SoundEvent> BEER_ELEMENTAL_DEATH = create("beer_elemental_death");
    public static final RegistrySupplier<SoundEvent> BEER_ELEMENTAL_ATTACK = create("beer_elemental_attack");
    public static final RegistrySupplier<SoundEvent> BREWSTATION_AMBIENT = create("brewstation_ambient");
    public static final RegistrySupplier<SoundEvent> BREWSTATION_KETTLE = create("brewstation_kettle");
    public static final RegistrySupplier<SoundEvent> BREWSTATION_OVEN = create("brewstation_oven");
    public static final RegistrySupplier<SoundEvent> BREWSTATION_PROCESS_FAILED = create("brewstation_process_failed");
    public static final RegistrySupplier<SoundEvent> BREWSTATION_TIMER_LOOP = create("brewstation_timer");
    public static final RegistrySupplier<SoundEvent> BREWSTATION_WHISTLE = create("brewstation_whistle");



    private static RegistrySupplier<SoundEvent> create(String name) {
        final ResourceLocation id = new DoApiRL(name);
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {
        DoApi.LOGGER.debug("Registering Sounds for " + DoApi.MOD_ID);
    }
}
