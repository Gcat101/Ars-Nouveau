package com.hollingsworth.arsnouveau.client.patchouli;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ApparatusProcessor implements IComponentProcessor {
    EnchantingApparatusRecipe recipe;
    @Override
    public void setup(IVariableProvider variables) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        String recipeID = variables.get("recipe").asString();
        recipe = (EnchantingApparatusRecipe) manager.byKey(new ResourceLocation(recipeID)).orElse(null);
    }

    @Override
    public IVariable process(String key) {
        if(recipe == null)
            return null;
        if(key.equals("reagent"))
            return IVariable.wrapList(Arrays.asList(recipe.reagent.getItems()).stream().map(IVariable::from).collect(Collectors.toList()));

        if(key.equals("recipe")){
            return IVariable.wrap(recipe.getId().toString());
        }
        if(key.equals("output")){
            return IVariable.from(recipe.result);
        }
        if(key.equals("footer")){
            return IVariable.wrap(recipe.result.getItem().getDescriptionId());
        }

        return null;
    }
}
