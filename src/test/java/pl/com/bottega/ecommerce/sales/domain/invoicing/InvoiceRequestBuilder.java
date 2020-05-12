package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;

import static org.mockito.Mockito.mock;

public class InvoiceRequestBuilder {

    private final InvoiceRequest invoiceRequest = new InvoiceRequest(mock(ClientData.class));
    private RequestItem requestItem;
    private int itemQuantity = 0;

    public InvoiceRequestBuilder withItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
        return this;
    }

    public InvoiceRequestBuilder withRequestItem (RequestItem requestItem) {
        this.requestItem = requestItem;
        return this;
    }

    public InvoiceRequest build() {
        for (int i = 0; i < itemQuantity; i++) {
            invoiceRequest.add(requestItem);
        }

        return invoiceRequest;
    }
}
