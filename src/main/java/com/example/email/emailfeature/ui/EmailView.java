package com.example.email.emailfeature.ui;

import com.example.base.ui.MainLayout;
import com.example.email.emailfeature.EmailService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@PageTitle("Send Email")
@Route(value = "email", layout = MainLayout.class)
@Menu(order = 1, icon = "vaadin:envelope", title = "Send Email")
public class EmailView extends VerticalLayout {

    private final EmailService emailService;

    public EmailView(EmailService emailService) {
        this.emailService = emailService;

        addClassNames(Display.FLEX, FlexDirection.COLUMN, AlignItems.CENTER, Padding.LARGE, Gap.MEDIUM);
        setWidthFull();

        var title = new H2("📧 Enviar Email");
        title.addClassNames(Margin.Bottom.MEDIUM);

        EmailField recipientField = new EmailField("Destinatário");
        TextField subjectField = new TextField("Assunto");
        TextArea messageField = new TextArea("Mensagem");

        Button sendButton = new Button("Enviar Email", event -> {
            try {
                emailService.sendPlainText(
                        recipientField.getValue(),
                        subjectField.getValue(),
                        messageField.getValue()
                );
                Notification notification = Notification.show("✅ Email enviado com sucesso!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (Exception e) {
                Notification notification = Notification.show("❌ Erro: " + e.getMessage());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        FormLayout form = new FormLayout(recipientField, subjectField, messageField);
        form.setMaxWidth("500px");
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        add(title, form, sendButton);
    }
}
