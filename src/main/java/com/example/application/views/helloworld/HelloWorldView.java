package com.example.application.views.helloworld;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "/", layout = MainLayout.class)
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

        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        //upload.setMaxFiles(1);
        upload.setDropLabel(new Label("Upload kubeconfig file"));
        upload.setAcceptedFileTypes("application/x-yaml");
        //upload.setMaxFileSize(300);


        Div output = new Div();

        upload.addSucceededListener(event -> {


            try {
                FileUtils.copyInputStreamToFile(buffer.getInputStream(), new File("uploaded.file"));
                output.removeAll();
                new Notification(
                        "uploaded file ", 3000).open();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        upload.addFileRejectedListener(event -> {


            Notification notification = new Notification(
                    "Error Please Select only  kubeconfig yaml", 3000);
            System.out.println(notification.getThemeNames());
            //notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            notification.open();

            output.removeAll();
//            showOutput(event.getErrorMessage(), component, output);
        });
        upload.getElement().addEventListener("file-remove", event -> {
            output.removeAll();
        });

        horizontalLayout2.add(upload, output);

        add(horizontalLayout1, horizontalLayout, grid, horizontalLayout2);

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
