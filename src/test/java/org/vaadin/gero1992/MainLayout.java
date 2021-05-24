package org.vaadin.gero1992;

import java.util.Arrays;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.RouterLayout;

/**
 * Main layout for the demo of the SideMenu component.
 * @author gvachkov on 24.05.2021
 */
@CssImport("./demo-styles.css")
public class MainLayout extends FlexLayout implements RouterLayout {

    private SideMenu sideMenu;

    public MainLayout() {
        Div drawer = buildDrawer();
        add(drawer);

        getStyle().set("overflow", "hidden");
        setSizeFull();
    }

    private Div buildDrawer() {
        sideMenu = buildSideMenu();

        Div sideContainer = new Div(sideMenu);
        sideContainer.setId("sideContainer");

        Button toggleButton = new Button("Collapse", VaadinIcon.CARET_LEFT.create(), buttonClickEvent -> {
            Button sourceButton = buttonClickEvent.getSource();
            if (sideMenu.isCompact()) {
                sideMenu.setCompact(false);
                sourceButton.setIcon(VaadinIcon.CARET_LEFT.create());
                sourceButton.setText("Collapse");
                sideContainer.removeClassName("collapsed");
                sourceButton.setWidthFull();
            }
            else {
                sideMenu.setCompact(true);
                sourceButton.setIcon(VaadinIcon.CARET_RIGHT.create());
                sourceButton.setText("");
                sideContainer.setClassName("collapsed");
                sourceButton.setSizeUndefined();
            }
        });
        toggleButton.setWidthFull();
        toggleButton.getStyle()
                .set("display", "flex");

        sideContainer.add(toggleButton);
        return sideContainer;
    }

    private SideMenu buildSideMenu() {
        // Items
        SideMenuItem home = new SideMenuItem("Home", VaadinIcon.HOME.create(), View.class);
        SideMenuItem favorites = new SideMenuItem("Favorites", VaadinIcon.STAR.create(), Favorites.class);
        SideMenuItem news = new SideMenuItem("News", VaadinIcon.NEWSPAPER.create(), News.class);
        SideMenuItem contact = new SideMenuItem("Settings", VaadinIcon.OPTIONS.create(), Settings.class);

        // Sub items
        SideMenuItem lastWeekEvents = new SideMenuItem("Last week events", LastWeekEvents.class);
        SideMenuItem lastMonthEvents = new SideMenuItem("Last month events", LastMonthEvents.class);

        // Create parent with sub items
        SideMenuItem events = new SideMenuItem("Events", VaadinIcon.CALENDAR.create());
        events.setChildMenuItems(Arrays.asList(lastWeekEvents, lastMonthEvents));

        return new SideMenu(Arrays.asList(home, favorites, events, news, contact));
    }
}
