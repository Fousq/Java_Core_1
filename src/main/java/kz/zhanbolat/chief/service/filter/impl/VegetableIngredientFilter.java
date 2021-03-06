package kz.zhanbolat.chief.service.filter.impl;

import kz.zhanbolat.chief.entity.ingredient.Ingredient;
import kz.zhanbolat.chief.entity.ingredient.organic.OrganicIngredient;
import kz.zhanbolat.chief.entity.ingredient.organic.OrganicType;
import kz.zhanbolat.chief.service.filter.IngredientFilter;

import java.util.ArrayList;
import java.util.List;

public class VegetableIngredientFilter implements IngredientFilter {
    @Override
    public List<Ingredient> filterIngredients(List<Ingredient> ingredients) {
        List<Ingredient> vegetableIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            if (ingredient instanceof OrganicIngredient) {
                OrganicIngredient organicIngredient = (OrganicIngredient) ingredient;
                if (organicIngredient.getOrganicType() == OrganicType.VEGETABLE) {
                    vegetableIngredients.add(organicIngredient);
                }
            }
        }
        return vegetableIngredients;
    }
}
