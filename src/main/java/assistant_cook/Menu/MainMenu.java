package assistant_cook.Menu;

import java.util.*;

public class MainMenu extends Menu{

    public MainMenu() {

        super();

         this.menuItems.add("Выберите действие:");
        addMenuItems("- 1. Получить рецепт", "1", 1);
        addMenuItems("- 2. Подобрать рецепт по ингредиентам", "2", 2);
        addMenuItems("- 3. Добавить рецепт", "3", 3);
        addMenuItems("- 4. Удалить рецепт", "4", 4);
        addMenuItems("- 0. Выход", "0", 0);

    }
}
