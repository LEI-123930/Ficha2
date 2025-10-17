package com.example.qrcodes.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("qr-code")
public class QRCodeView extends VerticalLayout {

    public QRCodeView() {
        TextField urlField = new TextField("URL");
        urlField.setPlaceholder("https://example.com");

        Button createButton = new Button("Create");

        Div imageHolder = new Div();

        createButton.addClickListener(evt -> {
            String url = urlField.getValue();
            if (url == null || url.trim().isEmpty()) {
                Notification.show("Please enter a URL");
                return;
            }
            try {
                // Use static helper to ensure QR generation is always reachable
                String dataUrl = com.example.qrcodes.QRCodeService.generateQRCodeDataUrl(url, 300);
                Image qrImage = new Image(dataUrl, "QR code");
                qrImage.setWidth("300px");
                qrImage.setHeight("300px");
                imageHolder.removeAll();
                imageHolder.add(qrImage);
            } catch (Exception e) {
                Notification.show("Failed to generate QR code");
            }
        });

        add(urlField, createButton, imageHolder);
        setPadding(true);
        setSpacing(true);
    }
}
