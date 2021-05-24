package org.vaadin.gero1992;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.StateTree;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.server.VaadinService;

/**
 * Vaadin server side component for the 'side-menu-item' web component.
 * Represents an individual item of {@link SideMenu}
 * It can have an icon, label, target and href.
 * It can also contain child items.
 *
 * @author gvachkov on 24.05.2021
 */

@Tag("side-menu-item")
@NpmPackage(
        value = "@maksu/side-menu",
        version = "1.0.14"
)
@JsModule("@maksu/side-menu/side-menu-item.js")
@CssImport("./side-menu-item.css")
public class SideMenuItem extends Component implements AfterNavigationObserver {

    private static final String HREF_PROPERTY = "href";
    private static final String TARGET_PROPERTY = "target";
    private static final String LABEL_PROPERTY = "label";

    private static final String SLOT_ATTRIBUTE = "slot";
    private static final String ICON_SLOT_VALUE = "icon";
    public static final String SELECTED = "selected";

    private String target;
    private String label;
    private List<SideMenuItem> childMenuItems;
    private Component icon;
    private String href;

    /**
     * Creates a parent item containing label icon and sub items.
     *
     * @param label          - the label which should be put in the item. Should describe the group of the child items.
     * @param icon           - icon which is placed on the left side of the label.
     * @param childMenuItems - sub items of the current item.
     */
    public SideMenuItem(String label, Component icon, List<SideMenuItem> childMenuItems, Class<? extends Component> clazz) {
        this(label, icon, clazz);
        setChildMenuItems(childMenuItems);
    }

    /**
     * Creates a parent item containing label icon and sub items.
     *
     * @param label          - the label which should be put in the item. Should describe the group of the child items.
     * @param childMenuItems - sub items of the current item.
     */
    public SideMenuItem(String label, SideMenuItem... childMenuItems) {
        this(label);
        setChildMenuItems(Arrays.asList(childMenuItems));
    }

    /**
     * Creates a parent item containing label icon and sub items.
     *
     * @param label          - the label which should be put in the item. Should describe the group of the child items.
     * @param childMenuItems - sub items of the current item.
     */
    public SideMenuItem(String label, List<SideMenuItem> childMenuItems) {
        this(label);
        setChildMenuItems(childMenuItems);
    }

    /**
     * Creates an instance of the item containing a label, href and icon.
     *
     * @param label            - the label which should be put in the item. Should describe the navigation target.
     * @param icon             - icon which is placed on the left side of the label.
     * @param navigationTarget - the class of the navigation target
     */
    public SideMenuItem(String label, Component icon, Class<? extends Component> navigationTarget) {
        this(label, navigationTarget);
        setIcon(icon);
    }

    /**
     * Creates an item containing a label and navigation target.
     *
     * @param label            - the label which should be put in the item.
     * @param navigationTarget - the class of the navigation target
     */
    public SideMenuItem(String label, Class<? extends Component> navigationTarget) {
        this(label);
        Objects.requireNonNull(navigationTarget, "The navigation target can't be null.");

        href = RouteConfiguration.forRegistry(getRouter().getRegistry())
                .getUrl(navigationTarget);

        // Set the router-link attribute to the anchor when the element is rendered
        getElement().executeJs(" this.updateComplete.then(() => {" +
                " const link = this.shadowRoot.getElementById('itemLink');" +
                " link.setAttribute('router-link', '');" +
                " this.setAttribute('href', $0);" +
                "});", href);
    }


    /**
     * Creates an item containing label.
     *
     * @param label - the label which should be put in the item.
     * @param icon  - the icon which should be put in the item.
     */
    public SideMenuItem(String label, Component icon, SideMenuItem... sideMenuItems) {
        this(label);
        setIcon(icon);
        setChildMenuItems(Arrays.asList(sideMenuItems));
    }

    /**
     * Creates an item containing label.
     *
     * @param label - the label which should be put in the item.
     * @param icon  - the icon which should be put in the item.
     */
    public SideMenuItem(String label, Component icon) {
        this(label);
        setIcon(icon);
    }

    /**
     * Creates an item containing label.
     *
     * @param label - the label which should be put in the item.
     */
    public SideMenuItem(String label) {
        setLabel(label);
    }

    /**
     * @return the state of the item. True if the item is selected
     */
    public boolean isSelected() {
        return isSelectedBoolean();
    }

    @Synchronize(
            property = "selected",
            value = "side-menu-item-select")
    protected boolean isSelectedBoolean() {
        return getElement().getProperty("selected", false);
    }

    public String getTarget() {
        return target;
    }

    /**
     * Specifies where to open the link when it's clicked.
     * <i>Tip: Use '_blank' to open in new tab</i>
     *
     * @param target - the target of the link.
     */
    public void setTarget(String target) {
        this.target = target;
        getElement().setProperty(TARGET_PROPERTY, target);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        Objects.requireNonNull(label, "The label can't be null.");
        this.label = label;
        getElement().setProperty(LABEL_PROPERTY, label);
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
        getElement().setProperty(HREF_PROPERTY, href);
    }

    public List<SideMenuItem> getChildMenuItems() {
        return childMenuItems;
    }

    public void setChildMenuItems(List<SideMenuItem> childMenuItems) {
        this.childMenuItems = childMenuItems;

        //Remove all child elements except the icon
        getElement().getChildren()
                .filter(element -> !element.equals(icon.getElement()))
                .forEach(element -> {
                    getElement().removeChild(element);
                });
        childMenuItems.forEach(sideMenuItem -> getElement().appendChild(sideMenuItem.getElement()));
    }

    public void setSelected(boolean selected) {
        getElement().setAttribute(SELECTED, selected);
    }

    public Component getIcon() {
        return icon;
    }

    public void setIcon(Component icon) {
        Objects.requireNonNull(icon, "The icon can't be null.");
        this.icon = icon;

        Element iconElement = icon.getElement();
        iconElement.setAttribute(SLOT_ATTRIBUTE, ICON_SLOT_VALUE);
        getElement().appendChild(iconElement);
    }

    private Router getRouter() {
        Router router = null;
        if (getElement().getNode()
                .isAttached()) {

            StateTree tree = (StateTree) getElement().getNode()
                    .getOwner();

            router = tree.getUI()
                    .getInternals()
                    .getRouter();
        }
        if (router == null) {
            router = VaadinService.getCurrent()
                    .getRouter();
        }
        if (router == null) {
            throw new IllegalStateException(
                    "Implicit router instance is not available. "
                            + "Use overloaded method with explicit router parameter.");
        }
        return router;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (event.getLocation()
                .getPath()
                .equals(getHref())) {
            this.setSelected(true);
        }
    }
}
