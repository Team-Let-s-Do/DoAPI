package de.cristelknight.doapi.common.datagen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ConditionalMincerRecipeGenerator {
    public static void createJson(String ingredientItem, String recipeType, String resultItem, int resultCount) {
        String json = String.format(
                "{\n" +
                        "  \"type\": \"doapi:conditional\",\n" +
                        "  \"conditions\": [\n" +
                        "    {\n" +
                        "      \"type\": \"forge:mod_loaded\",\n" +
                        "      \"modid\": \"farm_and_charm\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"recipe\": {\n" +
                        "    \"type\": \"farm_and_charm:mincer\",\n" +
                        "    \"ingredient\": { \"item\": \"%s\" },\n" +
                        "    \"recipe_type\": \"%s\",\n" +
                        "    \"result\": {\n" +
                        "      \"item\": \"%s\",\n" +
                        "      \"count\": %d\n" +
                        "    }\n" +
                        "  }\n" +
                        "}", ingredientItem, recipeType, resultItem, resultCount
        );

        String desktopPath = System.getProperty("user.home") + "/Desktop/Recipes";
        String filename = resultItem.replace(':', '_') + "_from_Mincer.json";
        Path filePath = Paths.get(desktopPath, filename);
        try {
            Files.writeString(filePath, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Map<String, Object>> recipes = List.of(
                Map.of("ingredient_item", "meadow:alpine_salt_ore", "recipe_type", "METAL", "result_item", "meadow:alpine_salt", "result_count", 6),
                Map.of("ingredient_item", "meadow:alpine_coal_ore", "recipe_type", "METAL", "result_item", "minecraft:coal", "result_count", 4),
                Map.of("ingredient_item", "meadow:alpine_gold_ore", "recipe_type", "METAL", "result_item", "minecraft:gold_nugget", "result_count", 12),
                Map.of("ingredient_item", "meadow:alpine_lapis_ore", "recipe_type", "METAL", "result_item", "minecraft:lapis_lazuli", "result_count", 4),
                Map.of("ingredient_item", "meadow:alpine_emerald_ore", "recipe_type", "METAL", "result_item", "minecraft:emerald", "result_count", 3),
                Map.of("ingredient_item", "meadow:alpine_iron_ore", "recipe_type", "METAL", "result_item", "minecraft:iron_nugget", "result_count", 6),
                Map.of("ingredient_item", "meadow:alpine_copper_ore", "recipe_type", "METAL", "result_item", "minecraft:raw_copper", "result_count", 8),
                Map.of("ingredient_item", "meadow:alpine_diamond_ore", "recipe_type", "METAL", "result_item", "minecraft:diamond", "result_count", 2),
                Map.of("ingredient_item", "meadow:alpine_redstone_ore", "recipe_type", "METAL", "result_item", "minecraft:redstone", "result_count", 12),
                Map.of("ingredient_item", "meadow:limestone", "recipe_type", "METAL", "result_item", "meadow:cobbled_limestone", "result_count", 4)
        );

        recipes.forEach(recipe -> createJson(
                (String) recipe.get("ingredient_item"),
                (String) recipe.get("recipe_type"),
                (String) recipe.get("result_item"),
                (int) recipe.get("result_count")
        ));
    }
}
