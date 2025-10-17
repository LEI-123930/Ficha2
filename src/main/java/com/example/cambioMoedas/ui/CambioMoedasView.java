package com.example.cambioMoedas.ui;


import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.javamoney.moneta.Money;
import javax.money.*;
import javax.money.convert.*;
import java.util.Currency;

@Route("currency-converter")
@PageTitle("Câmbio Moedas")
@Menu(order = 1, icon = "vaadin:coin-piles", title = "Câmbio Moedas")
class CambioMoedasView extends VerticalLayout {
    public CambioMoedasView() {
        // UI elements
        ComboBox<String> fromCurrency = new ComboBox<>("From Currency");
        ComboBox<String> toCurrency = new ComboBox<>("To Currency");

        fromCurrency.setItems(Currency.getAvailableCurrencies().stream()
                .map(java.util.Currency::getCurrencyCode)
                .sorted().toList());
        toCurrency.setItems(fromCurrency.getListDataView().getItems().toList());

        fromCurrency.setValue("USD");
        toCurrency.setValue("EUR");

        NumberField amountField = new NumberField("Amount");
        amountField.setValue(100.0);

        TextField resultField = new TextField("Result");
        resultField.setReadOnly(true);

        Button convertButton = new Button("Convert", event -> {
            try {
                MonetaryAmount input = Money.of(amountField.getValue(), fromCurrency.getValue());
                CurrencyConversion conversion = MonetaryConversions.getConversion(toCurrency.getValue());
                MonetaryAmount converted = input.with(conversion);

                resultField.setValue(converted.toString());
            } catch (Exception e) {
                Notification.show("Conversion failed: " + e.getMessage(),
                        3000, Notification.Position.MIDDLE);
            }
        });

        add(fromCurrency, toCurrency, amountField, convertButton, resultField);
    }
}
