# DoApi
## Example

```
public class Test {

    public static ResourceLocation PALM_BOAT_TYPE_LOCATION = new ResourceLocation(DoApi.MOD_ID, "palm");

    public static final ResourceLocation PALM_SIGN_TEXTURE_ID = new ResourceLocation(DoApi.MOD_ID, "entity/sign/palm");

    private static final String PALM_SIGN_ID = "palm_sign";
    private static final String PALM_WALL_SIGN_ID = "palm_wall_sign";

    public static void init() {
        RegistrySupplier<Item> palmBoat = TerraformBoatItemHelper.registerBoatItem(DoApi.ITEMS, "palm_boat", PALM_BOAT_TYPE_LOCATION, false, DoApi.CREATIVE_TAB);
        RegistrySupplier<Item> palmChestBoat = TerraformBoatItemHelper.registerBoatItem(DoApi.ITEMS, "palm_chest_boat", PALM_BOAT_TYPE_LOCATION, true, DoApi.CREATIVE_TAB);

        DoApiExpectPlatform.register(PALM_BOAT_TYPE_LOCATION, TerraformBoatType.builder().item(palmBoat).chestItem(palmChestBoat).build());



        RegistrySupplier<Block> sign = registerWithoutItem(PALM_SIGN_ID, () -> DoApiExpectPlatform.getSign(PALM_SIGN_TEXTURE_ID));
        RegistrySupplier<Block> wallSign = registerWithoutItem(PALM_WALL_SIGN_ID, () -> DoApiExpectPlatform.getWallSign(PALM_SIGN_TEXTURE_ID));

        RegistrySupplier<Item> palmSignItem = registerItem(PALM_SIGN_ID, () -> new SignItem(new Item.Properties().stacksTo(16).tab(DoApi.CREATIVE_TAB), sign.get(), wallSign.get()));
    }



    
}
```
### Client
```
public class DoApiClient {

    public static void onClientInit(){
        DoApiExpectPlatform.addSignSprite(Test.PALM_SIGN_TEXTURE_ID);
    }

}
```

### Registry
```
    private static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block, @Nullable CreativeModeTab tab) {
        return ObjectRegistry.registerWithItem(DoApi.BLOCKS, DoApi.BLOCK_REGISTRAR, DoApi.ITEMS, DoApi.ITEM_REGISTRAR, new ResourceLocation(DoApi.MOD_ID, name), block, tab);
    }

    private static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return ObjectRegistry.registerWithoutItem(DoApi.BLOCKS, DoApi.BLOCK_REGISTRAR, new ResourceLocation(DoApi.MOD_ID, path), block);
    }

    private static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return ObjectRegistry.registerItem(DoApi.ITEMS, DoApi.ITEM_REGISTRAR, new ResourceLocation(DoApi.MOD_ID, path), itemSupplier);
    }
´´´
