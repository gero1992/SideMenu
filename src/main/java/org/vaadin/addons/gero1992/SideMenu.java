package org.vaadin.addons.gero1992;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

/**
 * Vaadin server side component for the 'side-menu' web component.
 * Contains elements of {@link SideMenuItem}.
 * <p>
 * In <b>compact</b> mode the menu will require less space and display
 * the items only as icons. In addition a tooltip with the label of the
 * item will be displayed on hover.
 *
 * @author gvachkov on 24.05.2021
 */

@Tag("side-menu")
@NpmPackage(
        value = "@maksu/side-menu",
        version = "1.0.14"
)
@JsModule("@maksu/side-menu/side-menu.js")
public class SideMenu extends Component {

    public static final String COMPACT_PROPERTY = "compact";
    public static final String COMPACT_CHANGE_EVENT = "side-menu-compact-change";
    private List<SideMenuItem> sideMenuItems;

    /**
     * Creates an instance of the component.
     *
     * @param sideMenuItems - the child items which will be placed in the menu
     */
    public SideMenu(SideMenuItem... sideMenuItems) {
        setSideMenuItems(Arrays.asList(sideMenuItems));
    }

    /**
     * Creates an instance of the component.
     *
     * @param sideMenuItems - the child items which will be placed in the menu
     */
    public SideMenu(List<SideMenuItem> sideMenuItems) {
        setSideMenuItems(sideMenuItems);
    }

    /**
     * Enables or disables compact mode
     *
     * @param compact - true to enable compact mode, false to disable it
     */
    public void setCompact(boolean compact) {
        getElement().setProperty(COMPACT_PROPERTY, compact);
    }

    /**
     * @return true if the menu is in compact mode
     */
    public boolean isCompact() {
        return isCompactBoolean();
    }

    @Synchronize(
            property = COMPACT_PROPERTY,
            value = COMPACT_CHANGE_EVENT)
    protected boolean isCompactBoolean() {
        return getElement().getProperty(COMPACT_PROPERTY, false);
    }

    public List<SideMenuItem> getSideMenuItems() {
        return sideMenuItems;
    }

    public void setSideMenuItems(List<SideMenuItem> sideMenuItems) {
        this.sideMenuItems = sideMenuItems;
        getElement().removeAllChildren();
        sideMenuItems.forEach(sideMenuItem -> getElement().appendChild(sideMenuItem.getElement()));
    }
}
