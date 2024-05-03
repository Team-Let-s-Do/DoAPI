package de.cristelknight.doapi.common.datagen;

import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AdvancementRecipeGenerator {

    private static final String FOLDER = "/Users/marco/Desktop/Neuer Ordner/";

    public static void main(String[] args) {
        List<String> recipes = List.of("cooking_pot/fondue");
        recipes.forEach(AdvancementRecipeGenerator::processRecipe);
    }

    private static void processRecipe(String recipePath) {
        String[] parts = recipePath.split("/");
        if (parts.length < 2) {
            System.out.println("Invalid entry: " + recipePath);
            return;
        }
        generateJson(parts[0], parts[1]);
    }

    private static void generateJson(String category, String recipe) {
        String namespacedRecipe = "meadow:" + recipe;
        String namespacedCategory = "meadow:" + category;

        if (category.contains(":")) {
            namespacedCategory = category;
            category = category.substring(category.indexOf(':') + 1);
        }

        if (recipe.contains(":")) {
            namespacedRecipe = recipe;
            recipe = recipe.substring(recipe.indexOf(':') + 1);
        }

        try (FileWriter fileWriter = new FileWriter(FOLDER + recipe + ".json");
             JsonWriter jsonWriter = new JsonWriter(fileWriter)) {
            jsonWriter.setIndent("  ");
            jsonWriter.beginObject()
                    .name("parent").value("minecraft:recipes/root")
                    .name("rewards").beginObject()
                    .name("recipes").beginArray().value(namespacedRecipe).endArray()
                    .endObject()
                    .name("criteria").beginObject()
                    .name("has_" + category).beginObject()
                    .name("trigger").value("minecraft:inventory_changed")
                    .name("conditions").beginObject()
                    .name("items").beginArray()
                    .beginObject()
                    .name("items").beginArray().value(namespacedCategory).endArray()
                    .endObject()
                    .endArray()
                    .endObject()
                    .endObject()
                    .name("has_the_recipe").beginObject()
                    .name("trigger").value("minecraft:recipe_unlocked")
                    .name("conditions").beginObject()
                    .name("recipe").value(namespacedRecipe)
                    .endObject()
                    .endObject()
                    .endObject()
                    .name("requirements").beginArray()
                    .beginArray().value("has_" + category).value("has_the_recipe").endArray()
                    .endArray()
                    .endObject();
        } catch (IOException e) {
            System.out.printf("[Meadow] Couldn't write recipe to %s%s.json%n", FOLDER, recipe);
            e.printStackTrace();
        }
    }
}
