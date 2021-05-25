package org.vaadin.addons.gero1992;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value = "settings",
        layout = MainLayout.class)
public class Settings extends VerticalLayout {

    public Settings() {
        Button darkModeToggle = new Button("Toggle dark mode", VaadinIcon.MOON_O.create(), event -> setTheme(Lumo.DARK));
        darkModeToggle.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(darkModeToggle);
        add(new Button("Toggle light mode", VaadinIcon.SUN_O.create(), event -> setTheme(Lumo.LIGHT)));

        setSizeFull();
    }

    private void setTheme(String theme) {
        UI.getCurrent()
                .getPage()
                .executeJs("document.documentElement.setAttribute('theme', '" + theme + "')");
    }
}