package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;

import static org.mockito.Mockito.mock;

public class InvoiceRequestBuilder {

    private InvoiceRequest invoiceRequest = new InvoiceRequest(mock(ClientData.class));
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

    public InvoiceRequest build() {
        return invoiceRequest;
    }

}
