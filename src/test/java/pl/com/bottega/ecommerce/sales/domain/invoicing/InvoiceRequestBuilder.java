package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;

public class InvoiceRequestBuilder {

    private InvoiceRequest invoiceRequest;
    private RequestItem requestItem;

    public InvoiceRequestBuilder withItemQuantity(int itemQuantity) {
        for (int i = 0; i < itemQuantity; i++) {
            invoiceRequest.add(requestItem);
        }

        return this;
    }

    public InvoiceRequestBuilder withRequestItem (RequestItem requestItem) {
        this.requestItem = requestItem;
        return this;
    }

    public InvoiceRequestBuilder withClientData(ClientData clientData) {
        invoiceRequest = new InvoiceRequest(clientData);
        return this;
    }

    public InvoiceRequest build() {
        return invoiceRequest;
    }

}
