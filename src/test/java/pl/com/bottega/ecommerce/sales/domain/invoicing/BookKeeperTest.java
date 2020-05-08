package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookKeeperTest {

    @Mock
    private TaxPolicy taxPolicyMock;

    @Mock
    private ClientData clientDataMock;

    private BookKeeper bookKeeper;
    private InvoiceRequest invoiceRequest;

    @Before public void setUp() throws Exception {
        bookKeeper = new BookKeeper(new InvoiceFactory());
        invoiceRequest = new InvoiceRequest(clientDataMock);

        taxPolicyMock = mock(TaxPolicy.class);
        when(taxPolicyMock.calculateTax(ProductType.STANDARD, Money.ZERO)).thenReturn(new Tax(Money.ZERO, ""));
    }

    @Test public void invoiceRequestWithOneItemReturnInvoiceWithOnePosition() {
        invoiceRequest.add(new RequestItem(
                new Product(Id.generate(), Money.ZERO, "", ProductType.STANDARD).generateSnapshot(),
                1,
                Money.ZERO
        ));

        assertThat(bookKeeper.issuance(invoiceRequest, taxPolicyMock).getItems().size(), is(1));
    }
}
