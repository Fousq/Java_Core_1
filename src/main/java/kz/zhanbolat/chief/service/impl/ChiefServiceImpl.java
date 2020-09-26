package kz.zhanbolat.chief.service.impl;

import kz.zhanbolat.chief.entity.fridge.Fridge;
import kz.zhanbolat.chief.entity.ingredient.Ingredient;
import kz.zhanbolat.chief.entity.dish.Dish;
import kz.zhanbolat.chief.service.CookProcessorFactory;
import kz.zhanbolat.chief.service.DishIngredientFactory;
import kz.zhanbolat.chief.service.ChiefService;
import kz.zhanbolat.chief.service.DishType;
import kz.zhanbolat.chief.exception.NoIngredientsFoundException;
import kz.zhanbolat.chief.exception.NoSuchDishFound;
import kz.zhanbolat.chief.service.processor.CookProcessor;

import java.util.List;

public class ChiefServiceImpl implements ChiefService {
    private Fridge fridge;

    public ChiefServiceImpl(Fridge fridge) {
        this.fridge = fridge;
    }

    @Override
    public Dish cookDish(DishType dishType) throws NoIngredientsFoundException {
        List<Ingredient> ingredients = getIngredientsByDish(dishType);
        List<CookProcessor> cookProcessors = getCookProcessorsByDish(dishType);
        for (CookProcessor cookProcessor : cookProcessors) {
            ingredients = cookProcessor.cook(fridge);
        }
        return new Dish(ingredients);
    }

    private List<Ingredient> getIngredientsByDish(DishType dishType) throws NoIngredientsFoundException {
        for (DishIngredientFactory dishIngredient : DishIngredientFactory.values()) {
            if (dishIngredient.getDishes() == dishType) {
                return dishIngredient.getIngredients();
            }
        }
        throw new NoIngredientsFoundException("No ingredient was found for " + dishType.getName());
    }

    private List<CookProcessor> getCookProcessorsByDish(DishType dishType) {
        for (CookProcessorFactory cookProcessorFactory : CookProcessorFactory.values()) {
            if (cookProcessorFactory.getDishType() == dishType) {
                return cookProcessorFactory.getCookProcessors();
            }
        }
        throw new NoSuchDishFound("Chief doesn't know how to cook the \"" + dishType.getName() + "\"");
    }
}
