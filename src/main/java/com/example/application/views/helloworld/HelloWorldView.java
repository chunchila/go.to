package com.example.application.views.helloworld;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;

@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "/home", layout = MainLayout.class)
@PageTitle("Hello World")
public class HelloWorldView extends VerticalLayout {

    private TextField txtUserName;

    @Autowired
    SitesRepo sitesRepo;

    public HelloWorldView() {
        addClassName("hello-world-view");
        txtUserName = new TextField("whats your name");


        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        // add remove buttons

        Button btnAdd = new Button("Add", new Icon(VaadinIcon.FILE_ADD));
        Button btnRemove = new Button("Remove", new Icon(VaadinIcon.FILE_REMOVE));
        TextField txtUrl = new TextField();
        txtUrl.setLabel("Url");

        horizontalLayout.add(txtUrl, btnAdd, btnRemove);

        HorizontalLayout horizontalLayout1 = new HorizontalLayout(txtUserName);
        Grid<Site> grid = new Grid<>(Site.class);


        grid.addColumn(Site::getUrl);
        grid.addColumn(Site::getHash);
        grid.addColumn(Site::getPerson);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        add(horizontalLayout1, horizontalLayout, grid);

        //setVerticalComponentAlignment();
        setHorizontalComponentAlignment(Alignment.END, txtUserName);

        btnAdd.addClickShortcut(Key.ENTER);

        btnAdd.addClickListener(buttonClickEvent -> {
            Site site = null;
            try {
                site = new Site(txtUrl.getValue(), txtUserName.getValue());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            sitesRepo.save(site);

            grid.getDataProvider().refreshItem(site);
            btnAdd.addThemeVariants(ButtonVariant.LUMO_ICON);
            Notification.show("added " + txtUrl.getValue());
        });
    }


}
