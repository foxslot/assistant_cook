package assistant_cook.Menu;

import java.util.*;

public class MainMenu extends Menu{

    public MainMenu() {

        super();

        // Вывод меню
        //Выберите действие
        //1. Получить рецепт
        //2. Получить список покупок
        //3. Подобрать рецепт по ингридиентам
        //4. Добавить рецепт
        //0. Выход

        this.menuItems.add("Выберите действие:");
        addMenuItems("- 1. Получить рецепт", "1", 1);
        addMenuItems("- 2. Подобрать рецепт по ингридиентам", "2", 2);
        addMenuItems("- 3. Добавить рецепт", "3", 3);
        addMenuItems("- 0. Выход", "0", 0);

    }
}
