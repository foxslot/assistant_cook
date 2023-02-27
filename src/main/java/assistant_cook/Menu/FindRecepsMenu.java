package assistant_cook.Menu;

import java.security.PublicKey;

public class FindRecepsMenu extends Menu{

    public FindRecepsMenu(){
        super();

        this.menuItems.add("Выберите действие:");
        addMenuItems("- 1. Найти рецепт по наименованию", "1", 1);
        addMenuItems("- 2. Подобрать рецепт по ингридиентам", "2", 2);
        addMenuItems("- 0. Выход", "0", 0);

    }

}
