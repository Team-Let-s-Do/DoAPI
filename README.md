# DoApi Wiki

## Terraform Api for boats and signs
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
Don't forget to register your ``DeferredRegister``s after ``init()``

### Client
```
public class DoApiClient {

    public static void onClientInit(){
        DoApiExpectPlatform.addSignSprite(Test.PALM_SIGN_TEXTURE_ID);
    }

}
```
## Recipe Book
There is no tutorial yet, but you can take a look at this example of the Vinery mod

- Creating your [group](https://github.com/satisfyu/Vinery/blob/architectury-1.19.2/common/src/main/java/satisfyu/vinery/client/recipebook/custom/FermentationBarrelRecipeBookGroup.java)
- Handling your [book](https://github.com/satisfyu/Vinery/blob/architectury-1.19.2/common/src/main/java/satisfyu/vinery/client/screen/recipe/custom/FermentationPotRecipeBook.java)

- Adding your book to your [screenhandler](https://github.com/satisfyu/Vinery/blob/architectury-1.19.2/common/src/main/java/satisfyu/vinery/client/gui/handler/FermentationBarrelGuiHandler.java)
and [screen](https://github.com/satisfyu/Vinery/blob/architectury-1.19.2/common/src/main/java/satisfyu/vinery/client/screen/FermentationBarrelGui.java)
